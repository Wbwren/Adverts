var rootUrl = "http://localhost:8080/Adverts"

$(document).ready(function(){

    // Display logged in username
    let userName = sessionStorage.getItem("loggedInUserId");
    let profileElement = null;
    if (userName != null) {
        $('#userProfileNav').show();
		$('#sellerPostAdvert').show();
		$('#sellerViewAdverts').show();
        profileElement = $('#navbarDropdown')[0];
        profileElement.innerHTML = userName;
    } else {
        $('#login').show();
        $('#register').show();
    }
	if ( document.URL.includes("seller-adverts.html") ) {
		findBySeller(userName);
	}
    

	if ( document.URL.includes("edit-advert.html") ) {
		populateAdvertFields();
	}

    $(document).on("click", "#logOutBtn", function() {
        sessionStorage.clear();
        window.location = rootUrl;
    });
	
	$("#searchForm").submit(function(e) {
	    e.preventDefault();
	    findByKeyword($('#keyword').val());
	});
	
});


var findBySeller = function(userName) {
	$.ajax({
		type: 'GET',
		url: rootUrl + '/rest/adverts/search/seller/' + userName,
		dataType: "json",
		success: renderList
	});
}

var findByKeyword = function(title) {
	$.ajax({
		type: 'GET',
		url: rootUrl + '/rest/adverts/search/' + title,
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
		templateStr += '<a href="edit-advert.html?advert='+advert.id+'">';
		templateStr += '<div class="card">';
		templateStr += '<div class="card-block">';
		templateStr += '<img class="card-img-top" style="height: 140px; overflow:hidden" src="img/'+advert.imagePrimary+'" alt="Card image cap">';
		templateStr += '<div class="card-body">';

		templateStr += '<h5 class="card-title">'+ advert.title +'</h5>';
		templateStr += '<h5 class="card-title">Asking Price: $'+ advert.askingPrice +'</h5>';
		templateStr += '<p class="card-text">'+advert.description+'</p>';

		templateStr += '<div class="card-footer">';
		templateStr += '<small class="text-muted"><i class="fas fa-clock"></i> Posted: '+ calcPostDate(advert.datePosted) +'<br><i class="fas fa-user"></i> Seller: '+advert.seller+'<br><i class="fas fa-map"></i> Location: '+advert.location+'</small><i class="fa-solid fa-location-dot"></i>';
		templateStr += '</div>';

		templateStr += '</a>';
		if(advert.outForDelivery) {
			templateStr += '<p>Advert Sale Complete</p>';
			if(!advert.sellerLeftRating) {
				templateStr += '<a onclick="leaveBuyerRating(\''+advert.buyerId+'\','+ advert.id+')" href="#">Leave Buyer Rating (1-5):</a>';
				templateStr += '<select class="form-control form-control-sm" class="ratingScale" id="buyerRatingAdvert'+advert.id+'">'+
									'<option value="1">1</option>'+
									'<option value="2">2</option>'+
									'<option value="3">3</option>'+
									'<option value="4">4</option>'+
									'<option value="5">5</option>'+
								'</select>';
				templateStr += '<a onclick="removeAddFromSelleriew('+advert.id+')" href="#">Delete Advert</a></div></div>';
				templateStr += '</div></div>';
			}
			
		} else {
			if(advert.offerAccepted) {
				templateStr += '<strong>Offer Accepted.<br>Buyer Contact: '+ advert.buyerId +'</strong><br>';
				if(!advert.outForDelivery) {
					templateStr += '<a href="#" onclick="markOutForDelivery('+advert.id+')">Mark Out For Delivery</a><br>';
					templateStr += '<a onclick="removeAddFromSelleriew('+advert.id+')" href="#">Delete Advert</a></div></div>';
					templateStr += '</div></div>';
					
				} else {
					templateStr += '<a onclick="removeAddFromSelleriew('+advert.id+')" href="#">Delete Advert</a></div></div>';
					templateStr += '</div></div>';
				}
			} else if(advert.largestOffer > 0) {
				templateStr += '<strong>'+'Offer placed for: $' + advert.largestOffer + '</strong>';
				templateStr += '<div>Buyer Rating: ' + calculateBuyerRating(advert.buyerId) + '<i class="fas fa-star"></i></div>';
				templateStr += '<a onclick="acceptOffer('+advert.id+')" href="#">Accept Offer</a><br>';
				templateStr += '<a onclick="removeAddFromSelleriew('+advert.id+')" href="#">Delete Advert</a></div></div>';
				templateStr += '</div></div>';
			} else {
				templateStr += '<strong>'+'No offers yet placed</strong><br>';
				templateStr += '<a onclick="removeAddFromSelleriew('+advert.id+')" href="#">Delete Advert</a></div></div>';
				templateStr += '</div></div>';
			}
		}

		if (i === 4) {
			templateStr += '</div>';
			i = 1;
		} else {
			i++;	
		}
	});
	$('#advertList').append(templateStr);

};

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


async function populateAdvertFields() {
	let advertId = fetchAdvertId();
	let advert;
	try {
		advert = await fetchAdvertObj(advertId);
	} catch(e) {
		console.log(e);
	}
	fillFieldData(advert);
}

function fetchAdvertObj(advertId) {
	return $.ajax({
		type: 'GET',
		url: rootUrl + '/rest/adverts/' + advertId,
		dataType: "json"
	});
}

function fillFieldData(advert) {
	$('#advertTitle').attr('value', advert.title);
	$('#advertPrice').attr('value', advert.askingPrice);
	$('#advertCategory').attr('value', advert.category);
	$('#advertDescription').attr('value', advert.description);
	$('#advertLocation').attr('value', advert.location);
	$('#imagePrimary').attr('value', advert.imagePrimary);
	$('#imageSecondary').attr('value', advert.imageSecondary);
	$('#imageTertiary').attr('value', advert.imageTertiary);
	$('#imageQuaternary').attr('value', advert.imageQuaternary);
}

$(document).on("click", "#btnEditAdvert", async function(e) {
    e.preventDefault();
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: rootUrl + "/rest/adverts/edit",
        data: await advertFormToJSON(),
        success: function(response) {
            window.location = rootUrl+'/seller-adverts.html';
			
        }
    });
    return false;
});

async function advertFormToJSON() {
	let advertId = fetchAdvertId();
	let advert;
	try {
		advert = await fetchAdvertObj(advertId);
	} catch(e) {
		console.log(e);
	}
	return JSON.stringify({
		"id":fetchAdvertId(),
        "title": $('#advertTitle').val(),
        "askingPrice": $('#advertPrice').val(),
        "category": $('#advertCategory').val(),
        "description": $('#advertDescription').val(),
        "location": $('#advertLocation').val(),
        "imagePrimary": $('#imagePrimary').val(),
        "imageSecondary": $('#imageSecondary').val(),
        "imageTertiary": $('#imageTertiary').val(),
        "imageQuaternary": $('#imageQuaternary').val(),
		"datePosted": advert.datePosted,
		"largestOffer": advert.largestOffer,
		"offerAccepted":advert.offerAccepted, 
		"seller":advert.seller,
		"sellerRating":advert.sellerRating,
		"buyerId":advert.buyerId,
		"buyerRating": advert.buyerRating,
		"outForDelivery": advert.outForDelivery
    });
	
}

function acceptOffer(advertId) {
	
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: rootUrl + "/rest/adverts/accept-offer/"+advertId,
        success: function(response) {
            window.location = rootUrl+'/seller-adverts.html';
			
        }
    });
    return false;
};


function markOutForDelivery(advertId) {
	$.ajax({
        type: "PUT",
        contentType: "application/json",
        url: rootUrl + "/rest/adverts/out-for-delivery/"+advertId,
        success: function(response) {
            window.location = rootUrl+'/seller-adverts.html';
        }
    });
    return false;
}

function removeAddFromSelleriew(advertId) {
	$.ajax({
        type: "DELETE",
        contentType: "application/json",
        url: rootUrl + "/rest/adverts/"+advertId,
        data: advertId,
        success: function(response) {
            window.location = rootUrl+'/seller-adverts.html';
        }
    });
    return false;
}

async function leaveBuyerRating(sellerId, advertId) {
	
	let rating = $('#buyerRatingAdvert'+advertId).val();


	$.ajax({
        type: "PUT",
		async: false,
        contentType: "application/json",
        url: rootUrl + "/rest/users/rating",
        data: await buyerRatingJson(sellerId, rating, advertId, getUserType(sellerId)),
    });
	
    return false;
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


async function buyerRatingJson(userId, rating, advertId, userType) {
	return JSON.stringify({
		"buyerId":userId,
        "rating":rating,
        "advertId":advertId,
        "userType":userType
    });
}

function calculateBuyerRating(buyerId) {
	var rating = 0;
	$.ajax({
		type: 'GET',
		async: false,
		url: rootUrl + '/rest/users/',
		dataType: "json",
		success: function(userList) {
			for(let i = 0; i < userList.length; i++) {
				if(userList[i].email == buyerId) {
					rating += userList[i].ratingTotal / userList[i].numberOfRatings;
					break;
				}
			}
		},
	});
	
	return Math.round((rating + Number.EPSILON) * 100) / 100;
}