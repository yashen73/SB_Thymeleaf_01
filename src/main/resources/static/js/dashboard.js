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

    console.log( "ID: " + item.item_id + ", Brand ID: " + item.brand_item_id + ", quantity: " + item.item_quantity)
    console.log( "ID: " + item.item_id + ", Brand ID: " + item.brand_item_id + ", quantity: " + item.item_quantity)


        html += `
                <div class="col">
                    <div class="product-item">
                        <figure>
                            <a href="#">
                                <img src="images/product-thumbnails/item1_1.jpg" class="tab-image">
                            </a>
                        </figure>
                        <h3>Shirt_01</h3>
                        <span class="qty">20pcs</span>
                        <span class="price">2500.00/=</span>
                    </div>
                </div>
            `;

    })
    productitem.innerHTML = html;
})
