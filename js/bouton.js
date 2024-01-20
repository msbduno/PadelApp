// Get the button, and when the user clicks on it, execute myFunction
document.getElementById("noti_btn").onmouseenter = function () {
    Notificationsbtn()
};



document.getElementById("noti_content").onmouseleave = function () {
    Notificationsbtn()
};

document.getElementById("MonProf_btn").onmouseenter = function () {
    MonProfilbtn()
};

document.getElementById("MonProf_content").onmouseleave = function () {
    MonProfilbtn()
};

/* myFunction toggles between adding and removing the show class, which is used to hide and show the dropdown content */
function Notificationsbtn() {
    document.getElementById("noti_content").classList.toggle("show");

}

function MonProfilbtn() {
    document.getElementById("MonProf_content").classList.toggle("show");
}

