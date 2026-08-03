
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

fetch("http://localhost:8080/cart/ShowCartItems", {
    method: 'GET',
    headers: {
        'Authorization': userToken,
        'Content-Type' : 'application/json'
    }
})
.then(response =>  response.json())
.then(data => {
    let productitem = document.getElementById("product-grid");
    let html ="";

    console.log(data);

    data.forEach(item =>{
        html +=  `
            <div class="col">
                <div class="product-item" onclick="viewItem(${item.item_id})">
                    <figure>
                        <a href="#">
                            <img src="images/product-thumbnails/${item.item_thumbnailimg_name}" class="tab-image">
                        </a>
                    </figure>
                    <span id="itemID" class="itemID">${item.item_id}</span>
                    <h3>${item.item_name}</h3>
                    <span class="qty">${item.item_quantity}pcs</span>
                    <span class="price">${item.item_price}/=</span>

                </div>
            </div>
        `;
    })
    productitem.innerHTML = html;
})