function showSearchBox() {
    let searchbox = document.getElementById("search-box")
    if(searchbox.style.display === "none") {
        searchbox.style.display ="block";
    } else {
        searchbox.style.display ="none";
    }
}


function Search(){
    const query = document.querySelector(".search-box input").value;
    alert("Searching for: "+query);
}


