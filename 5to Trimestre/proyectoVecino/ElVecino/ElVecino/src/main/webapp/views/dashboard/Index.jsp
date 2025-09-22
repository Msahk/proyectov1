<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="models.usuario" %>
<%
    usuario usu = (usuario) session.getAttribute("usuario");
    if (usu == null || (!"E".equals(usu.getRol()) && !"A".equals(usu.getRol()))) {
        response.sendRedirect(request.getContextPath() + "/views/formularios/login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <jsp:include page="../components/Header.jsp" />
    <style>
        h2 {
            margin-bottom: 1.5rem;
        }

        .dashboard-cards {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
        }

        .dashboard-card {
            background: #fff;
            border: 1px solid #ddd;
            border-radius: 10px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            padding: 20px;
            width: 250px;
            text-align: center;
        }

        .dashboard-card h3 {
            margin-bottom: 10px;
            color: #000;
        }

        .card-count {
            font-size: 32px;
            font-weight: bold;
            color: #dc3545;
            margin-bottom: 10px;
        }

        .card-link {
            display: inline-block;
            margin-top: 8px;
            text-decoration: none;
            color: #000;
            font-weight: 500;
            border-bottom: 1px solid #000;
            padding-bottom: 2px;
        }

        .card-link:hover {
            font-weight: bold;
        }

        .actividad-reciente {
            margin-top: 30px;
            background: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            flex: 1;
        }

        .actividad-reciente h3 {
            margin-bottom: 15px;
            font-size: 18px;
            border-bottom: 1px solid #ccc;
            padding-bottom: 10px;
        }

        .tabla-actividad {
            width: 100%;
            border-collapse: collapse;
        }

        .tabla-actividad th, .tabla-actividad td {
            padding: 10px;
            border: 1px solid #ddd;
        }

        .tabla-actividad th {
            background-color: #000;
            color: #fff;
        }

        .empty-table {
            text-align: center;
            padding: 15px;
            color: #777;
            font-style: italic;
        }

        @media (max-width: 768px) {
            .dashboard-cards {
                flex-direction: column;
                align-items: center;
            }

            .dashboard-card {
                width: 100%;
                max-width: 400px;
            }
        }
    </style>
</head>
<body>
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
</body>
</html>
