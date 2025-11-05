document.addEventListener("click", function (e) {
    if (e.target.classList.contains("edit-btn")) {
        const id = e.target.getAttribute("data-id");
        actualizarUsuario(id);
    }

    if (e.target.classList.contains("delete-btn")) {
        const id = e.target.getAttribute("data-id");
        eliminarUsuario(id);
    }
});