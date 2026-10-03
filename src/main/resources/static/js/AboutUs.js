document.addEventListener("DOMContentLoaded", function() {
    const token = localStorage.getItem("jwt");
    const indicator = document.getElementById("login-indicator");

    if(token) {
        indicator.style.display = "block";
    }else {
        indicator.style.display = "none";
    }
});


function Search(){
    const query = document.querySelector(".search-box input").value;
    alert("Searching for: "+query);
}


