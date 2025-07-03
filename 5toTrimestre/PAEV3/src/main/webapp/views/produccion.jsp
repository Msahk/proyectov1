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
        /* Ejemplo básico de estilos para que te funcione el modal y botones */
        body {
            font-family: Arial, sans-serif;
            margin: 0; padding: 0; background: #f9f9f9;
        }
        .container {
            max-width: 960px;
            margin: 2rem auto;
            padding: 1rem;
            background: white;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .page-header h1 {
            margin-bottom: 1rem;
        }
        .stats-grid {
            display: flex;
            gap: 1rem;
            margin-bottom: 1rem;
        }
        .stat-card {
            background: #e3e3e3;
            padding: 1rem;
            border-radius: 6px;
            flex: 1;
            text-align: center;
        }
        .stat-number {
            font-size: 2rem;
            font-weight: bold;
        }
        .actions-bar {
            display: flex;
            justify-content: space-between;
            margin-bottom: 1rem;
        }
        .search-input {
            padding: 0.5rem;
            width: 250px;
            border-radius: 4px;
            border: 1px solid #ccc;
        }
        .btn {
            padding: 0.5rem 1rem;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 0.9rem;
        }
        .btn-primary {
            background-color: #c62828;
            color: white;
        }
        .btn-primary:hover {
            background-color: #b71c1c;
        }
        .btn-secondary {
            background-color: #666;
            color: white;
        }
        .btn-secondary:hover {
            background-color: #444;
        }
        .btn-danger {
            background-color: #d32f2f;
            color: white;
        }
        .btn-danger:hover {
            background-color: #9a0007;
        }
        .btn-small {
            padding: 0.25rem 0.5rem;
            font-size: 0.8rem;
            margin-right: 0.3rem;
        }
        .produccion-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill,minmax(250px,1fr));
            gap: 1rem;
        }
        .produccion-card {
            background: #fff;
            border: 1px solid #ddd;
            border-radius: 6px;
            padding: 1rem;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05);
            position: relative;
        }
        .card-header {
            display: flex;
            justify-content: space-between;
            margin-bottom: 0.5rem;
        }
        .estado-badge {
            padding: 0.25rem 0.5rem;
            border-radius: 12px;
            font-size: 0.8rem;
            font-weight: bold;
            text-transform: capitalize;
        }
        .estado-pendiente {
            background-color: #ffeb3b;
            color: #856404;
        }
        .estado-produciendo {
            background-color: #03a9f4;
            color: white;
        }
        .estado-listo {
            background-color: #4caf50;
            color: white;
        }
        .modal {
            display: none;
            position: fixed;
            z-index: 1000;
            left: 0; top: 0;
            width: 100%; height: 100%;
            overflow: auto;
            background-color: rgba(0,0,0,0.5);
        }
        .modal-content {
            background-color: #fff;
            margin: 5% auto;
            padding: 1rem;
            border-radius: 8px;
            width: 400px;
            max-width: 90%;
            position: relative;
        }
        .modal-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-bottom: 1px solid #ddd;
            padding-bottom: 0.5rem;
            margin-bottom: 1rem;
        }
        .modal-header h3 {
            margin: 0;
        }
        .close {
            background: none;
            border: none;
            font-size: 1.5rem;
            cursor: pointer;
            line-height: 1;
        }
        .form-group {
            margin-bottom: 1rem;
        }
        .form-input, select {
            width: 100%;
            padding: 0.4rem;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .modal-actions {
            display: flex;
            justify-content: flex-end;
            gap: 0.5rem;
        }
        footer.footer {
            text-align: center;
            margin: 2rem 0;
            color: #777;
        }
        /* Mensaje error adicional */
        .mensaje-error {
            color: #b71c1c;
            background-color: #ffcdd2;
            padding: 0.75rem 1rem;
            border-radius: 6px;
            font-weight: bold;
            margin-bottom: 1rem;
            text-align: center;
        }
    </style>
</head>
<body>

<jsp:include page="navbar.jsp" />

<div class="container">

    <div class="page-header">
        <h1>Gestión de Producción</h1>
    </div>

    <!-- Mensajes de error -->
    <c:if test="${param.error == 'stock'}">
        <div class="mensaje-error">
            No hay stock suficiente para realizar esta producción.
        </div>
    </c:if>
    <c:if test="${param.error == 'datos'}">
        <div class="mensaje-error">
            Error en los datos enviados, por favor verifica la información.
        </div>
    </c:if>

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

    <!-- Barra de acciones -->
    <div class="actions-bar">
        <div class="search-box">
            <input type="text" class="search-input" id="buscarProduccion" placeholder="Buscar producción..." oninput="filtrarProducciones()" />
        </div>
        <div>
            <button class="btn btn-primary" onclick="abrirModalNuevaProduccion()">➕ Nueva Producción</button>
        </div>
    </div>

    <!-- Grid de producciones -->
    <div class="produccion-grid" id="produccionGrid">
        <c:forEach var="p" items="${listaProduccion}">
            <c:set var="estadoClass" value="estado-${p.estado}" />
            <div class="produccion-card ${estadoClass}">
                <div class="card-header">
                    <div>
                        <div class="fecha-produccion"><strong>Fecha:</strong> ${p.fecha_produccion}</div>
                        <div class="tipo-produccion"><strong>Tipo:</strong> ${p.tipo}</div>
                    </div>
                    <span class="estado-badge ${estadoClass}">${p.estado}</span>
                </div>
                <div class="produccion-detalle">
                    <p><strong>Total Empanadas:</strong> ${p.total_emp}</p>
                </div>
                <div class="card-actions">
                    <button class="btn btn-secondary btn-small" onclick="abrirModalEditar(${p.id_proc}, '${p.fecha_produccion}', '${p.tipo}', ${p.total_emp}, '${p.estado}')">✏️ Editar</button>
                    <button class="btn btn-danger btn-small" onclick="confirmarEliminar(${p.id_proc})">🗑️ Eliminar</button>
                </div>
            </div>
        </c:forEach>
    </div>

    <div id="noResultados" style="display:none; text-align:center; margin-top:2rem;">
        <p>😕 No se encontraron producciones con ese criterio.</p>
    </div>
</div>

<!-- Modal Nueva Producción -->
<div id="modalNuevaProduccion" class="modal">
    <form action="${pageContext.request.contextPath}/produccionController" method="post" class="modal-content">
        <input type="hidden" name="accion" value="agregar" />
        <div class="modal-header">
            <h3>Registrar Producción</h3>
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
                <input type="number" name="total_emp" class="form-input" min="1" required />
            </div>
            <div class="form-group">
                <label>Estado:</label>
                <select name="estado" class="form-input">
                    <option value="pendiente">Pendiente</option>
                    <option value="produciendo">Produciendo</option>
                    <option value="listo">Listo</option>
                </select>
            </div>
            <div class="modal-actions">
                <button type="submit" class="btn btn-primary">Registrar</button>
                <button type="button" class="btn btn-secondary" onclick="cerrarModal()">Cancelar</button>
            </div>
        </div>
    </form>
</div>

<!-- Modal Editar Producción -->
<div id="modalEditarProduccion" class="modal">
    <form action="${pageContext.request.contextPath}/produccionController" method="post" class="modal-content">
        <input type="hidden" name="accion" value="actualizar" />
        <input type="hidden" name="id_proc" id="editarIdProc" />
        <div class="modal-header">
            <h3>Editar Producción</h3>
            <button type="button" class="close" onclick="cerrarModal()">&times;</button>
        </div>
        <div class="form-body">
            <div class="form-group">
                <label>Fecha de Producción:</label>
                <input type="date" name="fecha_produccion" id="editarFecha" class="form-input" required />
            </div>
            <div class="form-group">
                <label>Tipo de Empanada:</label>
                <select name="tipo" id="editarTipo" class="form-input" required>
                    <option value="Carne">Carne</option>
                    <option value="Pollo">Pollo</option>
                    <option value="Queso">Queso</option>
                </select>
            </div>
            <div class="form-group">
                <label>Total Empanadas:</label>
                <input type="number" name="total_emp" id="editarTotal" class="form-input" min="1" required />
            </div>
            <div class="form-group">
                <label>Estado:</label>
                <select name="estado" id="editarEstado" class="form-input">
                    <option value="pendiente">Pendiente</option>
                    <option value="produciendo">Produciendo</option>
                    <option value="listo">Listo</option>
                </select>
            </div>
            <div class="modal-actions">
                <button type="submit" class="btn btn-primary">Actualizar</button>
                <button type="button" class="btn btn-secondary" onclick="cerrarModal()">Cancelar</button>
            </div>
        </div>
    </form>
</div>

<!-- Modal Confirmar Eliminación -->
<div id="modalEliminarProduccion" class="modal">
    <div class="modal-content">
        <div class="modal-header">
            <h3>¿Eliminar Producción?</h3>
            <button type="button" class="close" onclick="cerrarModal()">&times;</button>
        </div>
        <p>¿Estás seguro de que deseas eliminar esta producción?</p>
        <div class="modal-actions">
            <a id="btnConfirmarEliminar" class="btn btn-danger" href="#">Sí, eliminar</a>
            <button type="button" class="btn btn-secondary" onclick="cerrarModal()">Cancelar</button>
        </div>
    </div>
</div>

<footer class="footer">
    <p>&copy; 2025 Péguele a la Empanada. Sistema de Producción.</p>
</footer>

<script>
    let modalActual = null;

    function abrirModalNuevaProduccion() {
        document.getElementById('modalNuevaProduccion').style.display = 'block';
        modalActual = 'modalNuevaProduccion';
    }

    function abrirModalEditar(id, fecha, tipo, total, estado) {
    console.log("EDITAR - Datos recibidos:");
    console.log("ID:", id);
    console.log("Fecha:", fecha);
    console.log("Tipo:", tipo);
    console.log("Total:", total);
    console.log("Estado:", estado);

    document.getElementById('editarIdProc').value = id;
    document.getElementById('editarFecha').value = fecha;

    const tipoNormalizado = tipo.charAt(0).toUpperCase() + tipo.slice(1).toLowerCase();
    const estadoNormalizado = estado.toLowerCase();

    document.getElementById('editarTipo').value = tipoNormalizado;
    document.getElementById('editarTotal').value = total;
    document.getElementById('editarEstado').value = estadoNormalizado;

    document.getElementById('modalEditarProduccion').style.display = 'block';
    modalActual = 'modalEditarProduccion';
}


    function confirmarEliminar(id) {
        const base = window.location.origin + '${pageContext.request.contextPath}/produccionController';
        document.getElementById('btnConfirmarEliminar').href = base + '?accion=eliminar&id=' + id;
        document.getElementById('modalEliminarProduccion').style.display = 'block';
        modalActual = 'modalEliminarProduccion';
    }

    function cerrarModal() {
        document.querySelectorAll('.modal').forEach(m => m.style.display = 'none');
        modalActual = null;
    }

    function filtrarProducciones() {
        const filtro = document.getElementById("buscarProduccion").value.toLowerCase();
        const tarjetas = document.querySelectorAll(".produccion-card");
        let hayCoincidencias = false;

        tarjetas.forEach(card => {
            const texto = card.textContent.toLowerCase();
            const visible = texto.includes(filtro);
            card.style.display = visible ? "block" : "none";
            if (visible) hayCoincidencias = true;
        });

        document.getElementById("noResultados").style.display = hayCoincidencias ? "none" : "block";
    }

    window.onclick = function (event) {
        if (event.target.classList.contains("modal")) {
            cerrarModal();
        }
    };
</script>

</body>
</html>








