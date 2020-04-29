function addElement(id) {
    api_get("/cartEdit/?id=" + id + "&type=add", addCounter(id));
}

function removeElement(id) {
    api_get("/cartEdit/?id=" + id + "&type=remove", removeCounter(id));
}

function addToCart(id) {
    api_get("/add-to-cart/?id=" + id)
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
    let cartCount = document.getElementById("cartCounter");
    cartCount.innerText = (parseInt(cartCount.innerText) - 1).toString();
    if (productCount.innerText === "0") {
        cartCount.innerText = "";
        document.getElementById("cartOpen").style.color = "black";
    }
}

function api_get(url) {
    fetch(url, {
        method: 'GET',
        credentials: 'same-origin'
    }).then(r => {
    })

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