<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="models.usuario" %>

<%
    usuario usu = (usuario) request.getAttribute("usuario");
%>

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
        <h2>Editar Usuario</h2>

        <form action="${pageContext.request.contextPath}/usuarioController" method="post" class="formulario">
            <input type="hidden" name="accion" value="actualizar"/>

            <label for="docUsuario">Documento:</label>
            <input type="text" name="docUsuario" id="docUsuario" value="<%= usu.getDocUsuario() %>" readonly>

            <label for="nombreUsuario">Nombre:</label>
            <input type="text" name="nombreUsuario" id="nombreUsuario" value="<%= usu.getNombreUsuario() %>" required>

            <label for="rol">Rol:</label>
            <select name="rol" id="rol" required>
                <option value="A" <%= "A".equals(usu.getRol()) ? "selected" : "" %>>Administrador</option>
                <option value="E" <%= "E".equals(usu.getRol()) ? "selected" : "" %>>Empleado</option>
            </select>

            <label for="email">Correo:</label>
            <input type="email" name="email" id="email" value="<%= usu.getEmail() %>" required>

            <label for="password">Contraseña:</label>
            <input type="password" name="password" id="password" value="<%= usu.getPassword() %>" required>

            <button type="submit" class="btn-primary">Actualizar</button>
            <a href="usuarioController?accion=listar" class="btn-secondary">Cancelar</a>
        </form>
    </section>
</main>

<jsp:include page="../components/Footer.jsp" />
</body>
</html>
