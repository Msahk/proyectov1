<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="models.usuario" %>
<%
    usuario usu = (usuario) session.getAttribute("usuario");
    if (usu == null || !"A".equals(usu.getRol())) {
        response.sendRedirect(request.getContextPath() + "/views/formularios/login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <!-- Este include contiene tus fuentes, estilos y cabecera -->
    <jsp:include page="../components/Header.jsp" />
</head>
<body>
    <!-- Este include es tu barra de navegación -->
    <jsp:include page="../components/Navbar.jsp" />

    <main class="main-content">
        <section class="dashboard">
            <h2>Panel de Administración</h2>

            <div class="dashboard-container">
                <div class="dashboard-cards">
                    <div class="dashboard-card">
                        <h3>Usuarios</h3>
                        <div class="card-count">0</div>
                        <a href="${pageContext.request.contextPath}/views/dashboard/Usuarios.jsp" class="card-link">
                            <span class="material-symbols-outlined">group</span> Gestionar
                        </a>
                    </div>

                    <div class="dashboard-card">
                        <h3>Reportes</h3>
                        <div class="card-count">0</div>
                        <a href="${pageContext.request.contextPath}/views/Reportes.jsp" class="card-link">
                            <span class="material-symbols-outlined">insert_chart</span> Ver Reportes
                        </a>
                    </div>

                    <div class="dashboard-card">
                        <h3>Configuraciones</h3>
                        <div class="card-count">0</div>
                        <a href="${pageContext.request.contextPath}/views/dashboard/Configuracion.jsp" class="card-link">
                            <span class="material-symbols-outlined">settings</span> Administrar
                        </a>
                    </div>
                </div>

                <div class="actividad-reciente">
                    <h3>Actividad Reciente</h3>
                    <div class="table-container">
                        <table class="tabla-actividad">
                            <thead>
                                <tr>
                                    <th>Fecha</th>
                                    <th>Usuario</th>
                                    <th>Actividad</th>
                                    <th>Detalle</th>
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

    <!-- Pie de página -->
    <jsp:include page="../components/Footer.jsp" />
</body>
</html>
