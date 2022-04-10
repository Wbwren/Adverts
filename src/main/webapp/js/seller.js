var rootUrl = "http://localhost:8080/Adverts"

$(document).ready(function(){
    
    // Display logged in username
    let userName = sessionStorage.getItem("loggedInUserId");
    let profileElement = null;
    if (userName != null) {
        $('#userProfileNav').show();
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
        console.log(advert.datePosted);
	    
		if (i === 1) {
			templateStr += '<div class="row hidden-md-up justify-content-md-center">';
		}
		
		templateStr += '<div class="col-md-2 col-lg-2 col-sm-12">';
		templateStr += '<a href="edit-advert.html?advert='+advert.id+'">';
		templateStr += '<div class="card">';
		templateStr += '<div class="card-block">';
		templateStr += '<img class="card-img-top" style="height: 140px; overflow:hidden " src="img/'+advert.imagePrimary+'" alt="Card image cap">';
		templateStr += '<div class="card-body">';
		templateStr += '<h5 class="card-title">'+ advert.title +'</h5>';
		templateStr += '<h5 class="card-title">'+ advert.askingPrice +'</h5>';
		templateStr += '<p class="card-text">'+advert.description+'</p>';
		templateStr += '<div class="card-footer">';
		templateStr += '<small class="text-muted"><i class="fas fa-clock"></i> Posted: '+ calcPostDate(advert.datePosted) +'<br><i class="fas fa-user"></i> Seller: '+advert.seller+'<br><i class="fas fa-map"></i> Location: '+advert.location+'</small><i class="fa-solid fa-location-dot"></i></div></div>';
		templateStr += '</div></div>';
		templateStr += '</a>';
		if(advert.offerAccepted) {
			templateStr += '<h5>'+'Offer Accepted. Buyer Contact: '+ advert.buyer +' </h5></div>';
		} else if(advert.largestOffer > 0) {
			templateStr += '<h5>'+'Offer placed for: ' + advert.largestOffer + '</h5>';
			templateStr += '<div>Buyer Rating: ' + advert.buyerRating + '<i class="fa-solid fa-star-sharp"></i></div>';
			templateStr += '<a onclick="acceptOffer('+advert.id+')" href="#">Accept Offer</a></div>';
		} else {
			templateStr += '</div>';
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
			
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR);
            console.log(textStatus);
            console.log(errorThrown);

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
	console.log(advert.seller);
	console.log(advert);
	console.log(advert.largestOffer);
	console.log(advert.offerAccepted);
	console.log(advert.seller);
	console.log(advert.buyer);
	console.log(advert.buyerRating);
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
		//"datePosted": advert.datePosted,
		"largestOffer": advert.largestOffer,
		"offerAccepted":advert.offerAccepted, 
		"seller":advert.seller,
		"buyer":advert.buyer,
		"buyerRating": advert.buyerRating
    });
	
}

function acceptOffer(advertId) {
	
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: rootUrl + "/rest/adverts/accept-offer/"+advertId,
        success: function(response) {
            window.location = rootUrl+'/seller-adverts.html';
			
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log('error updating advert...');
            $("#loginErrorText").show();
        }
    });
    return false;
};