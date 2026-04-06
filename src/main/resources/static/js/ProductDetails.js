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



document.getElementById("purchaseButton").addEventListner("click", async function(e) {
    e.preventDefault();

    const btn = this;
    btn.innerText = "Processing...";
    btn.style.pointerEvents = "none";

    try {
        await pay();
    }catch (err) {
    console.error(err);
    alert("Payment Failed.");

    btn.inneText ="Purchase";
    btn.style.pointerEvents =" auto";
    }
})

asyn function pay(){
    const amount = document.getElementById("insert-price").value;
    const quantity = document.getElementById("").value;
    const res = await fetch("", {
        method : "POST",
        headers: {
            "Content-Type": "aplication/json"
        },
        body: JSON.stringify({
        amount:amount

        })
    });
    const data = await res.json();

    const stripe = Stripe("pk_test_51SUjs3FWCjv2i6cESz4HrbXETu5RIuiP4yVycQ0JnbWptm7v7FpCH0qCuKEx49jwuZ5DGDr3pMNhPMNehDRer5N0003XOotCqv");

    await stripe.confirmCarpayment(data.client_secret, {
    payment_method: {
        card: cardElement
    }
    })
}