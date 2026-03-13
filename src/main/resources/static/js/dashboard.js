(function($) {

    var initSwiper = function(){
        var swiper = new Swiper(".main-swiper",{
            speed:500,
            pagination: {
                el: ".swiper-pagination",
                clickable :true,
            }
        });
    };

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



    initSwiper();
    brand_swiper();
})(jQuery);




fetch("http://localhost:8080/item/showAllItems")
.then(response => response.json())
.then(data => {
    let container = document.getElementById("itemcontainer");

    console.log(data)
    data.forEach(item =>{
    let div = document.createElement("div")
    div.innerHTML = "ID: " + item.item_id + ", Brand ID: " + item.brand_item_id + ", quantity: " + item.item_quantity;
    console.log( "ID: " + item.item_id + ", Brand ID: " + item.brand_item_id + ", quantity: " + item.item_quantity)
    console.log( "ID: " + item.item_id + ", Brand ID: " + item.brand_item_id + ", quantity: " + item.item_quantity)

    container.appendChild(div);
    })
})