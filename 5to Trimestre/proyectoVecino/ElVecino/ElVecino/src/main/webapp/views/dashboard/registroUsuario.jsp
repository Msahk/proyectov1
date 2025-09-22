<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <jsp:include page="../components/Header.jsp" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/usuarios.css">
</head>
<body>
<jsp:include page="../components/Navbar.jsp" />

<main class="main-content">
    <section class="dashboard">
        <h2>Registrar Nuevo Usuario</h2>

        <form action="${pageContext.request.contextPath}/usuarioController" method="post" class="formulario">
            <input type="hidden" name="accion" value="agregar"/>

            <label for="docUsuario">Documento:</label>
            <input type="text" name="docUsuario" id="docUsuario" required>

            <label for="nombreUsuario">Nombre:</label>
            <input type="text" name="nombreUsuario" id="nombreUsuario" required>

            <label for="rol">Rol:</label>
            <select name="rol" id="rol" required>
                <option value="A">Administrador</option>
                <option value="E">Empleado</option>
            </select>

            <label for="email">Correo:</label>
            <input type="email" name="email" id="email" required>

            <label for="password">Contraseña:</label>
            <input type="password" name="password" id="password" required>

            <button type="submit" class="btn-primary">Registrar</button>
            <a href="usuarioController?accion=listar" class="btn-secondary">Cancelar</a>
        </form>
    </section>
</main>

<jsp:include page="../components/Footer.jsp" />
</body>
</html>
