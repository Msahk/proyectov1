<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="models.usuario" %>

<%
    usuario usu = (usuario) session.getAttribute("usuario");
    if (usu == null || (!"A".equals(usu.getRol()) && !"E".equals(usu.getRol()))) {
        response.sendRedirect(request.getContextPath() + "/views/formularios/login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="es">
    <head>
        <jsp:include page="../components/Header.jsp" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/usuarios.css" />
    </head>
    <body>

        <jsp:include page="../components/Navbar.jsp" />

        <main class="main-content">
            <section class="dashboard">
                <h2>Gestión de Usuarios</h2>

                <div class="action-panel">
                    <button type="button" class="add-btn" id="addUserBtn">Agregar Usuario</button>
                </div>

                <div class="table-container">
                    <table class="tabla-actividad">
                        <thead>
                            <tr>
                                <th>Documento</th>
                                <th>Nombre de Usuario</th>
                                <th>Rol</th>
                                <th>Email</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty usuarios}">
                                    <c:forEach var="u" items="${usuarios}">
                                        <tr>
                                            <td>${u.docUsuario}</td>
                                            <td>${u.nombreUsuario}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${u.rol == 'A'}">Administrador</c:when>
                                                    <c:otherwise>Empleado</c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>${u.email}</td>
                                            <td>
                                                <div class="dropdown">
                                                    <button class="dropdown-toggle">⋮</button>
                                                    <ul class="dropdown-menu">
                                                        <li><a href="#" class="edit-btn" data-id="${u.docUsuario}">Editar</a></li>
                                                        <li><a href="#" class="delete-btn" data-id="${u.docUsuario}">Eliminar</a></li>
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
            </section>
        </main>

        <jsp:include page="../components/Footer.jsp" />
        <script src="${pageContext.request.contextPath}/assets/js/usuarios.js"></script>
    </body>
</html>
