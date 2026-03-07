
window.onload = function(){
    let successmessage = document.getElemenatById("successMessage").value;

    if(successMessage === "Successful"){
        setTimeout(function(){
        successMessage.style.display ="block"
        },5000)
    }
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