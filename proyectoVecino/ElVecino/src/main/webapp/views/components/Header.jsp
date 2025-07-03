<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="models.usuario" %>
<%
    usuario usu = (usuario) session.getAttribute("usuario");
    if (usu == null) {
        response.sendRedirect(request.getContextPath() + "/views/formularios/login.jsp");
        return;
    }
    String rol = usu.getRol();
    String tipoUsuario = "Empleado";
    if ("A".equals(rol)) tipoUsuario = "Administrador";
%>

<!-- Estilos globales y fuentes -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">
<link rel="preconnect" href="https://fonts.googleapis.com" />
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet" />
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />

<!-- Encabezado -->
<header class="header">
    <div class="logo-container">
        <img src="${pageContext.request.contextPath}/assets/img/logo2.jpg" alt="Logo" />
    </div>
    <div class="header-title">
        <h1>EL VECINO AMIGO</h1>
    </div>
    <div class="user-info">
        <span><%= tipoUsuario %>: <%= usu.getNombreUsuario() %></span>
        <a href="${pageContext.request.contextPath}/index.jsp" class="logout-btn">Cerrar Sesión</a>
    </div>
</header>
