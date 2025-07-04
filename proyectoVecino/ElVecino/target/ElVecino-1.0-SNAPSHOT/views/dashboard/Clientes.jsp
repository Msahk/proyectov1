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
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
        }

        h2 {
            margin-top: 1rem;
            margin-bottom: 1.5rem;
            text-align: center;
        }

        .btn-registrar {
            background-color: #dc3545;
            color: white;
            padding: 8px 14px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            margin-bottom: 1rem;
        }

        .tabla-clientes {
            width: 100%;
            border-collapse: collapse;
        }

        .tabla-clientes th, .tabla-clientes td {
            padding: 12px;
            border: 1px solid #ccc;
            text-align: left;
        }

        .tabla-clientes th {
            background-color: #f4f4f4;
        }

        .acciones a {
            margin: 0 5px;
            color: #007bff;
            text-decoration: none;
        }

        .acciones a:hover {
            text-decoration: underline;
        }

        .empty-table {
            text-align: center;
            color: #777;
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

        .modal-content {
            background-color: #fff;
            padding: 2rem;
            border-radius: 10px;
            width: 400px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.3);
        }

        .modal-content h3 {
            margin-top: 0;
            margin-bottom: 1rem;
        }

        .modal-content input, .modal-content select {
            width: 100%;
            padding: 8px;
            margin-bottom: 1rem;
        }

        .modal-content button {
            padding: 8px 12px;
            margin-right: 8px;
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

<jsp:include page="../components/Navbar.jsp" />

<main class="main-content">
    <section class="dashboard">
        <h2>Gestión de Clientes</h2>

        <button class="btn-registrar" onclick="document.getElementById('modalAgregar').style.display='flex'">Registrar nuevo cliente</button>

        <table class="tabla-clientes">
            <thead>
            <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Apellidos</th>
                <th>Teléfono</th>
                <th>Dirección</th>
                <th>Categoría</th>
                <th>Fecha Registro</th>
                <th>Límite Créditos</th>
                <th>Créditos Actuales</th>
                <th>ID Usuario</th>
                <th>Acciones</th>
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
                            <td class="acciones">
                                <a href="#" onclick="abrirEditar(
                                    '${c.idCliente}', '${c.nombre}', '${c.apellidos}', '${c.telefono}',
                                    '${c.direccion}', '${c.categoriaCrediticia}',
                                    '${c.limiteCreditos}', '${c.creditosActuales}'
                                )">Editar</a> |
                                <a href="clienteController?accion=eliminar&idCliente=${c.idCliente}"
                                   onclick="return confirm('¿Estás seguro de eliminar este cliente?')">Eliminar</a>
                            </td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td colspan="11" class="empty-table">No hay clientes registrados.</td>
                    </tr>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
    </section>
</main>

<!-- Modal Agregar -->
<div id="modalAgregar" style="display:none;" class="modal">
    <form action="clienteController" method="post" class="modal-content">
        <h3>Registrar Cliente</h3>
        <input type="hidden" name="accion" value="agregar">
        <label>Nombre:</label>
        <input type="text" name="nombre" required>
        <label>Apellidos:</label>
        <input type="text" name="apellidos" required>
        <label>Teléfono:</label>
        <input type="text" name="telefono" required>
        <label>Dirección:</label>
        <input type="text" name="direccion">
        <label>Categoría:</label>
        <select name="categoriaCrediticia" required>
            <option value="A">A</option>
            <option value="B">B</option>
            <option value="C">C</option>
        </select>
        <label>Límite de Créditos:</label>
        <input type="number" name="limiteCreditos" required>
        <label>Créditos Actuales:</label>
        <input type="number" name="creditosActuales" required>
        <button type="submit">Registrar</button>
        <button type="button" onclick="document.getElementById('modalAgregar').style.display='none'">Cancelar</button>
    </form>
</div>

<!-- Modal Editar -->
<div id="modalEditar" style="display:none;" class="modal">
    <form action="clienteController" method="post" class="modal-content">
        <h3>Editar Cliente</h3>
        <input type="hidden" name="accion" value="actualizar">
        <input type="hidden" name="idCliente" id="editId">
        <label>Nombre:</label>
        <input type="text" name="nombre" id="editNombre" required>
        <label>Apellidos:</label>
        <input type="text" name="apellidos" id="editApellidos" required>
        <label>Teléfono:</label>
        <input type="text" name="telefono" id="editTelefono" required>
        <label>Dirección:</label>
        <input type="text" name="direccion" id="editDireccion">
        <label>Categoría:</label>
        <select name="categoriaCrediticia" id="editCategoria" required>
            <option value="A">A</option>
            <option value="B">B</option>
            <option value="C">C</option>
        </select>
        <label>Límite de Créditos:</label>
        <input type="number" name="limiteCreditos" id="editLimite" required>
        <label>Créditos Actuales:</label>
        <input type="number" name="creditosActuales" id="editCreditos" required>
        <button type="submit">Actualizar</button>
        <button type="button" onclick="document.getElementById('modalEditar').style.display='none'">Cancelar</button>
    </form>
</div>

<script>
    function abrirEditar(id, nombre, apellidos, telefono, direccion, categoria, limite, creditos) {
        document.getElementById('modalEditar').style.display = 'flex';
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

<jsp:include page="../components/Footer.jsp" />
</body>
</html>
