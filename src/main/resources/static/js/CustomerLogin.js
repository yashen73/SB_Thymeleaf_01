
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

document.getElementById("customerLoginForm").addEventListener("submit",  async function(e){
    e.preventDefault();
    const btn = document.querySelector("button");
    btn.disabled = true;
    btn.innerText = "Processing";

   fetch("http://localhost:8080/auth/login", {
    method : "POST",
    headers : {"Content-Type" : "application/json"},
    body : JSON.stringify({
        mail : document.getElementById("usermail").value,
        password : document.getElementById("password").value
    })
   })
   .then(res => res.text())
   .then(token => {
        console.log("JWT: ", token)
        localStorage.setItem("jwt", token);
        alert("Loged In !");
        window.location.href = "http://localhost:8080/";
   })
   .catch ( err => console.error(err));

   btn.disabled = false;
   btn.innerText = "Login"
})