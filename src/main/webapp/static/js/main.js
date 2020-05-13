function addElement(id) {
    const data = {id: id, type: "add"};
    api_post("/cartEdit", data, changePrice);
    addCounter(id);
}

function changePrice(data){
    document.getElementById("fullPrice").innerText = "Full Price: " + data.fullPrice;
}

function removeElement(id) {
    const data = {id: id, type: "remove"};
    api_post("/cartEdit", data, changePrice)
    removeCounter(id);
    removeCartCounter();
}

function addToCart(id, price) {
    api_get("/add-to-cart/?id=" + id)
    addCartCounter();
    addProductToCart(id)
    changeFullPrice(price, true)
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

function addProductToCart(id){
    const productQuantity = document.getElementById("count" + id);

    if (productQuantity === null){
        buildProduct(id)
    } else {
        productQuantity.innerText = (parseInt(productQuantity.innerText) + 1).toString();
    }
}

function buildProduct(id){
    const product = document.getElementById("productData" + id);
    const productDataFloatPrice = parseFloat(product.getAttribute("data-float-price"));
    const productDataName = product.getAttribute("data-name");
    const productDataPrice = product.getAttribute("data-price");
    const container = document.getElementById("cartModal");
    const cardBody = document.getElementById("productList");



    let content = document.createElement("div");
    content.classList.add("modalContent");
    content.id = id;

    let productImage = document.createElement("div");
    productImage.classList.add("productImage");
    let image = document.createElement("img");
    image.src = "/static/img/product_" + id + ".jpg";
    image.style.width = "100px";
    image.style.height = "100px";
    productImage.appendChild(image);

    let productName = document.createElement("div");
    productName.classList.add("productName");
    productName.classList.add("text-center");
    productName.classList.add("center");
    productName.classList.add("defaultFontSize");
    productName.innerText = productDataName;

    let productPrice = document.createElement("div");
    productPrice.classList.add("productPrice");
    productPrice.classList.add("text-center");
    productPrice.classList.add("center");
    productPrice.classList.add("defaultFontSize");
    productPrice.innerText = productDataPrice;

    let productQuantity = document.createElement("div");
    productQuantity.id = "count" + id;
    productQuantity.classList.add("productQuantity");
    productQuantity.classList.add("text-center");
    productQuantity.classList.add("center");
    productQuantity.classList.add("defaultFontSize");
    productQuantity.innerText = "1";

    let plusButton = document.createElement("div");
    let plusImage = document.createElement("img");
    plusImage.src = "/static/img/addProduct.png";
    plusImage.classList.add("changeProductQuantity");
    plusImage.classList.add("text-center");
    plusImage.classList.add("center");
    plusImage.addEventListener("click", function () {
        addElement(id, productDataFloatPrice);
    });
    plusImage.addEventListener("mouseover", function () {
        this.src = "/static/img/addProductOnMouse.png";
    });
    plusImage.addEventListener("mouseout", function () {
        this.src = "/static/img/addProduct.png";
    });
    plusButton.appendChild(plusImage)

    let minusButton = document.createElement("div");
    let minusImage = document.createElement("img");
    minusImage.src = "/static/img/deleteProduct.png";
    minusImage.classList.add("changeProductQuantity");
    minusImage.classList.add("text-center");
    minusImage.classList.add("center");
    minusImage.addEventListener("click", function () {
        removeElement(id, productDataFloatPrice)
    });
    minusImage.addEventListener("mouseover", function () {
        this.src = "/static/img/deleteProductOnMouse.png";
    });
    minusImage.addEventListener("mouseout", function () {
        this.src = "/static/img/deleteProduct.png";
    });
    minusButton.appendChild(minusImage);

    content.appendChild(productImage);
    content.appendChild(productName);
    content.appendChild(productPrice);
    content.appendChild(productQuantity);
    content.appendChild(plusButton);
    content.appendChild(minusButton);

    if (cardBody === null){
        let newCardBody = document.createElement("div");
        newCardBody.id = "productList";
        newCardBody.classList.add("productList");
        newCardBody.appendChild(content);
        container.appendChild(newCardBody);
    } else {
        cardBody.appendChild(content);
        container.appendChild(cardBody);
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
        .then(response => response.json())
        .then(data => callback(data))
}
