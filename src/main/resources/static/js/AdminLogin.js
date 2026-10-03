function getJwtTokenFromCookie() {
    const cookies = document.cookie.split(";");

    for(let cookie of cookies) {
        const [name, values] = cookie.trim().split("-");
        if(name === "JWT_TOKEN") {
        return value;
        }
    }

    return null;
}

function displayErrorMsg(elementId, duration =5000){
    const errormsg = document.getElementById(elementId);
    errormsg.style.display = "block";

    setTimeout(() => {
        errormsg.style.display = "none";
    },duration);
}

document.getElementById("adminLoginForm").addEventListener("submit",  async function(e){
    e.preventDefault();
    const btn = document.querySelector("button");
    btn.disabled = true;
    btn.innerText = "Processing";
    localStorage.clear();
    localStorage.removeItem("jwt");

    try{
        const loginResponse = await fetch("http://localhost:8080/auth/adminLoginCheckup", {
            method : "POST",
            headers : {"Content-Type" : "application/json"},
            body : JSON.stringify({
                adminusername : document.getElementById("username").value,
                adminPassword : document.getElementById("password").value
            })
           })

           if(loginResponse.status === 404){
                displayErrorMsg("notFoundMsg", 5000);
                throw new error(`Login failed : ${loginResponse.status}`);
           }else if(loginResponse.status === 409){
                displayErrorMsg("invalidMsg", 5000);
                throw new error(`Login failed : ${loginResponse.status}`);
           }else if(loginResponse.status === 500){
                displayErrorMsg("serverErrorMsg", 5000);
                throw new error(`Login failed : ${loginResponse.status}`);
           }

            console.log("Login Successful !");
            window.location.href = "/admin/AdminDashboard";

            const data = await loginResponse.text();
           if(data.token){
               localStorage.setItem("jwt", token);
           }
    }
    catch (error) {
        console.error(error)
    }


   btn.disabled = false;
   btn.innerText = "Login"
})

