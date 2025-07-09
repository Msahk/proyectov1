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

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <title>Producción</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/produccion.css" />
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 0; background: #f9f9f9; }
        .container { max-width: 960px; margin: 2rem auto; padding: 1rem; background: white; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        .page-header h1 { margin-bottom: 1rem; }
        .stats-grid { display: flex; gap: 1rem; margin-bottom: 1rem; }
        .stat-card { background: #e3e3e3; padding: 1rem; border-radius: 6px; flex: 1; text-align: center; }
        .stat-number { font-size: 2rem; font-weight: bold; }
        .actions-bar { display: flex; justify-content: space-between; margin-bottom: 1rem; }
        .search-input { padding: 0.5rem; width: 250px; border-radius: 4px; border: 1px solid #ccc; }
        .btn { padding: 0.5rem 1rem; border: none; border-radius: 4px; cursor: pointer; font-size: 0.9rem; }
        .btn-primary { background-color: #c62828; color: white; }
        .btn-primary:hover { background-color: #b71c1c; }
        .btn-secondary { background-color: #666; color: white; }
        .btn-secondary:hover { background-color: #444; }
        .btn-danger { background-color: #d32f2f; color: white; }
        .btn-danger:hover { background-color: #9a0007; }
        .btn-small { padding: 0.25rem 0.5rem; font-size: 0.8rem; margin-right: 0.3rem; }
        .produccion-grid { display: grid; grid-template-columns: repeat(auto-fill,minmax(250px,1fr)); gap: 1rem; }
        .produccion-card { background: #fff; border: 1px solid #ddd; border-radius: 6px; padding: 1rem; box-shadow: 0 2px 4px rgba(0,0,0,0.05); position: relative; margin-bottom: 1rem; }
        .card-header { display: flex; justify-content: space-between; margin-bottom: 0.5rem; }
        .estado-badge { padding: 0.25rem 0.5rem; border-radius: 12px; font-size: 0.8rem; font-weight: bold; text-transform: capitalize; }
        .estado-pendiente { background-color: #ffeb3b; color: #856404; }
        .estado-produciendo { background-color: #03a9f4; color: white; }
        .estado-listo { background-color: #4caf50; color: white; }
        .modal { display: none; position: fixed; z-index: 9999; left: 0; top: 0; width: 100%; height: 100%; overflow: auto; background-color: rgba(0,0,0,0.4); }
        .modal-content { background-color: #fff; margin: 5% auto; padding: 20px; border: 1px solid #888; width: 90%; max-width: 600px; border-radius: 8px; }
        .form-group { margin-bottom: 1rem; }
        .form-input { width: 100%; padding: 0.5rem; border: 1px solid #ccc; border-radius: 4px; }
        .modal-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 1rem; }
        .modal-actions { text-align: right; }
        .insumo-item { display: flex; gap: 0.5rem; margin-top: 0.5rem; }
        .insumo-item input { flex: 1; }
        .insumo-item button { background: #d32f2f; color: white; border: none; border-radius: 4px; cursor: pointer; }
        footer.footer { text-align: center; margin: 2rem 0; color: #777; }
    </style>
</head>
<body>
<jsp:include page="navbar.jsp" />

<div class="container">
    <div class="page-header">
        <h1>Gestión de Producción</h1>
    </div>

    <!-- Estadísticas -->
    <div class="stats-grid">
        <div class="stat-card">
            <div class="stat-number">${listaProduccion.size()}</div>
            <div class="stat-label">Total Producciones</div>
        </div>
        <div class="stat-card">
            <div class="stat-number">--</div>
            <div class="stat-label">Producciones Pendientes</div>
        </div>
    </div>

    <!-- Acciones -->
    <div class="actions-bar">
        <input type="text" class="search-input" id="buscarProduccion" placeholder="Buscar producción..." oninput="filtrarProducciones()" />
        <button class="btn btn-primary" onclick="abrirModalNuevaProduccion()">➕ Nueva Producción</button>
    </div>

    <!-- Grid de producciones -->
    <div class="produccion-grid" id="produccionGrid">
        <c:forEach var="p" items="${listaProduccion}">
            <c:set var="estadoClass" value="estado-${p.estado.toLowerCase()}" />
            <div class="produccion-card ${estadoClass}">
                <div class="card-header">
                    <div>
                        <div><strong>Fecha:</strong> ${p.fecha_produccion}</div>
                        <div><strong>Tipo:</strong> ${p.tipo}</div>
                    </div>
                    <span class="estado-badge ${estadoClass}">${p.estado}</span>
                </div>
                <div class="produccion-detalle">
                    <p><strong>Total Empanadas:</strong> ${p.cantidad}</p>
                    <p><strong>ID Responsable:</strong> ${p.id_res}</p>
                </div>
                <div class="card-actions">
                    <button class="btn btn-secondary btn-small" onclick="abrirModalEditar(${p.id_proc}, '${p.fecha_produccion}', '${p.tipo}', ${p.cantidad}, ${p.id_res}, '${p.estado}')">✏️ Editar</button>
                    <button class="btn btn-danger btn-small" onclick="confirmarEliminar(${p.id_proc})">🗑️ Eliminar</button>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<!-- Modal Producción (registrar o editar) -->
<div id="modalNuevaProduccion" class="modal">
    <form action="${pageContext.request.contextPath}/produccionController" method="post" class="modal-content">
        <input type="hidden" name="accion" id="accion" value="agregar" />
        <input type="hidden" name="id_proc" id="id_proc" />
        <div class="modal-header">
            <h3 id="modalTitulo">Registrar Producción</h3>
            <button type="button" class="close" onclick="cerrarModal()">&times;</button>
        </div>
        <div class="form-body">
            <div class="form-group">
                <label>Fecha de Producción:</label>
                <input type="date" name="fecha_produccion" class="form-input" required />
            </div>
            <div class="form-group">
                <label>Tipo de Empanada:</label>
                <select name="tipo" class="form-input" required>
                    <option value="Carne">Carne</option>
                    <option value="Pollo">Pollo</option>
                    <option value="Queso">Queso</option>
                </select>
            </div>
            <div class="form-group">
                <label>Total Empanadas:</label>
                <input type="number" name="cantidad" class="form-input" min="1" required />
            </div>
            <div class="form-group">
                <label>Estado:</label>
                <select name="estado" class="form-input">
                    <option value="Pendiente">Pendiente</option>
                    <option value="Produciendo">Produciendo</option>
                    <option value="Listo">Listo</option>
                </select>
            </div>
            <div class="form-group">
                <label>Insumos y Cantidades:</label>
                <div id="insumos-lista">
                    <div class="insumo-item">
                        <input type="number" name="id_ins[]" placeholder="ID Insumo" required />
                        <input type="number" name="cantidad_ins[]" placeholder="Cantidad necesaria" step="0.01" required />
                        <button type="button" onclick="eliminarInsumo(this)">❌</button>
                    </div>
                </div>
                <button type="button" onclick="agregarInsumo()">➕ Agregar Insumo</button>
            </div>
            <div class="modal-actions">
                <button type="submit" class="btn btn-primary">Guardar</button>
                <button type="button" class="btn btn-secondary" onclick="cerrarModal()">Cancelar</button>
            </div>
        </div>
    </form>
</div>

<footer class="footer">
    <p>&copy; 2025 Péguele a la Empanada. Sistema de Producción.</p>
</footer>

<script>
    function abrirModalNuevaProduccion() {
        document.getElementById('modalTitulo').innerText = 'Registrar Producción';
        document.getElementById('accion').value = 'agregar';
        document.getElementById('id_proc').value = '';
        document.querySelector('form').reset();
        limpiarInsumos();
        agregarInsumo();
        document.getElementById('modalNuevaProduccion').style.display = 'block';
    }

    function abrirModalEditar(id, fecha, tipo, cantidad, id_res, estado) {
        document.getElementById('modalTitulo').innerText = 'Editar Producción';
        document.getElementById('accion').value = 'editar';
        document.getElementById('id_proc').value = id;
        document.querySelector('input[name="fecha_produccion"]').value = fecha;
        document.querySelector('select[name="tipo"]').value = tipo;
        document.querySelector('input[name="cantidad"]').value = cantidad;
        document.querySelector('select[name="estado"]').value = estado;

        limpiarInsumos();
        agregarInsumo(); // Por ahora solo uno (puedes usar AJAX para cargar todos)
        document.getElementById('modalNuevaProduccion').style.display = 'block';
    }

    function cerrarModal() {
        document.querySelectorAll('.modal').forEach(m => m.style.display = 'none');
    }

    function confirmarEliminar(id) {
        if (confirm('¿Estás seguro de eliminar esta producción?')) {
            window.location.href = '${pageContext.request.contextPath}/produccionController?accion=eliminar&id=' + id;
        }
    }

    function agregarInsumo() {
        const contenedor = document.getElementById("insumos-lista");
        const div = document.createElement("div");
        div.classList.add("insumo-item");
        div.innerHTML = `
            <input type="number" name="id_ins[]" placeholder="ID Insumo" required />
            <input type="number" name="cantidad_ins[]" placeholder="Cantidad necesaria" step="0.01" required />
            <button type="button" onclick="eliminarInsumo(this)">❌</button>
        `;
        contenedor.appendChild(div);
    }

    function eliminarInsumo(btn) {
        btn.parentElement.remove();
    }

    function limpiarInsumos() {
        document.getElementById("insumos-lista").innerHTML = "";
    }

    function filtrarProducciones() {
        const filtro = document.getElementById('buscarProduccion').value.toLowerCase();
        const cards = document.querySelectorAll('.produccion-card');
        cards.forEach(card => {
            const texto = card.textContent.toLowerCase();
            card.style.display = texto.includes(filtro) ? 'block' : 'none';
        });
    }
</script>
</body>
</html>
