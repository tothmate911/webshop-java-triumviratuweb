function openRegistrationModal() {
    let registrationModal = document.getElementById("registration-modal");
    registrationModal.classList.remove("hide");
}

function closeRegistrationModal() {
    let registrationModal = document.getElementById("registration-modal");
    registrationModal.classList.add("hide");
}

function main() {
    let registrationButton = document.getElementById("registration-button");
    registrationButton.addEventListener("click", openRegistrationModal);

    let cancelRegistrationModalButton = document.querySelector(".cancel-button");
    cancelRegistrationModalButton.addEventListener("click", closeRegistrationModal);
}

main();