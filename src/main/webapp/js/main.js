var rootUrl = "http://localhost:8080/Adverts"

$(document).ready(function(){  
	findAll();
	
	$("#searchForm").submit(function(e) {
	    e.preventDefault();
	    findByKeyword($('#keyword').val());
	});
	
});


var findAll = function() {
	$.ajax({
		type: 'GET',
		url: rootUrl + "/rest/adverts",
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
		templateStr += '<a href="advert.html?advert='+advert.id+'">';
		templateStr += '<div class="card">';
		templateStr += '<div class="card-block">';
		templateStr += '<img class="card-img-top" style="height: 140px; overflow:hidden " src="img/'+advert.image+'" alt="Card image cap">';
		templateStr += '<div class="card-body">';
		templateStr += '<h5 class="card-title">'+ advert.title +'</h5>';
		templateStr += '<h5 class="card-title">'+ advert.askingPrice +'</h5>';
		templateStr += '<p class="card-text">'+advert.description+'</p>';
		templateStr += '<div class="card-footer">';
		templateStr += '<small class="text-muted"><i class="fas fa-clock"></i> Posted: '+ calcPostDate(advert.datePosted) +'<br><i class="fas fa-user"></i> Seller: '+advert.seller+'<br><i class="fas fa-map"></i> Location: '+advert.location+'</small><i class="fa-solid fa-location-dot"></i></div></div>';
		templateStr += '</div></div>';
		templateStr += '</a></div>';
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

    var timeDifference = Date.now() - postDate;

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
            sessionStorage.setItem("loggedInUserId", response.userId);
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
        "userId": $('#userEmail').val(),
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
            sessionStorage.setItem("loggedInUserId", response.userEmail);
            window.location = rootUrl;
			setUserName();
        },
        error: function(jqXHR, textStatus, errorThrown) {
            $("#loginErrorText").show();
        }
    });
    return false;
});

var loginFormToJSON = function() {
    return JSON.stringify({
        "userId": $('#loginUserEmail').val(),
        "password": $('#loginUserPassword').val(),
    });
}

// Display logged in username
let userName = sessionStorage.getItem("loggedInUserId");
let profileElement = null;
if (userName != null) {
	$('#userProfileNav').show();
	let profileElement = $('#navbarDropdown')[0];
	profileElement.innerHTML = userName;
} else {
	$('#login').show();
	$('#register').show();
}

$(document).on("click", "#logOutBtn", function() {
    sessionStorage.clear();
    window.location = rootUrl;
});