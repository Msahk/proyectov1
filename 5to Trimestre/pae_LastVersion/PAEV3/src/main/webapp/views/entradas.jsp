<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="models.usuarios, models.entradaDao, models.entrada, java.util.*" %>

<%
    usuarios usu = (usuarios) session.getAttribute("usuarios");
    if (usu == null || ("EV".equals(usu.getRol()))) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }

    int id_ins = Integer.parseInt(request.getParameter("id_ins"));
    List<entrada> entradas = entradaDao.obtenerPorInsumo(id_ins);
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Entradas del Insumo</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/inventario.css">
</head>
<body>

<jsp:include page="navbar.jsp" />

<div class="container">
    <div class="page-header">
        <h1>📥 Entradas del Insumo ID: <%= id_ins %></h1>
    </div>

    <div style="margin-bottom: 2rem;">
        <a href="${pageContext.request.contextPath}/views/inventario.jsp" class="btn btn-secondary">⬅️ Volver al Inventario</a>
    </div>

    <table class="table" style="width: 100%;">
        <thead>
            <tr>
                <th>ID Entrada</th>
                <th>Fecha</th>
                <th>Cantidad</th>
            </tr>
        </thead>
        <tbody>
            <%
                if (entradas != null && !entradas.isEmpty()) {
                    for (entrada e : entradas) {
            %>
            <tr>
                <td><%= e.getId_ent() %></td>
                <td><%= e.getFecha() %></td>
                <td><%= e.getCantidad() %></td>
            </tr>
            <%
                    }
                } else {
            %>
            <tr>
                <td colspan="3" style="text-align: center;">No hay entradas registradas para este insumo.</td>
            </tr>
            <% } %>
        </tbody>
    </table>
</div>

<footer class="footer">
    <p>&copy; 2025 Péguele a la Empanada. Sistema de Inventario.</p>
</footer>

</body>
</html>
