const itemdetailsBtn = document.getElementById('showtrendingItems');
const customerdetailBtn = document.getElementById('showAllCustomers');

itemdetailsBtn.addEventListener('click', getDataforItems);
customerdetailBtn.addEventListener('click', getDataforCustomers);

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


async function getDataforItems(){

    try{
        const res = await fetch("http://localhost:8080/item/showAllItems");

        if(!res.ok) throw new Error (`HTTP ${res.status} ${res.statusText}`);
        const data = await res.json();
        displayOnItemTable(data);

    } catch (err) {
        console.error(err);
    }
}

function displayOnItemTable(data) {
    const tablbody = document.getElementById("show-all-items-table-body");
    tablbody.innerHTML = "";

    data.forEach( item => {
        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${item.item_id}</td>
            <td>${item.brand_item_id}</td>
            <td>${item.item_name}</td>
            <td>${item.item_quantity}</td>
            <td>${item.item_price}</td>
            <td>${item.item_price}</td>
            <td>${item.item_thumbnailimg_name}</td>
        `;
        tablbody.appendChild(row);
    });
}


async function getDataforCustomers(){

    try{
        const res1 = await fetch("http://localhost:8080/cust/ShowAllCustomers");

        if(!res1.ok) throw new Error (`HTTP ${res1.status} ${res1.statusText}`);
        const data1 = await res1.json();
        displayOnCustomerTable(data1);
        console.log(data1);

    } catch (err) {
        console.error(err);
    }
}

function displayOnCustomerTable(data1) {
    const tableBodyforCustomers = document.getElementById("show-all-customers");
    tableBodyforCustomers.innerHTML = "";

    data1.forEach( cust => {
        const row1 = document.createElement("tr");

        row1.innerHTML = `
            <td>${cust.id}</td>
            <td>${cust.name}</td>
            <td>${cust.mail}</td>
            <td>${cust.tele}</td>
        `;

        tableBodyforCustomers.appendChild(row1);
    });
}


