document.getElementById("customerSignUpForm").addEventListener("submit",  async function(e){
    e.preventDefault();
    const btn = document.querySelector("button");
    btn.disabled = true;
    btn.innerText = "Processing";
    localStorage.clear();
    localStorage.removeItem("jwt");

    try{
        const signUpResponse = await fetch("http://localhost:8080/auth/custsignup", {
            method : "POST",
            headers : {"Content-Type" : "application/json"},
            body : JSON.stringify({
                name : document.getElementById("name").value,
                mail : document.getElementById("mail").value,
                tele : document.getElementById("tele").value,
                password : document.getElementById("password").value
            })
           })

           if(!signUpResponse.ok){
                alert(`Sign up Failed : ${signUpResponse.status}`);
                throw new error(`Sign up failed : ${signUpResponse.status}`);
           }
            alert("SignUp Successful !");
            window.location.href = "/CustomerLogin";
    }
    catch (error) {
        console.error(error)
    }


   btn.disabled = false;
   btn.innerText = "Login"
})