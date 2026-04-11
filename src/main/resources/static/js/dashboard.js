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

    let productitem = document.getElementById("product-grid");
    let html ="";

    console.log(data)


    data.forEach(item =>{

        html += `
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

function viewItem(id) {
        //Redirect with ID
        window.location.href = "product/productdetail/" +id;
}

document.addEventListener("DOMContentLoaded", function() {
    const token = localStorage.getItem("jwt");
    const indicator = document.getElementById("login-indicator");

    if(token) {
        indicator.style.display = "block";
    }else {
        indicator.style.display = "none";
    }
});

