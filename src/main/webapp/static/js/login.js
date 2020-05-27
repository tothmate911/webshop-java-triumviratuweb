let loginbutton = document.getElementById("login-button");

function loginProcess(){
    const loginModal = document.getElementById("login-modal");
    loginModal.classList.remove("hide");
    loginModal.classList.add("display");
}



loginbutton.addEventListener("click",loginProcess )
