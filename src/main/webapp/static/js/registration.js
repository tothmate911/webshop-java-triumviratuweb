function openRegistrationModal() {
    const registrationModal = document.getElementById("registration-modal");
    registrationModal.classList.remove("hide");
    registrationModal.classList.add("display");
}

function main() {
    console.log("registration");

    let registrationButton = document.getElementById("registration-button");
    registrationButton.addEventListener("click", openRegistrationModal);

    // const openButton = document.getElementById("cartOpen");
    // const closeButton = document.getElementById("cartClose");
    // const container = document.getElementById("container");
    //
    // openButton.onclick = function () {
    //     modal.classList.remove("hide");
    //     modal.classList.add("display");
    //     container.style.pointerEvents = "none";
    //     container.style.opacity = "0.5";
    // }
    //
    // closeButton.onclick = function () {
    //     modal.classList.remove("display");
    //     modal.classList.add("hide");
    //     container.style.opacity = "1";
    //     container.style.pointerEvents = "auto";
    // }
}

main();