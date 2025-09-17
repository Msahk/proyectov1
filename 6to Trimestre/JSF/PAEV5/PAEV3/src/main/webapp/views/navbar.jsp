<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="models.usuarios" %>

<%
    usuarios usu = (usuarios) session.getAttribute("usuarios");
    if (usu == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
    String rol = usu.getRol();
    String vistaActual = (String) request.getAttribute("vistaActual"); // atributo opcional
%>

<style>
.usuario-header-info { display: flex; align-items: center; gap: 10px; color: black; }
.usuario-icono { font-size: 2rem; }
.usuario-datos { line-height: 1.2; }
.usuario-nombre { font-weight: bold; margin: 0; color: black; }
.usuario-rol { margin: 0; font-size: 0.85rem; color: black; }
</style>

<header class="header">
  <div class="logo">Péguele a la Empanada 🥟</div>
  <nav class="navbar">
    <% if ("recetas".equals(vistaActual)) { %>
        <!-- Si estamos en recetas.jsp -->
        <a href="${pageContext.request.contextPath}/produccion?accion=listar">Regresar</a>
        <a onclick="logout()">Cerrar Sesión</a>
    <% } else if ("inv_entradas".equals(vistaActual) || "inv_salidas".equals(vistaActual)) { %>
        <!-- Si estamos en inv_entradas.jsp o inv_salidas.jsp -->
        <a href="${pageContext.request.contextPath}/insumosController?accion=listar">Regresar</a>
        <a onclick="logout()">Cerrar Sesión</a>
    <% } else { %>
        <!-- Navbar normal según rol -->
        <% if ("A".equals(rol)) { %>
            <a href="${pageContext.request.contextPath}/usuariosController?accion=listar">Usuarios</a>
            <a href="${pageContext.request.contextPath}/ventasController?accion=listar">Ventas</a>
            <a href="${pageContext.request.contextPath}/pedidosController?accion=listar">Pedidos</a>
            <a href="${pageContext.request.contextPath}/insumosController?accion=listar">Inventario</a>
            <a href="${pageContext.request.contextPath}/produccion?accion=listar">Producción</a>
        <% } else if ("EP".equals(rol)) { %>
            <a href="${pageContext.request.contextPath}/insumosController?accion=listar">Inventario</a>
            <a href="${pageContext.request.contextPath}/produccion?accion=listar">Producción</a>
        <% } else if ("EV".equals(rol)) { %>
            <a href="${pageContext.request.contextPath}/ventasController?accion=listar">Ventas</a>
            <a href="${pageContext.request.contextPath}/pedidosController?accion=listar">Pedidos</a>
        <% } %>
        <a onclick="logout()">Cerrar Sesión</a>
    <% } %>
</nav>


</header>

<div class="usuario-header-info">
    <div class="usuario-icono">👤</div>
    <div class="usuario-datos">
        <p class="usuario-nombre"><%= usu.getNombres() + " " + usu.getApellidos() %></p>
        <p class="usuario-rol">
            <% if ("A".equals(rol)) { %>Administrador
            <% } else if ("EP".equals(rol)) { %>Producción
            <% } else if ("EV".equals(rol)) { %>Ventas
            <% } %>
        </p>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script>
function logout() {
    Swal.fire({
        title: '¿Cerrar sesión?',
        text: "¿Estás seguro de que deseas cerrar sesión?",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sí, cerrar sesión',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.isConfirmed) {
            window.location.href="${pageContext.request.contextPath}/logout";
        }
    });
}
</script>
