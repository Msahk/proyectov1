<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>El Vecino</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <!-- Estilos -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styleForm.css">

        <!-- Fuentes -->
        <link rel="preconnect" href="https://fonts.googleapis.com" />
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet" />
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
    </head>
    <body>
        <div class="login">
            <h2>Bienvenido al Vecino Amigo</h2>
            <h3>Inicia sesión con tus datos</h3>

            <c:if test="${not empty fail}">
                <div class="error-msg">${fail}</div>
            </c:if>

            <form class="form" method="post" action="${pageContext.request.contextPath}/usuarioController">
                <div class="caja">
                    <input type="text" name="email" id="email" required autocomplete="email" />
                    <label for="email">Correo</label>
                </div>
                <div class="caja">
                    <input type="password" name="password" id="password" required autocomplete="current-password" />
                    <label for="password">Contraseña</label>
                </div>
                <button type="submit" name="accion" value="Ingresar">
                    <p>Iniciar</p>
                    <span class="material-symbols-outlined">arrow_forward</span>
                </button>
            </form>
            <a href="${pageContext.request.contextPath}/index.jsp" class="back-button">
                <span class="material-symbols-outlined">arrow_back</span>
                <span>Regresar al inicio</span>
            </a>

        </div>

        <!-- Íconos -->
        <script src="https://kit.fontawesome.com/2b530c1d65.js" crossorigin="anonymous"></script>
    </body>
</html>
