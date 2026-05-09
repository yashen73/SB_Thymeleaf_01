(function($) {

    var initSwiper = function(){
        var swiper = new Swiper(".main-swiper",{
            speed:500,
            pagination: {
                el: ".swiper-pagination",
                clickable :true,
            }
        });


    var brand_swiper = new Swiper(".category-carousel", {
        slidesPerView: 6,
        slidesBetween: 30,
        speed: 500,
        navigation: {
            nextEl: ".category-carousel-next",
            prevEl: ".category-carousel-prev",
        },
        breakpoints: {
            0: {
                slidesPerView: 2,
            },
            768: {
                slidesPerView: 3,
            },
            991: {
                slidesPerView: 4,
            },
            1500: {
                slidesPerView: 6,
            },
        }
    });
}

    initSwiper();
})(jQuery);


document.addEventListener("DOMContentLoaded", function() {
    const token = localStorage.getItem("jwt");
    const indicator = document.getElementById("login-indicator");

    if(token) {
        indicator.style.display = "block";
    }else {
        indicator.style.display = "none";
    }
});


fetch("http://localhost:8080/item/showAllItems")
.then(response => response.json())
.then(data => {

    let productitem = document.getElementById("product-grid");
    let html ="";

    console.log(data)


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

                    </div>
                </div>
            `;

    })
    productitem.innerHTML = html;
})



document.getElementById("purchaseButton").addEventListener("click", async function(e) {
    e.preventDefault();

    const btn = this;
    btn.innerText = "Processing...";
    btn.style.pointerEvents = "none";

    try {
        if(!localStorage.getItem("jwt")){
            alert("Please Login fisrt");
            console.log("No JWT. Login first");
            btn.inneText ="Purchase";
            btn.style.pointerEvents =" auto";
        }else{
            console.log("JWT exists and forwading to the payment")
            await pay();
        }

    }catch (err) {
    console.error(err);
    alert("Payment Failed.");

    btn.inneText ="Purchase";
    btn.style.pointerEvents =" auto";

    }
})

async function pay(){
    const productid =document.getElementById("ProductId").textContent;
    const amount = document.getElementById("insert-price").textContent;
    const quantity = document.getElementById("quantity-no").value;
    console.log(productid, amount, quantity, " order is heading to the payment.")
    const res = await fetch("http://localhost:8080/api/payment/checkout", {
        method : "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "bearer " + localStorage.getItem("jwt")
        },
        body: JSON.stringify({
               productid: productid,
               quantity:quantity,
               amount : amount
        })
    })
    .then(res => res.text())
    .then( url => {
        window.location.href = url;
    })
    .catch(error=> console.error(error));

}


document.getElementById("addtocart-btn").addEventListener("click", async function(e) {
    e.preventDefault();

    try {
        if(!localStorage.getItem("jwt")){
            alert("Please Login first");
        }else{
            await addtocart();
        }
    }

})

await function addtocart(){
    let itemIdforAddtoCart = document.getElementById("ProductId").textContent;
    const tokenforAddtoCart = localStorage.getItem("jwt");
    let itemQuantityforAddtoCart = document.getElementById("quantity-no").value;

    const repondforAddtoCart =await fetch(""+itemIdforAddtoCart, {
        method: "POST",
        headers: {
            "Content-Type" : "Application/json",
            "Authorization-token" : "bearer" + token;
        },
        body: JSON.stringify{

        }
    })
}