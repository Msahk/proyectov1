document.addEventListener("DOMContentLoaded", () => {
    const dropdowns = document.querySelectorAll(".dropdown");

    dropdowns.forEach(drop => {
        const toggle = drop.querySelector(".dropdown-toggle");
        toggle.addEventListener("click", (e) => {
            e.stopPropagation();
            dropdowns.forEach(d => d.classList.remove("show"));
            drop.classList.toggle("show");
        });
    });

    document.addEventListener("click", () => {
        dropdowns.forEach(drop => drop.classList.remove("show"));
    });

    document.querySelectorAll(".delete-btn").forEach(btn => {
        btn.addEventListener("click", (e) => {
            e.preventDefault();
            const id = btn.getAttribute("data-id");
            if (confirm("¿Estás seguro de eliminar este usuario?")) {
                window.location.href = `usuarioController?accion=eliminar&docUsuario=${id}`;
            }
        });
    });

    document.querySelectorAll(".edit-btn").forEach(btn => {
        btn.addEventListener("click", (e) => {
            e.preventDefault();
            const id = btn.getAttribute("data-id");
            window.location.href = `usuarioController?accion=editarVista&docUsuario=${id}`;
        });
    });

    const addUserBtn = document.getElementById("addUserBtn");
    if (addUserBtn) {
        addUserBtn.addEventListener("click", () => {
            window.location.href = "usuarioController?accion=registrarVista";
        });
    }

    document.querySelectorAll(".dropdown-menu a").forEach(link => {
        link.addEventListener("click", () => {
            dropdowns.forEach(drop => drop.classList.remove("show"));
        });
    });
});

