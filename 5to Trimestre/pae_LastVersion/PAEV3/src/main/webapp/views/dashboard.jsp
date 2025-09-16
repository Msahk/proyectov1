<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="models.usuarios" %>



<%
    usuarios usu = (usuarios) session.getAttribute("usuarios");
    if (usu == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inicio</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dashboard.css">

    </head>
    
    
    <body>
        <jsp:include page="navbar.jsp" />
         
     

    <div class="container">
        <div class="welcome-card">
            <span class="welcome-icon">🎉</span>
            <h1 class="welcome-title">¡Bienvenido!</h1>
            <p class="welcome-subtitle">Sistema de Gestión</p>
            <p class="welcome-message">
                Accede a todas las funcionalidades de tu negocio desde el menú de navegación. 
                ¡Que tengas un excelente día de trabajo!
            </p>
        </div>
    </div>

    <footer class="footer">
        <p>&copy; 2025 Péguele a la Empanada. Sistema de Gestión.</p>
    </footer>
</body>
</html>