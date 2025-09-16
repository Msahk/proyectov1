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
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin panel</title>
    <link rel="stylesheet" href="assets/css/RegistroUsuarios.css">
</head>
<body>
     
    <jsp:include page="navbar.jsp" />

    <main class="main">
        <div class="admin-container">
            <div class="admin-header">
                <h1>Gestión de los Detalles de las Ventas</h1>
                
            </div>

            <div class="admin-grid">
                
                <div class="usuarios-section">
                    <button href="" class="boton" onclick="agregarUsuario()">Agregar</button>
                    
                    <div class="search-bar">
                        <input type="text" id="buscarUsuario" placeholder="🔍 Buscar usuario..." onkeyup="filtrarUsuarios()">
                    </div>

                    <table class="usuarios-table" id="tablaUsuarios">
                        <thead>
                            <tr>
                                <th>Codigo de Venta</th>
                                <th>Producto</th>
                                <th>Cantidad</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                          <c:choose>
                                <c:when test="${not empty detalle_venta}">
                                    <c:forEach var="u" items="${detalle_venta}">
                                        <tr>
                                            <td>${u.id_ven}</td>
                                            <td>${u.nombreProducto}</td>
                                            <td>${u.cantidad}</td>
                                            <td>
                                                <div class="dropdown">
                                                    <button class="dropdown-toggle">⋮</button>
                                                    <ul class="dropdown-menu">
                                                        <li><button type="button" class="edit-btn" data-id="${u.id_detalle}">Editar</button></li>
                                                    <li><button type="button" class="delete-btn" data-id="${u.id_detalle}">Eliminar</button></li>
                                                        </ul>
                                                </div>
                                            </td>

                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="5" class="empty-table">No hay usuarios registrados</td>
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
    
<div id="selects-hidden" style="visibility: hidden; position: absolute; left: -9999px;">
    <select id="ventas-options">
        <c:forEach var="v" items="${detalle_venta}">
            <option value="${v.id_ven}">${v.id_ven}</option>
        </c:forEach>
    </select>

    <select id="productos-options">
        <c:forEach var="p" items="${detalle_venta}">
            <option value="${p.id_proc}">${p.nombreProducto}</option>
        </c:forEach>
    </select>
</div>
    
    
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    
    <script>
  var listaVentas = [
    <c:forEach var="v" items="${ventas}">
      { id: ${v.id_ven} },
    </c:forEach>
  ];

  var listaProductos = [
    <c:forEach var="p" items="${productos}">
      { id: ${p.id_proc}, nombre: "${p.tipo}" },
    </c:forEach>
  ];
</script>
    
    <script>
    // Función para actualizar usuario
    


    // Listener global para los botones (delegación de eventos)

    document.addEventListener("click", function (e) {
        

        if (e.target.classList.contains("delete-btn")) {
            const id = e.target.getAttribute("data-id");
            eliminarD(id);
        }
    });
    
    function eliminarD(id) {
    Swal.fire({
        title: '¿Estás seguro?',
        text: "¡No podrás revertir esto!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        cancelButtonColor: '#3085d6',
        confirmButtonText: 'Sí, eliminar',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.isConfirmed) {
            window.location.href = 'detalle_ventaController?accion=eliminar&id=' + id;
        }
    });
}

</script>




<script>
  // Delegación de eventos para botones "Editar"
  document.addEventListener("click", function(e) {
    if (e.target.classList.contains("edit-btn")) {
      actualizarDetalleVenta(e.target.getAttribute("data-id"));
    }
  });
</script>

<script>
function actualizarDetalleVenta(id) {
  fetch("detalle_ventaController?accion=editar&id=" + id)
    .then(res => res.json())
    .then(dv => {
      if (!dv || !dv.id_detalle) {
        Swal.fire("Error", "No se encontró el detalle para editar.", "error");
        return;
      }

      // ✅ Copiamos directamente el contenido ya renderizado por JSP
      const ventasHTML = document.getElementById("ventas-options").innerHTML;
      const productosHTML = document.getElementById("productos-options").innerHTML;

      Swal.fire({
        html:
          '<div class="registro-section">' +
          '<h2>Actualizar Detalle de Venta</h2>' +
          '<form method="POST" action="detalle_ventaController">' +
          '<input type="hidden" name="accion" value="actualizar">' +
          '<input type="hidden" name="id_detalle" value="' + dv.id_detalle + '">' +

          '<div class="form-group">' +
          '<label for="id_ven">Código de Venta</label>' +
          '<select id="id_ven" name="id_ven" required>' + ventasHTML + '</select>' +
          '</div>' +

          '<div class="form-group">' +
          '<label for="id_proc">Producto</label>' +
          '<select id="id_proc" name="id_proc" required>' + productosHTML + '</select>' +
          '</div>' +

          '<div class="form-group">' +
          '<label for="cantidad">Cantidad</label>' +
          '<input type="number" id="cantidad" name="cantidad" value="' + dv.cantidad + '" required>' +
          '</div>' +

          '<div style="margin-top: 20px;">' +
          '<button type="submit" class="btn">Guardar Cambios</button>' +
          '</div>' +

          '</form>' +
          '</div>',
        showConfirmButton: false,
        showCloseButton: true,
        width: "500px",
        didOpen: () => {
          // ✅ Seleccionar opción correspondiente al detalle recibido
          document.getElementById("id_ven").value = dv.id_ven;
          document.getElementById("id_proc").value = dv.id_proc;
        }
      });
    })
    .catch(error => {
      console.error("Error al obtener el detalle para editar:", error);
      Swal.fire("Error", "Ocurrió un problema al cargar el detalle.", "error");
    });
}

</script>



<script>
    
    var ventasOptions = document.getElementById('ventas-options').innerHTML;
        var productosOptions = document.getElementById('productos-options').innerHTML;
    
        function agregarUsuario() {
            Swal.fire({
                html:`
                    <div class="registro-section">
                    <h2> Registrar nuevos detalles a la venta</h2>
                    
                    <div class="alert alert-success" id="alertSuccess">
                         registrado exitosamente
                    </div>
                    
                    <div class="alert alert-error" id="alertError">
                        Error al registrar 
                    </div>

                    <form id="registroForm" method="POST" action="${pageContext.request.contextPath}/detalle_ventaController?accion=agregar">
                        
                            <div class="form-group">
                                <label for="id_ven">Codigo de la venta</label>
                                <select id="id_ven" name="id_ven" required>
                                    <c:forEach var="u" items="${detalle_venta}">
                                    <option value="${u.id_ven}">${u.id_ven}</option>
                                    </c:forEach>
                                 
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="id_proc">Codigo del producto</label>
                                <select id="id_proc" name="id_proc" required>
                                    <c:forEach var="u" items="${detalle_venta}">
                                    <option value="${u.id_proc}">${u.nombreProducto}</option>
                                    </c:forEach>
                                 
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="cantidad"></label>
                                <input type="number" id="cantidad" name="cantidad" required>
                            </div>

                        <div style="display: flex; gap: 1rem; margin-top: 2rem;">
                            <button type="submit" class="btn">Registrar</button>
                        </div>
                    </form>
                </div>
                `,
                showConfirmButton: false,
                showCloseButton: true
            });
            
            
            
        }
        
        

        
        <% if (request.getAttribute("errorRegistro") != null) { %>
    
        Swal.fire({
          icon: 'error',
          title: '¡Error!',
          text: '<%= request.getAttribute("errorRegistro") %>'
        });
        <% } %>
     
            
        // Función para registrar usuario
        document.getElementById('registroForm').addEventListener('submit', function(e) {
            e.preventDefault();
            
            const formData = new FormData(this);
            const clave = formData.get('clave');
            const confirmarClave = formData.get('confirmarClave');
            
            // Validar contraseñas
            if (clave !== confirmarClave) {
                mostrarAlerta('error', 'Las contraseñas no coinciden');
                return;
            }
            
            // Validar longitud de contraseña
            if (clave.length < 6) {
                mostrarAlerta('error', 'La contraseña debe tener al menos 6 caracteres');
                return;
            }
        });

        // Función para mostrar alertas
        function mostrarAlerta(tipo, mensaje) {
            const alertSuccess = document.getElementById('alertSuccess');
            const alertError = document.getElementById('alertError');
            
            // Ocultar todas las alertas
            alertSuccess.style.display = 'none';
            alertError.style.display = 'none';
            
            if (tipo === 'success') {
                alertSuccess.textContent = mensaje;
                alertSuccess.style.display = 'block';
                setTimeout(() => {
                    alertSuccess.style.display = 'none';
                }, 3000);
            } else {
                alertError.textContent = mensaje;
                alertError.style.display = 'block';
                setTimeout(() => {
                    alertError.style.display = 'none';
                }, 3000);
            }
        }
        
        

        // Función para limpiar formulario
        function limpiarFormulario() {
            document.getElementById('registroForm').reset();
        }

      

        // Función para filtrar usuarios
        function filtrarUsuarios() {
            const input = document.getElementById('buscarUsuario');
            const filtro = input.value.toUpperCase();
            const tabla = document.getElementById('tablaUsuarios');
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


