<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="models.usuarios" %>



<%
    usuarios usu = (usuarios) session.getAttribute("usuarios");
    if (usu == null || ("EP".equals(usu.getRol())) || ("EV".equals(usu.getRol()))) {
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
                <h1>Gestión de Usuarios</h1>
                
            </div>

            <div class="admin-grid">
                
                <div class="usuarios-section">
                    <h2>👥 Usuarios Registrados</h2>
                    <button href="" class="boton" onclick="agregarUsuario()">Agregar</button>
                    
                    <div class="search-bar">
                        <input type="text" id="buscarUsuario" placeholder="🔍 Buscar usuario..." onkeyup="filtrarUsuarios()">
                    </div>
<div class="table-container">
                    <table class="usuarios-table" id="tablaUsuarios">
                       
                        <thead>
                            <tr>
                                <th>Id</th>
                                <th>Documento</th>
                                <th>Nombres</th>
                                <th>Apellidos</th>
                                <th>Telefono</th>
                                <th>Direccion</th>
                                <th>Correo</th>
                                <th>Rol</th>
                                <th>Estado</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                          <c:choose>
                                <c:when test="${not empty usuarios}">
                                    <c:forEach var="u" items="${usuarios}">
                                        <tr>
                                            <td>${u.id_usu}</td>
                                            <td>${u.documento}</td>
                                            <td>${u.nombres}</td>
                                            <td>${u.apellidos}</td>
                                            <td>${u.telefono}</td>
                                            <td>${u.direccion}</td>
                                            <td>${u.correo}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${u.rol == 'A'}">Administrador</c:when>
                                                    <c:when test="${u.rol == 'EP'}">Produccion</c:when>
                                                    <c:otherwise>Ventas</c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                                                                <span class="status-badge 
    <c:choose>
        <c:when test='${u.estado == "A"}'>status-activo</c:when>
        <c:when test='${u.estado == "I"}'>status-inactivo</c:when>
        <c:otherwise>status-pendiente</c:otherwise>
    </c:choose>">
    <c:choose>
        <c:when test="${u.estado == 'A'}">Activo</c:when>
        <c:when test="${u.estado == 'I'}">Inactivo</c:when>
        <c:otherwise>Pendiente</c:otherwise>
    </c:choose>
</span
              
                                            </td>
                                            <td>
                                                <div class="dropdown">
                                                    <button class="dropdown-toggle">⋮</button>
                                                    <ul class="dropdown-menu">
                                                        <li><button type="button" class="edit-btn" data-id="${u.id_usu}">Editar</button></li>
                                                        <li><button type="button" class="update-btn" data-id="${u.id_usu}" data-estado="${u.estado}">
                                                    <c:choose>
                                                                <c:when test="${u.estado == 'I'}">Activar</c:when>
                                                                <c:when test="${u.estado == 'A'}">Desactivar</c:when>
                                                    <c:otherwise>Pendiente</c:otherwise>
                                                     </c:choose>
                                                    
                                                    </button></li>
                                                    <li><button type="button" class="delete-btn" data-id="${u.id_usu}">Eliminar</button></li>
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
    
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    
    <script>
    // Función para actualizar usuario
    function actualizarUsuario(id) {
    fetch("usuariosController?accion=editar&id=" + id)
        .then(function(res) {
            return res.json();
        })
        .then(function(u) {
            console.table("Usuario a editar:", u);

            // Variables para marcar el rol seleccionado
            var rolAdmin = u.rol === 'A' ? 'selected' : '';
            var rolVend = u.rol === 'EV' ? 'selected' : '';
            var rolProd = u.rol === 'EP' ? 'selected' : '';
            
            var Act = u.estado === 'A' ? 'selected' : '';
            var Inact = u.estado === 'I' ? 'selected' : '';

            Swal.fire({
                html: '' +
                    '<div class="registro-section">' +
                        '<h2>Actualizar Usuario</h2>' +
                        '<form method="POST" action="${pageContext.request.contextPath}/usuariosController">' +
                            '<input type="hidden" name="accion" value="actualizar">' +
                            '<input type="hidden" name="id_usu" value="' + u.id_usu + '">' +

                            '<label>Documento:</label>' +
                            '<input type="number" name="documento" value="' + u.documento + '" required>' +

                            '<label>Nombres:</label>' +
                            '<input type="text" name="nombres" value="' + u.nombres + '" required>' +

                            '<label>Apellidos:</label>' +
                            '<input type="text" name="apellidos" value="' + u.apellidos + '" required>' +

                            '<label>Correo:</label>' +
                            '<input type="email" name="correo" value="' + u.correo + '" required>' +

                            '<label>Teléfono:</label>' +
                            '<input type="number" name="telefono" value="' + u.telefono + '" required>' +

                            '<label>Dirección:</label>' +
                            '<input type="text" name="direccion" value="' + u.direccion + '" required>' +

                            '<label>Contraseña:</label>' +
                            '<input type="password" name="password" value="" required>' +

                            '<label>Rol:</label>' +
                            '<select name="rol" required>' +
                                '<option value="">Seleccionar</option>' +
                                '<option value="A" ' + rolAdmin + '>Administrador</option>' +
                                '<option value="EV" ' + rolVend + '>Vendedor</option>' +
                                '<option value="EP" ' + rolProd + '>Producción</option>' +
                            '</select>' +

                            '<div style="margin-top: 20px;">' +
                                '<button type="submit" class="btn">Actualizar</button>' +
                            '</div>' +
                        '</form>' +
                    '</div>',
                showConfirmButton: false,
                showCloseButton: true
            });
        })
        .catch(function(error) {
            console.error("Error al obtener el usuario para editar:", error);
        });
}




    // Función para eliminar usuario
    document.addEventListener("click", function (e) {
    if (e.target.classList.contains("update-btn")) {
        const id = e.target.getAttribute("data-id");
        const estadoActual = e.target.getAttribute("data-estado");

        const nuevoEstado = estadoActual === "A" ? "I" : "A";
        const mensaje = estadoActual === "A"
            ? "¿Estás seguro de que quieres desactivar este usuario?"
            : "¿Estás seguro de que quieres activar este usuario?";

        Swal.fire({
            title: 'Confirmación',
            text: mensaje,
            icon: 'question',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#aaa',
            confirmButtonText: 'Sí, confirmar'
        }).then((result) => {
            if (result.isConfirmed) {
                window.location.href = "usuariosController?accion=cambiarEstado&id="+id+"&nuevoEstado="+nuevoEstado;
            }
        });
    }
});


    // Listener global para los botones (delegación de eventos)

    document.addEventListener("click", function (e) {
        if (e.target.classList.contains("edit-btn")) {
            const id = e.target.getAttribute("data-id");
            actualizarUsuario(id);
        }

        if (e.target.classList.contains("delete-btn")) {
            const id = e.target.getAttribute("data-id");
            eliminarUsuario(id);
        }
    });
    
    function eliminarUsuario(id) {
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
            window.location.href = 'usuariosController?accion=eliminar&id=' + id;
        }
    });
}

</script>

    
<script>
    
        function agregarUsuario() {
            Swal.fire({
                html:`
               <div class="registro-section">
                    <h2> Registrar Nuevo Usuario</h2>
                    
                    <div class="alert alert-success" id="alertSuccess">
                        Usuario registrado exitosamente
                    </div>
                    
                    <div class="alert alert-error" id="alertError">
                        Error al registrar usuario
                    </div>

                    <form id="registroForm" method="POST" action="${pageContext.request.contextPath}/usuariosController?accion=agregar">
                            <div class="form-group">
                                <label for="documento">Documento:</label>
                                <input type="number" id="documento" name="documento" required>
                            </div>
                        <div class="form-row">
                            
                            <div class="form-group">
                                <label for="nombres">Nombres:</label>
                                <input type="text" id="nombres" name="nombres" required>
                            </div>
                            <div class="form-group">
                                <label for="apellidos">Apellidos:</label>
                                <input type="text" id="apellidos" name="apellidos" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="correo">Correo Electrónico:</label>
                            <input type="email" id="correo" name="correo" required>
                        </div>
                        
                        
                        <div class="form-row">
                            <div class="form-group">
                                <label for="usuario">Direccion:</label>
                                <input type="text" id="direccion" name="direccion">
                            </div>
                             
                            <div class="form-group">
                                <label for="telefono">Teléfono:</label>
                                <input type="number" id="telefono" name="telefono" required>
                            </div>
                        </div>
                        

                            <div class="form-group">
                                <label for="password">Contraseña:</label>
                                <input type="password" id="password" name="password" required>
                            </div>
                            
            
                            <div class="form-group">
                                <label for="rol">Rol:</label>
                                <select id="rol" name="rol" required>
                                    <option value="">Seleccionar rol</option>
                                    <option value="A">Administrador</option>
                                    <option value="EV">Vendedor</option>
                                    <option value="EP">Produccion</option>
                                 
                                </select>
                            </div>

                        <input type="hidden" name="estado" value="Activo">

                        <div style="display: flex; gap: 1rem; margin-top: 2rem;">
                            <button type="submit" class="btn">Registrar Usuario</button>
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