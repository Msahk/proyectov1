<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="models.usuario" %>
<%
    usuario usu = (usuario) session.getAttribute("usuario");
    if (usu == null) {
        response.sendRedirect(request.getContextPath() + "/views/formularios/login.jsp");
        return;
    }
    String rol = usu.getRol();
%>

<nav class="main-nav">
    <ul>
        <li>
            <!-- Enlace corregido: ahora apunta al controlador con la acción listar -->
            <a href="${pageContext.request.contextPath}/clienteController?accion=listar" class="nav-link">
                <span class="material-symbols-outlined">group</span>
                <l>Clientes</l>
            </a>
        </li>
        <li>
            <!-- Asegúrate de tener un controlador para Créditos también -->
            <a href="${pageContext.request.contextPath}/views/dashboard/Creditos.jsp" class="nav-link">
                <span class="material-symbols-outlined">credit_card</span>
                <l>Créditos</l>
            </a>
        </li>
        <li>
            <!-- Y uno para Pagos si aplica -->
            <a href="${pageContext.request.contextPath}/views/dashboard/Pagos.jsp" class="nav-link">
                <span class="material-symbols-outlined">paid</span>
                <l>Pagos</l>
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/views/dashboard/Index.jsp" class="nav-link">
                <span class="material-symbols-outlined">dashboard</span>
                <l>Panel Principal</l>
            </a>
        </li>

        <% if ("A".equals(rol)) { %>
            <li>
                <a href="${pageContext.request.contextPath}/usuarioController?accion=listar" class="nav-link">
                    <span class="material-symbols-outlined">person</span>
                    <l>Usuarios</l>
                </a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/views/dashboard/Admin.jsp" class="nav-link">
                    <span class="material-symbols-outlined">admin_panel_settings</span>
                    <l>Administrar</l>
                </a>
            </li>
        <% } %>
    </ul>
</nav>
