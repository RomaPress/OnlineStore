function myFunction() {
    var x = document.getElementById("password");
    if (x.type === "password") {
        x.type = "text";
    } else {
        x.type = "password";
    }
}

function openPopUp() {
    var modal = $modal();
    document.querySelector('#show-modal')
        .addEventListener('click', function (e) {
        modal.show();
    });
}