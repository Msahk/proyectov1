<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="models.usuarios" %>

<%
    usuarios usu = (usuarios) session.getAttribute("usuarios");
    if (usu == null || ("EP".equals(usu.getRol()))) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
%>




<!DOCTYPE html>
<html lang="es"> <!-- cambia a "es" si tu público es hispano -->
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pedidos</title>
    <link rel="stylesheet" href="assets/css/pedidos.css">
</head>
<body>
    <jsp:include page="navbar.jsp" />
    <main class="main">
        <div class="admin-container">
            <div class="admin-header">
                <h1>Gestión de Pedidos</h1>
                
                
            </div>

            <div class="alert alert-success" id="alertSuccess">
                Estado del pedido actualizado exitosamente
            </div>
            
            <div class="alert alert-error" id="alertError">
                Error al actualizar el pedido
            </div>

            <div class="stats-grid">
                <div class="stat-card">
                    <div class="stat-number" id="statPendientes">0</div>
                    <div class="stat-label">Pedidos Pendientes</div>
                </div>
                <div class="stat-card">
                    <div class="stat-number" id="statEnProceso">0</div>
                    <div class="stat-label">En Proceso</div>
                </div>
                <div class="stat-card">
                    <div class="stat-number" id="statHoy">0</div>
                    <div class="stat-label">Entregas Hoy</div>
                </div>
                <div class="stat-card">
                    <div class="stat-number" id="statUrgentes">0</div>
                    <div class="stat-label">Urgentes</div>
                </div>
            </div>

            <div class="pedidos-container">
                <div class="pedidos-header">
                    <h2>📋 Pedidos Activos</h2>
                    <div class="filter-controls">
                        <div class="search-bar">
                            <input type="text" id="buscarPedido" placeholder="🔍 Buscar pedido..." onkeyup="filtrarPedidos()">
                        </div>
                        <select id="filtroEstado" class="filter-select" onchange="filtrarPorEstado()">
                            <option value="">Todos los estados</option>
                            <option value="P">Pendientes</option>
                            <option value="E">En Proceso</option>
                            <option value="C">Completados</option>
                        </select>
                    </div>
                </div>

                <table class="pedidos-table" id="tablaPedidos">
                    <thead>
                        <tr>
                             <th>ID ventas</th>
                            <th>ID Pedido</th>
                            <th>Fecha entrega</th>
                            <th>Estado</th>
                            <th>Observaciones</th>
                           
                        </tr>
                    </thead>
                    <tbody>
                    <c:choose>
                        <c:when test= "${not empty pedidos}">
                            <c:forEach var="p" items="${pedidos}">
                                <tr>
                                    <td>${p.id_ven}</td>
                                     <td>${p.id_ped}</td>
                                     <td>${p.fecha_entrega}</td>
                                     <td>${p.estado}</td>
                                     <td>${p.observaciones_pedido}</td>
                                     <td>
    <ul>
        <li><button type="button" class="edit-btn" data-id="${p.id_ped}" 
            style="background-color: #28a745; color: white; padding: 8px 12px; border: none; border-radius: 4px; font-weight: bold;">
            ✏️ Editar</button></li>
        <li><button type="button" class="delete-btn" delete-id="${p.id_ven}"
            style="background-color: #dc3545; color: white; padding: 8px 12px; border: none; border-radius: 4px; font-weight: bold;">
            🗑️ Eliminar</button></li>
    </ul>
</td>
                            </c:forEach>
                        </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="5" class="empty-table"> No hay ventas registradas</td> 
                                    
                                </tr>
                            </c:otherwise>
                    </c:choose>
                    </tbody>
                </table>
            </div>
        </div>
    </main>

    

    <footer class="footer">
        <p>&copy; 2025 Péguele a la Empanada. Todos los derechos reservados.</p>
    </footer>

    <div id="modalActualizar" class="modal">
        <div class="modal-content">
            <div class="modal-header">
                <h3>Actualizar Pedido <span id="pedidoIdModal">#001</span></h3>
                <span class="close" onclick="cerrarModal()">&times;</span>
            </div>
           <form id="formActualizar" action="${pageContext.request.contextPath}/pedidosController" method="post">
    <input type="hidden" id="id_ped" name="id_ped">
    <input type="hidden" name="accion" value="actualizar">
  <div class="form-group">
        <label for="nuevoEstado">Estado del Pedido:</label>
        <select id="nuevoEstado" name="estado" required>
            <option value="P">Pendiente</option>
            <option value="E">En Proceso</option>
            <option value="C">Completado</option>
            <option value="X">Cancelado</option>
        </select>
    </div>

    <div class="form-group">
        <label for="nuevaFechaEntrega">Nueva Fecha de Entrega:</label>
        <input type="datetime-local" id="nuevaFechaEntrega" name="fecha_entrega">
    </div>

    <div class="form-group">
        <label for="nuevasObservaciones">Observaciones:</label>
        <textarea id="nuevasObservaciones" name="observaciones_pedido" rows="3"></textarea>
    </div>

    <div style="display: flex; gap: 1rem; margin-top: 2rem;">
        <button type="submit" class="btn">Actualizar Pedido</button>
        <button type="button" class="btn" style="background-color: #666;" onclick="cerrarModal()">Cancelar</button>
    </div>
</form>        </div>
    </div>

   
    <script>
        function filtrarPedidos() {
            const input = document.getElementById('buscarPedido');
            const filtro = input.value.toUpperCase();
            const tabla = document.getElementById('tablaPedidos');
            const filas = tabla.getElementsByTagName('tr');

            for (let i = 1; i < filas.length; i++) {
                const fila = filas[i];
                const celdas = fila.getElementsByTagName('td');
                let mostrar = false;
                
                for (let j = 0; j < celdas.length - 1; j++) {
                    if (celdas[j].textContent.toUpperCase().indexOf(filtro) > -1) {
                        mostrar = true;
                        break;
                    }
                }
                
                fila.style.display = mostrar ? '' : 'none';
            }
        }
    </script>
   
</body>
</html>
