<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Péguele a la Empanada</title>

   
    <link rel="stylesheet" href="assets/css/index.css">
</head>

<body>

    
    <header class="header">
        <div class="logo">Péguele a la Empanada 🥟</div>
        <nav class="navbar">
            <a href="#inicio">Inicio</a>
            
          <a class="boton" onclick="login()">Ingresar</a>

        </nav>
    </header>
<main>
    
    <section class="inicio" id="inicio">
        <div class="contenido">
            <h1>¡Bienvenido al sistema de gestión de ventas y producción!</h1>
            <p>Optimiza tus procesos, organiza tu inventario y mantén el control total de tus operaciones desde un solo lugar.</p>
            <a onclick="login()" class="boton">Ingresar al sistema</a>
        </div>
        <div class="imagenes">
            <img src="assets/img/1.png" alt="Empanada tradicional">
            <img src="assets/img/2.png" alt="Empanada de queso">
            <img src="assets/img/4.png" alt="Empanada picante">
        </div>
    </section>

    
    
</main>
    
    <footer class="footer">
        <p>&copy; 2025 Péguelo a la Empanada. Todos los derechos reservados.</p>
    </footer>


<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

    <script>
        
            window.olvidePassword = function () {
                Swal.fire({
                        html: `
                                    <section class='ingreso' id='ingreso'>
                                        <h2>Recuperar contraseña</h2>
                                        <form  method='POST' action="${pageContext.request.contextPath}/usuariosController" class='formulario-ingreso'>
                                            <input type="hidden" name='accion' value='olvidar'>
                                            <input type='text' name='documento' placeholder='Documento' required>
                                            <input type='text' name='correo' placeholder='Correo' required>
                                            <input type='password' name='password' placeholder='Contraseña' required>
                                            <button type='submit' class="boton" name='accion' value='Ingresar'>
                                                <p>Ingresar</p>
                                            </button>
                                    </form>
                                </section>
                                `,
                        showConfirmButton: false,
                        showCloseButton: true
                    });
            }   
        </script>
    <script>
        
        
        window.login = function () {
            Swal.fire({
                html: `
                <section class='ingreso' id='ingreso'>
                        <h2>Inicia sesión</h2>
                        <form  method='POST' action="${pageContext.request.contextPath}/usuariosController" class='formulario-ingreso'>
                            <input type="hidden" name='accion' value='Ingresar'>
                            <input type='text' name='correo' placeholder='Correo o documento' required>
                            <input type='password' name='password' placeholder='Contraseña' required>
                            <button type='submit' class="boton" name='accion' value='Ingresar'>
                                <p>Iniciar</p>
                            </button>
                    </form>
                        <a href='#' id='forgot-link'>Olvidé mi contraseña</a>
                </section>
                `,
                showConfirmButton: false,
                showCloseButton: true,
                didOpen: () => {
                  const forgot = document.getElementById('forgot-link');
                  if (forgot) {
                    forgot.addEventListener('click', (e) => {
                      e.preventDefault();
                      Swal.close();
                      window.olvidePassword();
                    });
                  }
                }
                 
            });
        }
    </script>
        
    


    
    


</body>

<% if (request.getAttribute("loginError") != null) { %>
<script>
    Swal.fire({
        icon: 'error',
        title: 'Error...',
        text: '<%= request.getAttribute("loginError") %>',
        confirmButtonColor: '#d33'
    });
</script>
<% } %>
<% if (request.getAttribute("Exitosamente") != null) {%>
<script>
    Swal.fire({
        position: "top-end",
        icon: 'success',
        title: 'Contraseña actualizada',
        text: '<%= request.getAttribute("Exitosamente") %>',
        confirmButtonColor: '#d33',
        showConfirmButton: false,
        timer: 1500
    });
</script>
<% }%>
</html>
