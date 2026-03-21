document.getElementById("trendingItemsInsertingForm").addEventListener("submit", function(e){

    e.preventDefault(); //stoping Relod the page

    let data = {
        brand_item_id :document.getElementById("brand_item_id").value,
        item_quantity : document.getElementById("item_quantity").value,
        item_price : document.getElementById("item_price").value,
        item_sold_count : document.getElementById("item_sold_count").value
    };

    console.log(data)

    fetch("http://localhost:8080/product/addProductOnTrendingItems", {
        method :"POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
    .then(response => response.text())
    .then(result =>{

    setTimeout(() => {
                document.getElementById("message").innerHTML = result;
    },3000)
        document.getElementById("message").innerHTML = result;
        document.getElementById("trendingItemsInsertingForm").reset();
    })
    .catch(error=> console.error(error));

});