
window.onload = function(){
    let successmessage = document.getElemenatById("successMessage").value;

    if(successMessage === "Successful"){
        setTimeout(function(){
        successMessage.style.display ="block"
        },5000)
    }
}


function showSearchBox() {
    let searchbox = document.getElementById("search-box")
    if(searchbox.style.display === "none") {
        searchbox.style.display ="block";
    } else {
        searchbox.style.display ="none";
    }
}

function displayErrorMsg(elementId, duration =5000){
    const errormsg = document.getElementById(elementId);
    errormsg.style.display = "block";

    setTimeout(() => {
        errormsg.style.display = "none";
    },duration);
}



window.onload= function(){
let alertBox = document.getElementById("failedmessage");
let status = alertBox.getAttribute("failed-status");
console.log(status)
    if(status === "failed"){
    setTimeout(function(){
    alertBox.style.display ="block"
    },5000);
    }
}

document.getElementById("customerLoginForm").addEventListener("submit",  async function(e){
    e.preventDefault();
    const btn = document.querySelector("button");
    btn.disabled = true;
    btn.innerText = "Processing";
    localStorage.clear();
    localStorage.removeItem("jwt");

    try {
        const responseforCustLogin = await fetch("/auth/custlogin", {
         method : "POST",
         headers : {"Content-Type" : "application/json"},
         body : JSON.stringify({
             mail : document.getElementById("usermail").value,
             password : document.getElementById("password").value
         })
        });

       if(responseforCustLogin.status === 404){
            console.log(responseforCustLogin);
            displayErrorMsg("notFoundMsg", 5000);
            throw new error(`Login failed : ${loginResponse.status}`);
       }else if(responseforCustLogin.status === 401){
            console.log(responseforCustLogin);
            displayErrorMsg("invalidMsg", 5000);
            throw new error(`Login failed : ${loginResponse.status}`);
       }else if(responseforCustLogin.status === 500){
            console.log(responseforCustLogin);
            displayErrorMsg("serverErrorMsg", 5000);
            throw new error(`Login failed : ${loginResponse.status}`);
       }

        console.log("JWT: ", token);
        localStorage.setItem("jwt", token);
        alert("Loged In !");
        window.location.href = "/index";
    }catch (err){
        console.error(err);
    }

   btn.disabled = false;
   btn.innerText = "Login"
})


document.addEventListener("DOMContentLoaded", function() {
    const token = localStorage.getItem("jwt");
    const indicator = document.getElementById("login-indicator");

    if(token) {
        indicator.style.display = "block";
    }else {
        indicator.style.display = "none";
    }
});