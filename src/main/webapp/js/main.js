var rootUrl = "http://localhost:8080/Adverts"


$(document).ready(function(){
    let userName = getUserName();
    
    let userType = getUserType(userName);
    let profileElement = null;

    if ( document.URL.includes("buyer-offers-placed.html") ) {
		findOffersPlaced(userName);
	} else {
        findAll();
    }

    // Display logged in username
    if (userName != null) {
        
        $('#userProfileNav').show();
        profileElement = $('#navbarDropdown')[0];
        profileElement.innerHTML = userName;
        if(userType == "Seller") {
            $('#sellerPostAdvert').show();
            $('#sellerViewAdverts').show();
        } else {
            $('#buyerViewOffers').show();
        }
    } else {
        $('#login').show();
        $('#register').show();
    }

    $(document).on("click", "#logOutBtn", function() {
        sessionStorage.clear();
        window.location = rootUrl;
    });
	
	$("#searchForm").submit(function(e) {
	    e.preventDefault();
	    findByKeyword($('#keyword').val(), $('#pricerange').val(), $('#includeWarranty')[0].checked);
        
	});
	
});

function getUserName() {
    return sessionStorage.getItem("loggedInUserId");
}

function getUserType(userName) {
    let userTypeFound = null;
    $.ajax({
		type: 'GET',
		async: false,
		url: rootUrl + '/rest/users/',
		dataType: "json",
		success: function(userList) {
			for(let i = 0; i < userList.length; i++) {
				if(userList[i].email == userName) {
                    userTypeFound = userList[i].userType;
					break;
				}
			}
		},
	});
    return userTypeFound;
}

function findOffersPlaced(userName) {
    $.ajax({
		type: 'GET',
		url: rootUrl + "/rest/adverts/search/buyer/"+userName,
		dataType: "json",
		success: renderList
	});
}

var findAll = function(userName) {
	$.ajax({
		type: 'GET',
		url: rootUrl + "/rest/adverts",
		dataType: "json",
		success: renderList
	});
}

var findByKeyword = function(title, pricerange, warrantyIncluded) {
	$.ajax({
		type: 'GET',
		url: rootUrl + '/rest/adverts/search/' + title+'/'+pricerange+'/'+warrantyIncluded,
		dataType: "json",
		success: renderList
	});
}

var renderList= function(data) {
	templateStr = '';
	var list = data == null ? [] : (data instanceof Array ? data : [data]);
	$('#advertList div').remove();
	
	let i = 1;
	$.each(list, function(index, advert) {
	    
		if (i === 1) {
			templateStr += '<div class="row hidden-md-up justify-content-md-center">';
		}
		
		templateStr += '<div class="col-md-2 col-lg-2 col-sm-12">';
		templateStr += '<a onclick="viewAdvert('+advert.id+')" href="#">';
		templateStr += '<div class="card">';
		templateStr += '<div class="card-block">';
		templateStr += '<img class="card-img-top" style="height: 140px; overflow:hidden " src="'+advert.imagePrimary+'" alt="Card image cap">';
		templateStr += '<div class="card-body">';
		templateStr += '<h5 class="card-title">'+ advert.title +'</h5>';
		templateStr += '<h5 class="card-title">Asking Price: $'+ advert.askingPrice +'</h5>';
		templateStr += '<p class="card-text">'+advert.description+'</p>';
		templateStr += '<div class="card-footer">';
		templateStr += '<small class="text-muted"><i class="fas fa-clock"></i> Posted: '+ calcPostDate(advert.datePosted) 
        +'<br><i class="fas fa-user"></i>Seller: '+advert.seller+'<br>Seller rating: '+advert.sellerRating+'<i class="fas fa-star"></i><br><i class="fas fa-map"></i>Location: '+advert.location+'</small><i class="fa-solid fa-location-dot"></i></div></div>';
		templateStr += '</div></div>';
		templateStr += '</a>';
        if (document.URL.includes("buyer-offers-placed.html")) {
            
            if(advert.outForDelivery) {
                templateStr += "<strong>Item has been posted!</strong><br>";
                if(!advert.buyerLeftRating) {
                    templateStr += '<a onclick="leaveSellerRating(\''+advert.seller+'\','+ advert.id+')" href="#">Leave Seller Rating (1-5):</a>';
                    templateStr += '<select class="form-control form-control-sm" class="ratingScale" id="sellerRatingAdvert'+advert.id+'">'+
                                        '<option value="1">1</option>'+
                                        '<option value="2">2</option>'+
                                        '<option value="3">3</option>'+
                                        '<option value="4">4</option>'+
                                        '<option value="5">5</option>'+
                                    '</select>';
                }
                
            } else if(advert.offerAccepted) {
                templateStr += "<em>Offer of $"+ advert.largestOffer +" accepted by Seller</em><br>"
                templateStr += "<strong>Contact Seller on: " + advert.seller +"</strong><br>";
            } else {
                templateStr += "<strong>Your Offer: $" + advert.largestOffer + "</strong><br>";
                templateStr += "<a onclick='withdrawOffer("+advert.id+")' href='#'>Withdraw Offer<a>";
            }
        }
        templateStr += '</div>';
		if (i === 4) {
			templateStr += '</div>';
			i = 1;
		} else {
			i++;	
		}
	});
	$('#advertList').append(templateStr);

};

function withdrawOffer(advertId) {
    console.log('advert id ='+advertId);
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: rootUrl + "/rest/adverts/withdraw-offer/"+advertId,
        async: false,
        success: function(response) {
            alert('Offer withdrawn successfully');
            window.location = rootUrl + '/buyer-offers-placed.html';
        }
    });
}

function viewAdvert(advertId) {
    var advert = null 
    $.ajax({
        type: 'GET',
        async: false,
        url: rootUrl + "/rest/adverts/"+advertId,
        dataType: "json",
        success: function(response) {
                    advert = response;
                }
    });
    
    templateStr = '';
	$('#advertList div').remove();
    templateStr += '<div class="row hidden-md-up justify-content-md-center">';
    templateStr += '<div class="col-md-2 col-lg-2 col-sm-12">';
    templateStr += '<a onclick="viewAdvert("'+ advert.id +'")" href="#">';
    templateStr += '<div class="card">';
    templateStr += '<div class="card-block">';
    templateStr += '<img class="card-img-top" style="height: 140px; overflow:hidden " src="//'+advert.imagePrimary+'" alt="Card image cap">';
    templateStr += '<div class="card-body">';
    templateStr += '<h5 class="card-title">'+ advert.title +'</h5>';
    templateStr += '<h5 class="card-title">'+ advert.askingPrice +'</h5>';
    templateStr += '<p class="card-text">'+advert.description+'</p>';
    templateStr += '<div class="card-footer">';
    templateStr += '<small class="text-muted"><i class="fas fa-clock"></i> Posted: '+ calcPostDate(advert.datePosted) +'<br><i class="fas fa-user"></i> Seller: '+advert.seller+'<br><i class="fas fa-map"></i> Location: '+advert.location+'</small><i class="fa-solid fa-location-dot"></i></div></div>';
    templateStr += '</div></div>';
    templateStr += '</a>';
    templateStr += '<p>Highest Offer Placed: '+advert.largestOffer+'</p>';
    templateStr += '<input id="offerAmount" placeholder="0.00"></input><br>'
    templateStr += '<a onclick="placeOffer('+advert.id+')" href="#">Place Offer</a>'
    templateStr += '</div></div>';
	$('#advertList').append(templateStr);

}

function calcPostDate(postDate) {

    const minute = 60 * 1000;
    const hour = minute * 60;
	const day = hour * 24;
    const month = day * 30;
    const year = day * 365;

    var now = Date.now();
    
    var timeDifference = now - new Date(postDate);
    if (timeDifference < minute) {
         return Math.round(timeDifference/1000) + ' seconds ago';   
    } else if (timeDifference < hour) {
         return Math.round(timeDifference/minute) + ' minutes ago';   
    } else if (timeDifference < day ) {
         return Math.round(timeDifference/hour ) + ' hours ago';   
    } else if (timeDifference < month) {
        return Math.round(timeDifference/day) + ' days ago';   
    } else if (timeDifference < year) {
        return Math.round(timeDifference/month) + ' months ago';   
    } else {
        return Math.round(timeDifference/year ) + ' years ago';   
    }
}

// When user has selected an advert, we need to know the id of the advert
let fetchAdvertId = function () {
	let url = new URL(window.location.href);
	return url.searchParams.get("advert");
}


// User Registration
$(document).on("click", "#btnRegister", function(e) {
    e.preventDefault();

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: rootUrl + "/rest/users/register",
        data: regFormToJSON(),
        success: function(response) {
            sessionStorage.setItem("loggedInUserId", response.email);
            window.location = rootUrl + "/login.html";
        },
        error: function(jqXHR, textStatus, errorThrown) {
            $("#loginErrorText").show();
        }
    });
    return false;
});

var regFormToJSON = function() {
    return JSON.stringify({
        "email": $('#userEmail').val(),
        "password": $('#userPassword').val(),
        "userType": $('#userType').val()
    });
}

// User Login
$(document).on("click", "#btnLogin", function(e) {
    e.preventDefault();

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: rootUrl + "/rest/users/login",
        data: loginFormToJSON(),
        success: function(response) {
            sessionStorage.setItem("loggedInUserId", response.email);
            window.location = rootUrl;
        },
        error: function(jqXHR, textStatus, errorThrown) {
            $("#loginErrorText").show();
        }
    });
    return false;
});

let loginFormToJSON = function() {
    return JSON.stringify({
        "email": $('#loginUserEmail').val(),
        "password": $('#loginUserPassword').val(),
    });
}

// Post adverts
$(document).on("click", "#btnPostAdvert", function(e) {
    e.preventDefault();
    $.ajax({
        type: "POST",
        contentType: "application/json",
        async: false,
        url: rootUrl + "/rest/adverts/post",
        data: advertFormToJSON(),
        success: function(response) {
            window.location = rootUrl;
			
        },
        error: function(jqXHR, textStatus, errorThrown) {
        }
    });
    return false;
});

let advertFormToJSON = function() {
    let userName = getUserName();
    return JSON.stringify({
        "title": $('#advertTitle').val(),
        "askingPrice": $('#advertPrice').val(),
        "category": $('#advertCategory').val(),
        "description": $('#advertDescription').val(),
        "location": $('#advertLocation').val(),
        "imagePrimary": $('#imagePrimary').val(),
        "imageSecondary": $('#imageSecondary').val(),
        "imageTertiary": $('#imageTertiary').val(),
        "imageQuaternary": $('#imageQuaternary').val(),
        "warrantyIncluded":$('#postWarranty')[0].checked,
        "seller": userName,
        "sellerRating": calculateUserRating(userName),
		"largestOffer": 0,
		"offerAccepted":false,
		"outForDelivery": false
    });
}

function placeOffer(advertId) {
    let offer = $('#offerAmount').val();

    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: rootUrl + "/rest/adverts/place-offer/",
        async: false,
        data: JSON.stringify({
            "offer":offer,
            "advertId":advertId,
            "buyerId": getUserName()
        }),
        success: function(response) {
            alert('Offer placed successfully');
            window.location = rootUrl + '/buyer-offers-placed.html';
        },
        error: function(){
            alert('Offer is less than the highest offer');
        }
    });
}

async function leaveSellerRating(sellerId, advertId) {
	
	let rating = $('#sellerRatingAdvert'+advertId).val();


	$.ajax({
        type: "PUT",
		async: false,
        contentType: "application/json",
        url: rootUrl + "/rest/users/rating",
        data: await sellerRatingJson(sellerId, rating, advertId, getUserType(sellerId)),
    });
	
    return false;
}

async function sellerRatingJson(sellerId, rating, advertId, userType) {
	return JSON.stringify({
		"buyerId":sellerId,
        "rating":rating,
        "advertId":advertId,
        "userType":userType
    });
}

function calculateUserRating(userId) {
	var rating = 0;
	$.ajax({
		type: 'GET',
		async: false,
		url: rootUrl + '/rest/users/',
		dataType: "json",
		success: function(userList) {
			for(let i = 0; i < userList.length; i++) {
				if(userList[i].email == userId) {
					rating += userList[i].ratingTotal / userList[i].numberOfRatings;
					break;
				}
			}
		}
	});
	
	return Math.round((rating + Number.EPSILON) * 100) / 100;
}