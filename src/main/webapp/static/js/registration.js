function openRegistrationModal() {
    const registrationModal = document.getElementById("registration-modal");
    registrationModal.classList.remove("hide");
    registrationModal.classList.add("display");
}

function main() {
    console.log("registration");

    let registrationButton = document.getElementById("registration-button");
    registrationButton.addEventListener("click", openRegistrationModal);
}
main()