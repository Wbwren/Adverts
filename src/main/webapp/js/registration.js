var rootUrl = "http://localhost:8080/Adverts"

// User Registration
$(document).on("click", "#btnRegister", function(e) {
    e.preventDefault();

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: rootUrl + "/rest/users/register",
        data: regFormToJSON(),
        success: function(response) {
            console.log('handle success');
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
    console.log(loginFormToJSON());

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: rootUrl + "/rest/users/login",
        data: loginFormToJSON(),
        success: function(response) {
            sessionStorage.setItem("loggedInUserId", response.userEmail);
            window.location = rootUrl;
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log('error thrown: '+ errorThrown);
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

$('#navbarDropdown').val = sessionStorage.getItem("loggedInUserId");
console.log($('#navbarDropdown'));

$(document).on("click", "#logOutBtn", function() {
    sessionStorage.clear();
    window.location = loginPage;
});