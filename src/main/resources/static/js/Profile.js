const noItemsError = document.getElementById("noItemsError");
const serverError = document.getElementById("serverError");

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

let userToken = localStorage.getItem("jwt");

document.addEventListener("DOMContentLoaded", function() {
    const token = localStorage.getItem("jwt");
    const indicator = document.getElementById("login-indicator");

    if(token) {
        indicator.style.display = "block";
    }else {
        indicator.style.display = "none";
    }
});


try {
    if(userToken){
        fetch("http://localhost:8080/cart/ShowCartItems")
            .then(response => response.json())
            .then(data => {

                let productitem = document.getElementById("product-grid");
                let html ="";

                console.log(data);

                data.forEach(item =>{

                    html += `
                            <div class="col">
                                <div class="product-item" onclick="viewItem(${item.item_id})">
                                    <figure>
                                        <a href="#">
                                            <img src="/images/product-thumbnails/${item.item_thumbnailimg_name}" class="tab-image">
                                        </a>
                                    </figure>
                                    <span id="itemID" class="itemID">${item.item_id}</span>
                                    <h3>${item.item_name}</h3>
                                    <span class="qty">${item.item_quantity}pcs</span>
                                    <span class="price">${item.item_price}/=</span>
                                    <div class="cart-dash-logo"><i class="bi bi-cart-dash"></i></div>
                                </div>
                            </div>
                        `;

                })
                productitem.innerHTML = html;
            })
    }
    else {
        noItemsError.style.display = "block";
    }
}catch (err){
    serverError.style.display = "block";
    console.error(err);
}