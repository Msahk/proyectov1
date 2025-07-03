<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="models.usuario" %>
<%
    usuario usu = (usuario) session.getAttribute("usuario");
    if (usu == null || (!"E".equals(usu.getRol()) && !"A".equals(usu.getRol()))) {
        // Solo permite acceso a empleados y administradores
        response.sendRedirect(request.getContextPath() + "/views/formularios/login.jsp");
        return;
    }
%>

<jsp:include page="../components/Header.jsp" />
<jsp:include page="../components/Navbar.jsp" />

<main class="main-content">
    <section class="dashboard">
        <h2>Panel del Empleado</h2>

        <div class="dashboard-container">
            <div class="dashboard-cards">
                <div class="dashboard-card">
                    <h3>Clientes</h3>
                    <div class="card-count">0</div>
                    <a href="${pageContext.request.contextPath}/views/dashboard/Clientes.jsp" class="card-link">Ver todos</a>
                </div>

                <div class="dashboard-card">
                    <h3>Créditos Activos</h3>
                    <div class="card-count">0</div>
                    <a href="${pageContext.request.contextPath}/views/dashboard/Creditos.jsp" class="card-link">Ver todos</a>
                </div>

                <div class="dashboard-card">
                    <h3>Productos</h3>
                    <div class="card-count">0</div>
                    <a href="${pageContext.request.contextPath}/views/dashboard/Productos.jsp" class="card-link">Ver todos</a>
                </div>

                <div class="dashboard-card">
                    <h3>Pagos Recientes</h3>
                    <div class="card-count">0</div>
                    <a href="${pageContext.request.contextPath}/views/dashboard/Pagos.jsp" class="card-link">Ver todos</a>
                </div>
            </div>

            <div class="actividad-reciente">
                <h3>Actividad Reciente</h3>
                <div class="table-container">
                    <table class="tabla-actividad">
                        <thead>
                            <tr>
                                <th>Fecha</th>
                                <th>Cliente</th>
                                <th>Actividad</th>
                                <th>Monto</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td colspan="4" class="empty-table">No hay actividad reciente</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </section>
</main>

<jsp:include page="../components/Footer.jsp" />
