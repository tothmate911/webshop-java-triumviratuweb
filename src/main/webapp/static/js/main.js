function addElement(id) {
    let sendData = {
        "id": id
    };
    addCounter(id)
    // api_get("/addToCart")
}

function removeElement(id) {
    let sendData = {
        "id": id
    }
    removeCounter(id)
}

function addCounter(id) {
    let productCount = document.getElementById("count" + id);
    productCount.innerText = (parseInt(productCount.innerText) + 1).toString();
}

function removeCounter(id) {
    let productCount = document.getElementById("count" + id);
    productCount.innerText = (parseInt(productCount.innerText) - 1).toString();
    if (productCount.innerText === "0"){
        document.getElementById(id).remove();
    }
}





function api_get(url, callback) {
    fetch(url, {
        method: 'GET',
        credentials: 'same-origin'
    })
        .then(response => response.json())
        .then(json_response => callback(json_response));
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