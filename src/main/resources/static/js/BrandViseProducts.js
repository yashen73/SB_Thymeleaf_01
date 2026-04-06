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


fetch("http://localhost:8080/item/showAllItems")
.then(response => response.json())
.then(data => {

    let productitemformoose = document.getElementById("product-grid-moose");
    let productitemforlevis = document.getElementById("product-grid-levis");
    let productitemforcrocodile = document.getElementById("product-grid-crocodile");
    let productitemforsignature = document.getElementById("product-grid-signature");
    let html1 = "";
    let html2 = "";
    let html3 = "";
    let html4 = "";

    console.log(data);

    data.forEach(item =>{

        if(item.brand_item_id==="T1"){

            html1 += `
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
        }else if(item.brand_item_id === "T2"){
        html2 += `
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

        }else if(item.brand_item_id === "T3"){
        html3 += `
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
        }else if(item.brand_item_id === "T4"){
        html4 += `
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
        }

    })

    productitemformoose.innerHTML =html1;
    productitemforlevis.innerHTML =html2;
    productitemforcrocodile.innerHTML =html3;
    productitemforsignature.innerHTML =html4;


})

function viewItem(id) {
        //Redirect with ID
        window.location.href = "product/productdetail/" +id;
}
