<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="models.usuarios" %>

<%
    usuarios usu = (usuarios) session.getAttribute("usuarios");
    if (usu == null || ("EV".equals(usu.getRol()))) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
%>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Inventario</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/inventario.css">

    <!-- Declaramos el contextPath en una variable JS global -->
    <script>
        const contextPath = '${pageContext.request.contextPath}';
    </script>
</head>
<body>

<jsp:include page="navbar.jsp" />

<div class="container">
    <div class="page-header">
        <h1>Gestión de Inventario</h1>
    </div>

    <!-- Estadísticas -->
    <div class="stats-grid">
        <div class="stat-card">
            <div class="stat-number">${listaInsumos.size()}</div>
            <div class="stat-label">Total Ingredientes</div>
        </div>
        <div class="stat-card">
            <div class="stat-number">--</div>
            <div class="stat-label">Stock Bajo</div>
        </div>
    </div>

    <!-- Acciones -->
    <div class="actions-bar">
        <div class="search-box">
            <input type="text" class="search-input" placeholder="Buscar ingrediente..." id="buscarIngrediente" oninput="filtrarIngredientes()">
        </div>
        <div>
            <button class="btn btn-primary" onclick="abrirModalNuevo()">➕ Nuevo Ingrediente</button>
        </div>
    </div>

    <!-- Lista dinámica -->
    <div class="inventario-grid" id="inventarioGrid">
        <c:forEach var="i" items="${listaInsumos}">
            <c:set var="stockClass"
                   value="${i.cantidad <= i.stock_min ? 'stock-bajo' : (i.cantidad > i.stock_min * 2 ? 'stock-alto' : 'stock-medio')}" />
            <div class="ingrediente-card ${stockClass}">
                <div class="card-header">
                    <div>
                        <div class="ingrediente-nombre">${i.nombre}</div>
                        <div class="ingrediente-codigo">ID: ${i.id_ins}</div>
                    </div>
                    <span class="stock-badge ${stockClass}-badge">
                        <c:choose>
                            <c:when test="${stockClass == 'stock-bajo'}">Stock Bajo</c:when>
                            <c:when test="${stockClass == 'stock-alto'}">Stock Alto</c:when>
                            <c:otherwise>Stock Medio</c:otherwise>
                        </c:choose>
                    </span>
                </div>
                <div class="stock-info">
                    <div class="stock-item">
                        <div class="stock-value">${i.cantidad}</div>
                        <div class="stock-label">${i.unidad_medida} Disponible</div>
                    </div>
                    <div class="stock-item">
                        <div class="stock-value">${i.stock_min}</div>
                        <div class="stock-label">${i.unidad_medida} Mínimo</div>
                    </div>
                </div>
                <div class="card-actions">
                    <button class="btn btn-secondary btn-small" onclick="abrirModalEditar(${i.id_ins}, '${i.nombre}', '${i.unidad_medida}', ${i.cantidad}, ${i.stock_min})">✏️ Editar</button>
                    <button class="btn btn-danger btn-small" onclick="confirmarEliminar(${i.id_ins})">🗑️ Eliminar</button>
                </div>
            </div>
        </c:forEach>
    </div>

    <div id="noResultados" style="display: none; text-align: center; margin-top: 2rem;">
        <p>😕 No se encontraron ingredientes con ese nombre.</p>
    </div>
</div>

<!-- Modal Crear Ingrediente -->
<div id="modalNuevo" class="modal">
    <form action="${pageContext.request.contextPath}/insumosController" method="post" class="modal-content">
        <input type="hidden" name="accion" value="agregar">
        <div class="modal-header">
            <h3>Nuevo Ingrediente</h3>
            <button type="button" class="close" onclick="cerrarModal()">&times;</button>
        </div>
        <div>
            <div class="form-group">
                <label>Nombre:</label>
                <input type="text" class="form-input" name="nombre" required>
            </div>
            <div class="form-group">
                <label>Unidad de medida:</label>
                <select class="form-input" name="unidad_medida">
                    <option value="kg">Kilogramos</option>
                    <option value="L">Litros</option>
                    <option value="und">Unidades</option>
                </select>
            </div>
            <div class="form-group">
                <label>Cantidad:</label>
                <input type="number" class="form-input" name="cantidad" step="0.01" required>
            </div>
            <div class="form-group">
                <label>Stock mínimo:</label>
                <input type="number" class="form-input" name="stock_min" step="0.01" required>
            </div>
            <div style="display: flex; gap: 1rem; margin-top: 2rem;">
                <button class="btn btn-primary" type="submit" style="flex: 1;">Crear</button>
                <button class="btn btn-secondary" type="button" onclick="cerrarModal()" style="flex: 1;">Cancelar</button>
            </div>
        </div>
    </form>
</div>

<!-- Modal Editar Ingrediente -->
<div id="modalEditar" class="modal">
    <form action="${pageContext.request.contextPath}/insumosController" method="post" class="modal-content">
        <input type="hidden" name="accion" value="actualizar">
        <input type="hidden" name="id_ins" id="editarId">
        <div class="modal-header">
            <h3>Editar Ingrediente</h3>
            <button type="button" class="close" onclick="cerrarModal()">&times;</button>
        </div>
        <div>
            <div class="form-group">
                <label>Nombre:</label>
                <input type="text" class="form-input" name="nombre" id="editarNombre" required>
            </div>
            <div class="form-group">
                <label>Unidad de medida:</label>
                <select class="form-input" name="unidad_medida" id="editarUnidad">
                    <option value="kg">Kilogramos</option>
                    <option value="L">Litros</option>
                    <option value="und">Unidades</option>
                </select>
            </div>
            <div class="form-group">
                <label>Cantidad:</label>
                <input type="number" class="form-input" name="cantidad" id="editarCantidad" step="0.01" required>
            </div>
            <div class="form-group">
                <label>Stock mínimo:</label>
                <input type="number" class="form-input" name="stock_min" id="editarStockMin" step="0.01" required>
            </div>
            <div style="display: flex; gap: 1rem; margin-top: 2rem;">
                <button class="btn btn-primary" type="submit" style="flex: 1;">Actualizar</button>
                <button class="btn btn-secondary" type="button" onclick="cerrarModal()" style="flex: 1;">Cancelar</button>
            </div>
        </div>
    </form>
</div>

<!-- Modal Confirmar Eliminación -->
<div id="modalEliminar" class="modal">
    <div class="modal-content">
        <div class="modal-header">
            <h3>Confirmar Eliminación</h3>
            <button class="close" onclick="cerrarModal()">&times;</button>
        </div>
        <p>¿Estás seguro de que deseas eliminar este ingrediente?</p>
        <div style="display: flex; gap: 1rem; margin-top: 2rem;">
            <a href="#" class="btn btn-danger" id="btnEliminarConfirmado" style="flex: 1;">Sí, eliminar</a>
            <button class="btn btn-secondary" onclick="cerrarModal()" style="flex: 1;">Cancelar</button>
        </div>
    </div>
</div>

<footer class="footer">
    <p>&copy; 2025 Péguele a la Empanada. Sistema de Inventario.</p>
</footer>

<!-- Scripts -->
<script>
    let modalActual = null;

    function abrirModalNuevo() {
        document.getElementById('modalNuevo').style.display = 'block';
        modalActual = 'nuevo';
    }

    function abrirModalEditar(id, nombre, unidad, cantidad, stockMin) {
        document.getElementById('editarId').value = id;
        document.getElementById('editarNombre').value = nombre;
        document.getElementById('editarUnidad').value = unidad;
        document.getElementById('editarCantidad').value = cantidad;
        document.getElementById('editarStockMin').value = stockMin;
        document.getElementById('modalEditar').style.display = 'block';
        modalActual = 'editar';
    }

    function confirmarEliminar(id) {
        const enlace = contextPath + '/insumosController?accion=eliminar&id=' + id;
        document.getElementById('btnEliminarConfirmado').href = enlace;
        document.getElementById('modalEliminar').style.display = 'block';
        modalActual = 'eliminar';
    }

    function cerrarModal() {
        ['modalNuevo', 'modalEditar', 'modalEliminar'].forEach(id => {
            document.getElementById(id).style.display = 'none';
        });
        modalActual = null;
    }

    function filtrarIngredientes() {
        const filtro = document.getElementById("buscarIngrediente").value.toLowerCase();
        const tarjetas = document.querySelectorAll(".ingrediente-card");
        let hayCoincidencias = false;

        tarjetas.forEach(card => {
            const nombre = card.querySelector(".ingrediente-nombre").textContent.toLowerCase();
            const visible = nombre.includes(filtro);
            card.style.display = visible ? "block" : "none";
            if (visible) hayCoincidencias = true;
        });

        document.getElementById("noResultados").style.display = hayCoincidencias ? "none" : "block";
    }

    window.onclick = function (event) {
        if (event.target.classList.contains("modal")) {
            cerrarModal();
        }
    }
</script>

</body>
</html>
