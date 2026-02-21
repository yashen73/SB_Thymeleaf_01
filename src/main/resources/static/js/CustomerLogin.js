
window.onload = function(){
    let successmessage = document.getElemenatById("successMessage").value;

    if(successMessage==="Successful"){
        setTimeout(function(){
        successMessage.style.display ="block"
        },5000)
    }
}