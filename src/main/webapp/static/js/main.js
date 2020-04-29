function addElement(id, price) {
    api_get("/cartEdit/?id=" + id + "&type=add");
    addCounter(id);
    // addCartCounter()
    changeFullPrice(price, true)
}

function removeElement(id, price) {
    api_get("/cartEdit/?id=" + id + "&type=remove");
    removeCounter(id);
    removeCartCounter();
    changeFullPrice(price, false)
}

function addToCart(id) {
    api_get("/add-to-cart/?id=" + id)
    addCartCounter();
}

function addCounter(id) {
    let productCount = document.getElementById("count" + id);
    productCount.innerText = (parseInt(productCount.innerText) + 1).toString();
    let cartCount = document.getElementById("cartCounter");
    cartCount.innerText = (parseInt(cartCount.innerText) + 1).toString();
}

function removeCounter(id) {
    let productCount = document.getElementById("count" + id);
    productCount.innerText = (parseInt(productCount.innerText) - 1).toString();
    if (productCount.innerText === "0") {
        document.getElementById(id).remove();
    }

}

function changeFullPrice(price, add) {
    let fullPrice = document.getElementById("fullPrice");
    let newPrice;
    if (add){
        newPrice = parseFloat(fullPrice.getAttribute("data-full-price")) + price;
    } else {
        newPrice = parseFloat(fullPrice.getAttribute("data-full-price")) - price;
    }
    if (newPrice === 0){
        fullPrice.innerText = "Full Price: " + 0.0 + " $";
    } else {
        fullPrice.innerText = "Full Price: " + newPrice.toFixed(2) + " $";
    }
    fullPrice.setAttribute("data-full-price", newPrice);
}


function removeCartCounter(){
    let cartCount = document.getElementById("cartCounter");
    cartCount.innerText = (parseInt(cartCount.innerText) - 1).toString();
    if (cartCount.innerText === "0"){
        cartCount.innerText = "";
        document.getElementById("cartOpen").style.color = "black";
    }
}

function addCartCounter() {
    let cartCount = document.getElementById("cartCounter");
    if (cartCount.innerText === ""){
        cartCount.innerText = "1";
    } else {
        cartCount.innerText = (parseInt(cartCount.innerText) + 1).toString();
    }
    if (parseInt(cartCount.innerText) >= 0){
        document.getElementById("cartOpen").style.color = "red";
    }
}

function api_get(url) {
    fetch(url, {
        method: 'GET',
        credentials: 'same-origin'
    }).then(() => {})

}

function api_post(url, data, callback) {
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(function () {
            callback()
        })
}
