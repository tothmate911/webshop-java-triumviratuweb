import {dataHandler} from "./data_handler.js";

function main() {
    let addToCartButtons = document.querySelectorAll(".add-to-cart-button");
    for (let button of addToCartButtons) {
        button.addEventListener("click", function () {
            dataHandler._api_post("/cart", 1, )
            alert("ho");
        })
    }
}

main();