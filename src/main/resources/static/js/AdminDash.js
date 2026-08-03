

document.getElementById("showtrendingItems").addEventListener("click",async function(e) {
    e.preventDefault();

   try {
        let responseForShowAllItems = await fetch("http://localhost:8080/item/showAllItems");
            if( !responseForShowAllItems.ok){
                throw new Error("Failed to fetch items");
            }

            let finalResponseForShowAllItems = await responseForShowAllItems.json();

            console.log(finalResponseForShowAllItems);

            let tableRowsForShowingAvailbaleItems = "";

            finalResponseForShowAllItems.forEach(item => {
                tableRowsForShowingAvailbaleItems +=  `<tr>
                                                        <td>${item.item_id}</td>
                                                        <td>${item.brand_item_id}</td>
                                                        <td>${item.item_name}</td>
                                                        <td>${item.item_quantity}</td>
                                                        <td>${item.item_price}</td>
                                                        <td>${item.item_sold_count}</td>
                                                        <td>${item.item_thumbnailimg_name}</td>
                                                                                         </tr>`
            })

            let tablebody = document.getElementById("show-all-items-table-body");
            tablebody.innerHTML = tableRowsForShowingAvailbaleItems;

   } catch (error) {
        console.log(error);
   }
})

document.getElementById("trendingItemsInsertingForm").addEventListener("submit", function(e){

    e.preventDefault(); //stoping Relod the page

    let item_thumbnail_img = document.getElementById("thumbnail_img");
    let item_detail_img1 = document.getElementById("detail_img01");
    let item_detail_img2 = document.getElementById("detail_img02");

    let fileForThumbnailImg = item_thumbnail_img.files[0];
    let fileForDetailImg1 = item_detail_img1.files[0];
    let fileForDetailImg2 = item_detail_img2.files[0];


    let addingDTO = {
        item_name : document.getElementById("item_name").value,
        brand_item_id :document.getElementById("brand_item_id").value,
        item_quantity : document.getElementById("item_quantity").value,
        item_price : document.getElementById("item_price").value,
        item_sold_count : document.getElementById("item_sold_count").value,
        item_thumbnailimg_name : document.getElementById("thumbnail_img_name").value,
        item_detail_img1_name : document.getElementById("detail_img01_name").value,
        item_detail_img2_name : document.getElementById("detail_img02_name").value
    };


   console.log(addingDTO);

   let sendingDataForInsertingProduct = new FormData();

    sendingDataForInsertingProduct.append("addingDTO", new Blob([JSON.stringify(addingDTO)], { type: "application/json" }));
    sendingDataForInsertingProduct.append("fileForThumbnailImg", fileForThumbnailImg);
    sendingDataForInsertingProduct.append("fileForDetailImg1", fileForDetailImg1);
    sendingDataForInsertingProduct.append("fileForDetailImg2", fileForDetailImg2);

    fetch("http://localhost:8080/item/addProductOnTrendingItems", {
        method :"POST",
        body: sendingDataForInsertingProduct
    })
    .then(response => response.text())
    .then(result =>{

    setTimeout(() => {
                document.getElementById("messageforAddingItem").innerHTML = result;
    },3000)
        document.getElementById("messageforAddingItem").innerHTML = result;
        document.getElementById("trendingItemsInsertingForm").reset();
        thumbanail_img_preview.src="";
        detail_img01_preview.src="";
        detail_img02_preview.src="";
    })
    .catch(error=> console.error(error));

});


document.getElementById("trendingItemsEditingForm").addEventListener("submit", function(e){

    e.preventDefault();

    let editingData = {
        item_id : document.getElementById("editing_item_id").value,
        item_price : document.getElementById("editing_item_quantity").value,
        item_price : document.getElementById("editing_item_price").value,
        item_sold_count : document.getElementById("editing_item_sold_count").value
    };

    console.log(editingData);

    fetch("", {
        method : "POST",
        headers : {
            "Content-Type":"Application/json"
        },
        body: JSON.stringify(editingData)
    })
    .then(response => response.text())
    .then(result => {

        setTimeout(() => {
            document.getElementById("messageforEditingItem").innerHTML = result;
        },3000)
        document.getElementById("messageforEditingItem").reset();
    })
})

document.getElementById("trendingItemsDeletingForm").addEventListener("submit", function(e){
    e.preventDefault();

    let deletingData ={
        item_id : document.getElementById("deleting_item_id").value 
    };

    fetch("http://localhost:8080/item/deleteAnItem", {
        method :"DELETE",
        headers : {
            "Content-Type" : "Application/json"
        },
        body : JSON.stringify(deletingData)
    })
    .then(response => {
        if(response.status === 200) {
        console.log(response.status);
        document.getElementById("trendingItemsDeletingForm").reset();
        document.getElementById("messageforDeletingItem").innerText = "Successfully deleted";
        }
    })
    .catch (error => alert("There is no Item already from that id..."))
})


//showing Preview of inserted images & names of them for inserting new product

    // ----for thumbnail Images
let thumbanail_img = document.getElementById("thumbnail_img");
let thumbanail_img_name = document.getElementById("thumbnail_img_name");
let thumbanail_img_preview = document.getElementById("thumbnail-image-preview");
let thumbnail_img_display_name = document.getElementById("thumbnail_img_display_name");

thumbanail_img.addEventListener("change", function() {
    let file_thumbnail = this.files[0];

    if(file_thumbnail) {
        const reader = new FileReader();

        reader.addEventListener("load", function() {
            thumbanail_img_preview.src = this.result;
            thumbanail_img_preview.style.display = "block";
        });

        reader.readAsDataURL(file_thumbnail);
    }
})
//Display thumbnail image name
thumbnail_img_name.addEventListener("input", function() {
    thumbnail_img_display_name.textContent = this.value;
})



    //for detailed images -------------------------------

        //for detailed image 01
let detail_img01 = document.getElementById("detail_img01");
let detail_img01_name = document.getElementById("detail_img01_name");
let detail_img01_preview = document.getElementById("detail-image01-preview");
let detail_img01_display_name = document.getElementById("detail_img01_display_name");

detail_img01.addEventListener("change", function() {
    let file_detail_img1 = this.files[0];

    if(file_detail_img1) {
        const reader1 = new FileReader();

        reader1.addEventListener("load", function() {
            detail_img01_preview.src = this.result;
            detail_img01_preview.style.display ="block";
        });

        reader1.readAsDataURL(file_detail_img1);
    }
})
//Display detail image name
detail_img01_name.addEventListener("input", function() {
    detail_img01_display_name.textContent = this.value;
})


        //for detailed image 02
let detail_img02 = document.getElementById("detail_img02");
let detail_img02_name = document.getElementById("detail_img02_name");
let detail_img02_preview = document.getElementById("detail-image02-preview");
let detail_img02_display_name = document.getElementById("detail_img02_display_name");

detail_img02.addEventListener("change", function() {
    let file_detail_img02 = this.files[0];

    if(file_detail_img02) {
        const reader2 = new FileReader();

        reader2.addEventListener("load", function() {
            detail_img02_preview.src = this.result;
            detail_img02_preview.style.display ="block";
        });

        reader2.readAsDataURL(file_detail_img02);
    }
})
//Display detail image name
detail_img02_name.addEventListener("input", function() {
    detail_img02_display_name.textContent = this.value;
})



//showing Preview of inserted images & names of them for editing exisiting product

    // ----for thumbnail Images
let thumbanail_img_forediting = document.getElementById("thumbnail_img_forediting");
let thumbanail_img_name_forediting = document.getElementById("thumbnail_img_name_forediting");
let thumbanail_img_preview_forediting = document.getElementById("thumbnail-image-preview_forediting");
let thumbnail_img_display_name_forediting = document.getElementById("thumbnail_img_display_name_forediting");

thumbanail_img_forediting.addEventListener("change", function() {
    let file_thumbnail_forediting = this.files[0];

    if(file_thumbnail_forediting) {
        const reader_forediting = new FileReader();

        reader_forediting.addEventListener("load", function() {
            thumbanail_img_preview_forediting.src = this.result;
            thumbanail_img_preview_forediting.style.display = "block";
        });

        reader_forediting.readAsDataURL(file_thumbnail_forediting);
    }
})
//Display thumbnail image name
thumbnail_img_name_forediting.addEventListener("input", function() {
    thumbnail_img_display_name_forediting.textContent = this.value;
})



    //for detailed images -------------------------------

        //for detailed image 01
let detail_img01_forediting = document.getElementById("detail_img01_forediting");
let detail_img01_name_forediting = document.getElementById("detail_img01_name_forediting");
let detail_img01_preview_forediting = document.getElementById("detail-image01-preview_forediting");
let detail_img01_display_name_forediting = document.getElementById("detail_img01_display_name_forediting");

detail_img01_forediting.addEventListener("change", function() {
    let file_detail_img1_forediting = this.files[0];

    if(file_detail_img1_forediting) {
        const reader1_forediting = new FileReader();

        reader1_forediting.addEventListener("load", function() {
            detail_img01_preview_forediting.src = this.result;
            detail_img01_preview_forediting.style.display ="block";
        });

        reader1_forediting.readAsDataURL(file_detail_img1_forediting);
    }
})
//Display detail image name
detail_img01_name_forediting.addEventListener("input", function() {
    detail_img01_display_name_forediting.textContent = this.value;
})


        //for detailed image 02
let detail_img02_forediting = document.getElementById("detail_img02_forediting");
let detail_img02_name_forediting = document.getElementById("detail_img02_name_forediting");
let detail_img02_preview_forediting = document.getElementById("detail-image02-preview_forediting");
let detail_img02_display_name_forediting = document.getElementById("detail_img02_display_name_forediting");

detail_img02_forediting.addEventListener("change", function() {
    let file_detail_img02_forediting = this.files[0];

    if(file_detail_img02_forediting) {
        const reader2_forediting = new FileReader();

        reader2_forediting.addEventListener("load", function() {
            detail_img02_preview_forediting.src = this.result;
            detail_img02_preview_forediting.style.display ="block";
        });

        reader2_forediting.readAsDataURL(file_detail_img02_forediting);
    }
})
//Display detail image name 
detail_img02_name_forediting.addEventListener("input", function() {
    detail_img02_display_name_forediting.textContent = this.value;
})