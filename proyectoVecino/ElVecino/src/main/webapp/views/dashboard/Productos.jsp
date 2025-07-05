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
        h2 {
            margin-bottom: 1.5rem;
        }

        .add-btn {
            background-color: #28a745;
            color: #fff;
            padding: 10px 16px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            margin-bottom: 1.2rem;
        }

        .tabla-actividad {
            width: 100%;
            border-collapse: collapse;
        }

        .tabla-actividad th, .tabla-actividad td {
            padding: 12px;
            border: 1px solid #ddd;
            text-align: center;
        }

        .tabla-actividad th {
            background-color: #000;
            color: #fff;
        }

        .acciones i {
            font-size: 18px;
            margin: 0 8px;
            cursor: pointer;
        }

        .acciones i.edit {
            color: #007bff;
        }

        .acciones i.delete {
            color: #dc3545;
        }

        .modal {
            position: fixed;
            top: 0; left: 0; right: 0; bottom: 0;
            background-color: rgba(0,0,0,0.5);
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

        .modal-content input, .modal-content select {
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

        .empty-table {
            text-align: center;
            color: #777;
            font-style: italic;
            padding: 10px;
        }
    </style>
</head>
<body>
    <div class="container">
<jsp:include page="../components/Navbar.jsp" />

<main class="main-content">
    <section class="dashboard">
        <h2>Gestión de Productos</h2>

        <button class="add-btn" onclick="abrirModalAgregar()">Agregar Producto</button>

        <table class="tabla-actividad">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Categoría</th>
                    <th>Precio</th>
                    <th>Stock</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
            <c:choose>
                <c:when test="${not empty productos}">
                    <c:forEach var="p" items="${productos}">
                        <tr>
                            <td>${p.idProducto}</td>
                            <td>${p.nombre}</td>
                            <td>${p.categoria}</td>
                            <td>$${p.precio}</td>
                            <td>${p.stock}</td>
                            <td class="acciones">
                                <i class="edit" onclick="abrirModalEditar('${p.idProducto}', '${p.nombre}', '${p.categoria}', '${p.precio}', '${p.stock}')">✏️</i>
                                <i class="delete" onclick="abrirModalEliminar('${p.idProducto}')">🗑️</i>
                            </td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr><td colspan="6" class="empty-table">No hay productos registrados.</td></tr>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
    </section>
</main>

<!-- Modal Agregar -->
<div id="modalAgregar" class="modal hidden">
    <div class="modal-content">
        <h3>Registrar Producto</h3>
        <form action="productoController" method="post">
            <input type="hidden" name="accion" value="agregar" />
            <label>Nombre:</label>
            <input type="text" name="nombre" required />
            <label>Categoría:</label>
            <input type="text" name="categoria" required />
            <label>Precio:</label>
            <input type="number" name="precio" required />
            <label>Stock:</label>
            <input type="number" name="stock" required />
            <div class="modal-actions">
                <button type="submit">Registrar</button>
                <button type="button" onclick="cerrarModal('modalAgregar')">Cancelar</button>
            </div>
        </form>
    </div>
</div>

<!-- Modal Editar -->
<div id="modalEditar" class="modal hidden">
    <div class="modal-content">
        <h3>Editar Producto</h3>
        <form action="productoController" method="post">
            <input type="hidden" name="accion" value="actualizar" />
            <input type="hidden" name="idProducto" id="editId" />
            <label>Nombre:</label>
            <input type="text" name="nombre" id="editNombre" required />
            <label>Categoría:</label>
            <input type="text" name="categoria" id="editCategoria" required />
            <label>Precio:</label>
            <input type="number" name="precio" id="editPrecio" required />
            <label>Stock:</label>
            <input type="number" name="stock" id="editStock" required />
            <div class="modal-actions">
                <button type="submit">Actualizar</button>
                <button type="button" onclick="cerrarModal('modalEditar')">Cancelar</button>
            </div>
        </form>
    </div>
</div>

<!-- Modal Eliminar -->
<div id="modalEliminar" class="modal hidden">
    <div class="modal-content">
        <h3>¿Eliminar producto?</h3>
        <form action="productoController" method="get">
            <input type="hidden" name="accion" value="eliminar" />
            <input type="hidden" name="idProducto" id="deleteId" />
            <div class="modal-actions">
                <button type="submit">Sí, eliminar</button>
                <button type="button" onclick="cerrarModal('modalEliminar')">Cancelar</button>
            </div>
        </form>
    </div>
</div>

<jsp:include page="../components/Footer.jsp" />

<script>
    function abrirModalAgregar() {
        document.getElementById('modalAgregar').classList.remove('hidden');
    }

    function abrirModalEditar(id, nombre, categoria, precio, stock) {
        document.getElementById('editId').value = id;
        document.getElementById('editNombre').value = nombre;
        document.getElementById('editCategoria').value = categoria;
        document.getElementById('editPrecio').value = precio;
        document.getElementById('editStock').value = stock;
        document.getElementById('modalEditar').classList.remove('hidden');
    }

    function abrirModalEliminar(id) {
        document.getElementById('deleteId').value = id;
        document.getElementById('modalEliminar').classList.remove('hidden');
    }

    function cerrarModal(id) {
        document.getElementById(id).classList.add('hidden');
    }
</script>
<div/>
</body>
</html>
