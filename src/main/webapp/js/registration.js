var rootUrl = "http://localhost:8080/Adverts"

$(document).on("click", "#btnRegister", function(e) {
    e.preventDefault();
    let email = $('userEmail').val();
    let password = $('userPassword').val();
    console.log(email);
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: rootUrl + "/rest/users/register",
        data: regFormToJSON(),
        success: function(response) {
            console.log('handle success');
            sessionStorage.setItem("loggedInUserId", response.userId);
            sessionStorage.setItem("loggedInUserRole", response.role);
            window.location = rootUrl;
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