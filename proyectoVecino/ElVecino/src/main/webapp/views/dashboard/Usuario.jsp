<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="models.usuario" %>

<%
    usuario usu = (usuario) session.getAttribute("usuario");
    if (usu == null || (!"A".equals(usu.getRol()) && !"E".equals(usu.getRol()))) {
        response.sendRedirect(request.getContextPath() + "/views/formularios/login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <jsp:include page="../components/Header.jsp" />
    <style>
        /* ==== MODALES ==== */
        .modal {
            position: fixed;
            top: 0; left: 0; right: 0; bottom: 0;
            background: rgba(0, 0, 0, 0.5);
            display: flex;
            justify-content: center;
            align-items: center;
            z-index: 1000;
        }

        .modal.hidden {
            display: none;
        }

        .modal-content {
            background: #fff;
            padding: 2rem;
            border-radius: 10px;
            width: 90%;
            max-width: 500px;
        }

        .modal-content h3 {
            margin-top: 0;
        }

        .modal-content input,
        .modal-content select {
            width: 100%;
            margin-bottom: 1rem;
            padding: 8px;
        }

        .modal-actions {
            text-align: right;
        }

        .modal-actions button {
            padding: 8px 12px;
            margin-left: 10px;
        }

        /* ==== TABLA ==== */
        .tabla-actividad {
            width: 100%;
            border-collapse: collapse;
        }

        .tabla-actividad th, .tabla-actividad td {
            padding: 10px;
            border: 1px solid #ccc;
        }

        .tabla-actividad th {
            background-color: #f5f5f5;
        }

        .empty-table {
            text-align: center;
            color: #777;
        }

        /* ==== BOTONES ==== */
        .add-btn {
            background: #28a745;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            margin-bottom: 1rem;
        }

        .dropdown {
            position: relative;
        }

        .dropdown-toggle {
            background: transparent;
            border: none;
            font-size: 20px;
            cursor: pointer;
        }

        .dropdown-menu {
            display: none;
            position: absolute;
            background: white;
            border: 1px solid #ccc;
            right: 0;
            z-index: 10;
        }

        .dropdown:hover .dropdown-menu {
            display: block;
        }

        .dropdown-menu li {
            list-style: none;
        }

        .dropdown-menu a {
            text-decoration: none;
            padding: 8px 12px;
            display: block;
            color: black;
        }

        .dropdown-menu a:hover {
            background: #eee;
        }
    </style>
</head>
<body>

<jsp:include page="../components/Navbar.jsp" />

<main class="main-content">
    <section class="dashboard">
        <h2>Gestión de Usuarios</h2>

        <button class="add-btn" id="addUserBtn">Agregar Usuario</button>

        <table class="tabla-actividad">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Rol</th>
                    <th>Email</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty usuarios}">
                        <c:forEach var="u" items="${usuarios}">
                            <tr>
                                <td>${u.idUsuario}</td>
                                <td>${u.nombreUsuario}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${u.rol == 'A'}">Administrador</c:when>
                                        <c:otherwise>Empleado</c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${u.email}</td>
                                <td>
                                    <div class="dropdown">
                                        <button class="dropdown-toggle">⋮</button>
                                        <ul class="dropdown-menu">
                                            <li><a href="#" class="edit-btn" data-id="${u.idUsuario}">Editar</a></li>
                                            <li><a href="#" class="delete-btn" data-id="${u.idUsuario}">Eliminar</a></li>
                                        </ul>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr><td colspan="5" class="empty-table">No hay usuarios registrados.</td></tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </section>
</main>

<!-- Modal Registro -->
<div id="modalRegistro" class="modal hidden">
    <div class="modal-content">
        <h3>Registrar Usuario</h3>
        <form action="usuarioController" method="post">
            <input type="hidden" name="accion" value="agregar" />
            <label>Nombre:</label>
            <input type="text" name="nombreUsuario" required />
            <label>Rol:</label>
            <select name="rol">
                <option value="A">Administrador</option>
                <option value="E">Empleado</option>
            </select>
            <label>Email:</label>
            <input type="email" name="email" required />
            <label>Contraseña:</label>
            <input type="password" name="password" required />
            <div class="modal-actions">
                <button type="submit">Registrar</button>
                <button type="button" class="close-modal">Cancelar</button>
            </div>
        </form>
    </div>
</div>

<!-- Modal Editar -->
<div id="modalEditar" class="modal hidden">
    <div class="modal-content">
        <h3>Editar Usuario</h3>
        <form action="usuarioController" method="post">
            <input type="hidden" name="accion" value="actualizar" />
            <input type="hidden" name="idUsuario" id="edit_idUsuario" />
            <label>Nombre:</label>
            <input type="text" name="nombreUsuario" id="edit_nombreUsuario" required />
            <label>Rol:</label>
            <select name="rol" id="edit_rol">
                <option value="A">Administrador</option>
                <option value="E">Empleado</option>
            </select>
            <label>Email:</label>
            <input type="email" name="email" id="edit_email" required />
            <label>Contraseña:</label>
            <input type="password" name="password" id="edit_password" required />
            <div class="modal-actions">
                <button type="submit">Actualizar</button>
                <button type="button" class="close-modal">Cancelar</button>
            </div>
        </form>
    </div>
</div>

<!-- Modal Eliminar -->
<div id="modalEliminar" class="modal hidden">
    <div class="modal-content">
        <h3>¿Eliminar usuario?</h3>
        <form action="usuarioController" method="get">
            <input type="hidden" name="accion" value="eliminar" />
            <input type="hidden" name="idUsuario" id="delete_idUsuario" />
            <div class="modal-actions">
                <button type="submit">Sí, eliminar</button>
                <button type="button" class="close-modal">Cancelar</button>
            </div>
        </form>
    </div>
</div>

<jsp:include page="../components/Footer.jsp" />

<script>
    // Abrir modal registro
    document.getElementById("addUserBtn").addEventListener("click", () => {
        document.getElementById("modalRegistro").classList.remove("hidden");
    });

    // Editar
    document.querySelectorAll(".edit-btn").forEach(btn => {
        btn.addEventListener("click", function () {
            const row = this.closest("tr");
            document.getElementById("edit_idUsuario").value = this.dataset.id;
            document.getElementById("edit_nombreUsuario").value = row.children[1].textContent.trim();
            const rolText = row.children[2].textContent.trim();
            document.getElementById("edit_rol").value = rolText.startsWith("A") ? "A" : "E";
            document.getElementById("edit_email").value = row.children[3].textContent.trim();
            document.getElementById("edit_password").value = "";
            document.getElementById("modalEditar").classList.remove("hidden");
        });
    });

    // Eliminar
    document.querySelectorAll(".delete-btn").forEach(btn => {
        btn.addEventListener("click", function () {
            document.getElementById("delete_idUsuario").value = this.dataset.id;
            document.getElementById("modalEliminar").classList.remove("hidden");
        });
    });

    // Cerrar modales
    document.querySelectorAll(".close-modal").forEach(btn => {
        btn.addEventListener("click", () => {
            btn.closest(".modal").classList.add("hidden");
        });
    });
</script>

</body>
</html>
