<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="models.ventas" %>
<%@ page import="models.usuarios" %>

<%
    usuarios usu = (usuarios) session.getAttribute("usuarios");
    if (usu == null || ("EP".equals(usu.getRol()))) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
%>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ventas</title>
    <link rel="stylesheet" href="assets/css/ventas.css">
    </head>
    <body>

 <jsp:include page="navbar.jsp" />

    <main class="main">
        <div class="admin-container">
            <div class="admin-header">
                <h1>Gestión de Ventas</h1>
                <p>Registra nuevas ventas y gestiona el historial de pedidos</p>
                <button href="" class="boton" onclick="agregarVentas()">Agregar</button>
                <a href="${pageContext.request.contextPath}/detalle_ventaController?accion=listar">Observar los detalles de las ventas</a>
            </div>

            

                <div class="ventas-list-section">
                    <h2>📋 Ventas Registradas</h2>
                    
                    <div class="search-bar">
                        <input type="text" id="buscarUsuario" placeholder="🔍 Buscar venta..." onkeyup="filtrarUsuarios()">
                    </div>

                    <table class="ventas-table" id="tablaVentas">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Tipo</th>
                                <th>Fecha</th>
                                <th>Empleado</th>
                                <th>Cliente</th>
                                <th>Total</th>
                                <th>Estado</th>
                                <th>Observaciones</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                           <c:choose>
                                <c:when test="${not empty ventas}">
                                    <c:forEach var="v" items="${ventas}">
                                        <tr>
                                            <td>${v.id_ven}</td>
                                            <td>${v.tipo}</td>
                                            <td>${v.fecha}</td>
                                            <td>${v.vendedorNombre}</td>
                                            <td>${v.clienteNombre}</td>
                                            <td>${v.total}</td>
                                            <td>${v.estado}</td>
                                            <td>${v.observaciones}</td>
                                            <td>
                                                <div>
                                                    <ul>
                                                        <li><button type="button" class="btn-editar-venta" onclick="editarVentas(${v.id_ven})">Editar</button></li>
                                                        <li><button type="button" class="delete-btn" data-id="${v.id_ven}">Eliminar</button></li>
                                                     </ul>
                                                </div>
                                            </td>

                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="5" class="empty-table">No hay ventas registradas</td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </main>

    <footer class="footer">
        <p>&copy; 2025 Péguele a la Empanada. Todos los derechos reservados.</p>
    </footer>
 
  
 
    <script>
        
        function agregarVentas() {
            Swal.fire({
                html: `
                 <div class="admin-grid">
                <div class="ventas-section">
                    <h2>🥟 Nueva Venta</h2>
                    
                    <div class="alert alert-success" id="alertSuccess">
                        Venta registrada exitosamente
                    </div>
                    
                    <div class="alert alert-error" id="alertError">
                        Error al registrar venta
                    </div>

                    <form id="ventaForm" method="POST" action="${pageContext.request.contextPath}/VentaClientePedidoController?accion=agregar">
                        <div class="form-group">
                            <label for="nombreCliente">Nombre del Cliente:</label>
                            <input type="text" id="nombreCliente" name="nombreCliente" placeholder="Nombre completo del cliente" required>
                        </div>

                        <div class="form-row">
                            <div class="form-group">
                                <label for="telefonoCliente">Teléfono:</label>
                                <input type="tel" id="telefonoCliente" name="telefonoCliente" placeholder="Número de teléfono" required>
                            </div>
                            <div class="form-group">
                                <label for="correoCliente">Correo:</label>
                                <input type="email" id="correoCliente" name="correoCliente" placeholder="correo@ejemplo.com">
                            </div>
                        </div>

                            
                                <input type="hidden" id="tipoVenta" name="tipoVenta" value="Pedido" required>
                                    
                                <input type="hidden" id="estadoVenta" name="estadoVenta" value="Procesando">

                        <div class="form-row">
                            <div class="form-group">
                                <label for="fechaEntregaPedido">Fecha de pedido:</label>
                                <input type="datetime-local" id="fechaEntregaPedido" name="fechaEntregaPedido" required>
                            </div>
                            <div class="form-group">
                                <label for="vendedor">Vendedor:</label>
                                <select id="vendedor" name="vendedor" required>
                                    <c:forEach var="v" items="${vendedores}">
                                        <option value="${v.id_usu}">${v.nombres} ${v.apellidos}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        
                        <div class="form-group">
                                <label for="totalVenta">Total Venta:</label>
                                <input type="number" id="totalVenta" name="totalVenta">
                            </div>

                        <div class="form-group">
                            <label for="observaciones">Observaciones:</label>
                            <textarea id="observaciones" name="observaciones" rows="3" style="width: 100%; padding: 0.8rem; border: 2px solid #ddd; border-radius: 5px; resize: vertical;"></textarea>
                        </div>

                        <div style="display: flex; gap: 1rem; margin-top: 2rem;">
                            <button type="submit" class="btn">Registrar Venta</button>
                            <button type="button" class="btn btn-secondary" onclick="limpiarFormulario()">Limpiar</button>
                        </div>
                    </form>
                </div>

                `,
            showConfirmButton: false,
            showCloseButton: true
            });
        }
        
        </script>
     
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script type="text/javascript">
  console.log("⏳ Cargando script de ventas…");

  // Construye vendedoresOptions con JSP EL SOLO AQUÍ, en servidor:
  var vendedoresOptions = "";
  <c:forEach var="u" items="${vendedores}">
    vendedoresOptions += "<option value='" 
      + "${u.id_usu}" + "'>" 
      + "${u.nombres} ${u.apellidos}" 
      + "</option>";
  </c:forEach>;
  console.log("Opciones de vendedores:", vendedoresOptions);

  function editarVentas(id) {
    console.log("✏️ editarVentas llamado con id =", id);
    fetch('VentaClientePedidoController?accion=obtener&id=' + id)
      .then(res => res.ok ? res.json() : Promise.reject(res.status))
      .then(v => {
        console.log("Datos recibidos para editar:", v);

        // Aquí construimos TODO con concatenación de JS:
        var html = 
          '<div class="ventas-section">' +
            '<h2>✏️ Editar Venta #' + v.id_ven + '</h2>' +
            '<form method="POST" action="' + 
              '${pageContext.request.contextPath}/VentaClientePedidoController?accion=actualizar' + '">' +

              '<input type="hidden" name="idVenta"  value="' + v.id_ven     + '">' +
              '<input type="hidden" name="idCliente" value="' + v.id_Cliente + '">' +

              '<label>Nombre del Cliente:</label>' +
              '<input type="text" name="nombreCliente" value="' + 
                (v.nombreCliente || '') + '" required>' +

              '<label>Teléfono:</label>' +
              '<input type="tel" name="telefonoCliente" value="' + 
                (v.telefonoCliente || '') + '" required>' +

              '<label>Correo:</label>' +
              '<input type="email" name="correoCliente" value="' + 
                (v.correoCliente || '') + '">' +

              '<input type="hidden" name="tipoVenta"  value="' + 
                (v.tipoVenta || '') + '">' +
              '<input type="hidden" name="estadoVenta" value="' + 
                (v.estadoVenta || '') + '">' +

              '<label>Fecha de pedido:</label>' +
              '<input type="datetime-local" name="fechaEntregaPedido" value="' + 
                (v.fechaEntregaPedido 
                   ? v.fechaEntregaPedido.replace(' ', 'T').substring(0,16) 
                   : '') 
                + '" required>' +

              '<label>Vendedor:</label>' +
              '<select name="vendedor" required>' +
                '<option value="">Seleccionar vendedor</option>' +
                vendedoresOptions +
              '</select>' +

              '<label>Total Venta:</label>' +
              '<input type="number" name="totalVenta" step="0.01" value="' + 
                (v.totalVenta || v.total || '') + '">' +

              '<label>Observaciones:</label>' +
              '<textarea name="observaciones" rows="3">' + 
                (v.obsVenta || v.observaciones || '') + 
              '</textarea>' +

              '<div style="text-align:right; margin-top:1rem;">' +
                '<button type="submit" class="btn">Actualizar Venta</button>' +
              '</div>' +
            '</form>' +
          '</div>';

        Swal.fire({
          html: html,
          showConfirmButton: false,
          showCloseButton: true,
          width: 600
        });
      })
      .catch(err => {
        console.error("❌ Error en editarVentas:", err);
        Swal.fire("Error", "No se pudo cargar los datos de la venta.", "error");
      });
  }
  
  function filtrarUsuarios() {
            const input = document.getElementById('buscarUsuario');
            const filtro = input.value.toUpperCase();
            const tabla = document.getElementById('tablaVentas');
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


    
       
    
