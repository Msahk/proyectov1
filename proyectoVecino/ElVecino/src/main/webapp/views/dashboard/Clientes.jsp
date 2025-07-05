<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="models.usuario" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
    <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined" rel="stylesheet" />
    <style>
        .tabla-clientes {
            width: 100%;
            border-collapse: collapse;
        }

        .tabla-clientes th, .tabla-clientes td {
            padding: 12px;
            border: 1px solid #ccc;
            text-align: center;
        }

        .tabla-clientes th {
            background-color: #000;
            color: white;
        }

        .empty-table {
            text-align: center;
            color: #777;
        }

        .btn-registrar {
            background-color: #28a745;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            margin-bottom: 1rem;
        }

        .action-buttons {
            display: flex;
            justify-content: center;
            gap: 10px;
        }

        .icon-btn {
            background: none;
            border: none;
            cursor: pointer;
            font-size: 20px;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 6px;
            border-radius: 5px;
            transition: background 0.2s;
        }

        .edit-btn {
            color: #007bff;
        }

        .delete-btn {
            color: #dc3545;
        }

        .edit-btn:hover {
            background-color: rgba(0, 123, 255, 0.1);
        }

        .delete-btn:hover {
            background-color: rgba(220, 53, 69, 0.1);
        }

        .modal {
            position: fixed;
            top: 0; left: 0; right: 0; bottom: 0;
            background-color: rgba(0,0,0,0.5);
            display: flex;
            align-items: center;
            justify-content: center;
            z-index: 10;
        }

        .modal.hidden { display: none; }

        .modal-content {
            background-color: #fff;
            padding: 2rem;
            border-radius: 10px;
            width: 90%;
            max-width: 500px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.3);
        }

        .modal-content input,
        .modal-content select {
            width: 100%;
            padding: 8px;
            margin-bottom: 1rem;
        }

        .modal-content button {
            padding: 8px 12px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
        }

        .modal-content button[type="submit"] {
            background-color: #28a745;
            color: white;
        }

        .modal-content button[type="button"] {
            background-color: #6c757d;
            color: white;
        }
        
        
    </style>
</head>
<body>
<div class="container">
    <jsp:include page="../components/Navbar.jsp" />

    <main class="main-content">
        <section class="dashboard">
            <h2>Gestión de Clientes</h2>
            <button class="btn-registrar" onclick="document.getElementById('modalAgregar').classList.remove('hidden')">Registrar nuevo cliente</button>

            <table class="tabla-clientes">
                <thead>
                    <tr>
                        <th>ID</th><th>Nombre</th><th>Apellidos</th><th>Teléfono</th><th>Dirección</th><th>Categoría</th>
                        <th>Fecha Registro</th><th>Límite Créditos</th><th>Créditos Actuales</th><th>ID Usuario</th><th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty clientes}">
                            <c:forEach var="c" items="${clientes}">
                                <tr>
                                    <td>${c.idCliente}</td>
                                    <td>${c.nombre}</td>
                                    <td>${c.apellidos}</td>
                                    <td>${c.telefono}</td>
                                    <td>${c.direccion}</td>
                                    <td>${c.categoriaCrediticia}</td>
                                    <td>${c.fechaRegistro}</td>
                                    <td>${c.limiteCreditos}</td>
                                    <td>${c.creditosActuales}</td>
                                    <td>${c.idUsuario}</td>
                                    <td>
                                        <div class="action-buttons">
                                            <button class="icon-btn edit-btn" title="Editar" onclick="abrirEditar('${c.idCliente}', '${c.nombre}', '${c.apellidos}', '${c.telefono}', '${c.direccion}', '${c.categoriaCrediticia}', '${c.limiteCreditos}', '${c.creditosActuales}')">
                                                <span class="material-symbols-outlined">edit</span>
                                            </button>
                                            <form action="clienteController" method="get" style="display:inline;">
                                                <input type="hidden" name="accion" value="eliminar">
                                                <input type="hidden" name="idCliente" value="${c.idCliente}">
                                                <button class="icon-btn delete-btn" type="submit" onclick="return confirm('¿Estás seguro de eliminar este cliente?')">
                                                    <span class="material-symbols-outlined">delete</span>
                                                </button>
                                            </form>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr><td colspan="11" class="empty-table">No hay clientes registrados.</td></tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </section>
    </main>

    <!-- Modal Agregar -->
    <div id="modalAgregar" class="modal hidden">
        <form action="clienteController" method="post" class="modal-content">
            <h3>Registrar Cliente</h3>
            <input type="hidden" name="accion" value="agregar">
            <label>Nombre:</label><input type="text" name="nombre" required>
            <label>Apellidos:</label><input type="text" name="apellidos" required>
            <label>Teléfono:</label><input type="text" name="telefono" required>
            <label>Dirección:</label><input type="text" name="direccion">
            <label>Categoría:</label>
            <select name="categoriaCrediticia" required>
                <option value="A">A</option><option value="B">B</option><option value="C">C</option>
            </select>
            <label>Límite de Créditos:</label><input type="number" name="limiteCreditos" required>
            <label>Créditos Actuales:</label><input type="number" name="creditosActuales" required>
            <div style="text-align: right;">
                <button type="submit">Registrar</button>
                <button type="button" onclick="document.getElementById('modalAgregar').classList.add('hidden')">Cancelar</button>
            </div>
        </form>
    </div>

    <!-- Modal Editar -->
    <div id="modalEditar" class="modal hidden">
        <form action="clienteController" method="post" class="modal-content">
            <h3>Editar Cliente</h3>
            <input type="hidden" name="accion" value="actualizar">
            <input type="hidden" name="idCliente" id="editId">
            <label>Nombre:</label><input type="text" name="nombre" id="editNombre" required>
            <label>Apellidos:</label><input type="text" name="apellidos" id="editApellidos" required>
            <label>Teléfono:</label><input type="text" name="telefono" id="editTelefono" required>
            <label>Dirección:</label><input type="text" name="direccion" id="editDireccion">
            <label>Categoría:</label>
            <select name="categoriaCrediticia" id="editCategoria" required>
                <option value="A">A</option><option value="B">B</option><option value="C">C</option>
            </select>
            <label>Límite de Créditos:</label><input type="number" name="limiteCreditos" id="editLimite" required>
            <label>Créditos Actuales:</label><input type="number" name="creditosActuales" id="editCreditos" required>
            <div style="text-align: right;">
                <button type="submit">Actualizar</button>
                <button type="button" onclick="document.getElementById('modalEditar').classList.add('hidden')">Cancelar</button>
            </div>
        </form>
    </div>

    <jsp:include page="../components/Footer.jsp" />
</div>
<script>
    function abrirEditar(id, nombre, apellidos, telefono, direccion, categoria, limite, creditos) {
        document.getElementById('modalEditar').classList.remove('hidden');
        document.getElementById('editId').value = id;
        document.getElementById('editNombre').value = nombre;
        document.getElementById('editApellidos').value = apellidos;
        document.getElementById('editTelefono').value = telefono;
        document.getElementById('editDireccion').value = direccion;
        document.getElementById('editCategoria').value = categoria;
        document.getElementById('editLimite').value = limite;
        document.getElementById('editCreditos').value = creditos;
    }
</script>
</body>
</html>
