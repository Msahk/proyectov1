const nav = document.querySelector("#logo");
const padre = document.querySelector(".contenedor-padre");

nav.addEventListener("click", () => {
    padre.classList.toggle("escondida");
})