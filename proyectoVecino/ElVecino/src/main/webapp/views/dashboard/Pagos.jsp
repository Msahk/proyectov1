<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <!-- Estilos y fuentes comunes -->
    <jsp:include page="../components/Header.jsp" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/pagos.css" />
</head>
<body>
    <!-- Barra de navegación común -->
    <jsp:include page="../components/Navbar.jsp" />

    <main class="main-content">
        <section class="dashboard">
            <h2>Gestión de Pagos</h2>

            <form class="formulario" id="formPago">
                <div class="form-group">
                    <label for="cliente">Cliente</label>
                    <select id="cliente" name="cliente" required>
                        <option value="">Seleccionar cliente</option>
                        <!-- Aquí deberías iterar sobre los clientes registrados -->
                    </select>
                </div>
                <div class="form-group">
                    <label for="fecha">Fecha de Pago</label>
                    <input type="date" id="fecha" name="fecha" required />
                </div>
                <div class="form-group">
                    <label for="monto">Monto</label>
                    <input type="number" id="monto" name="monto" required />
                </div>
                <div class="form-group">
                    <button type="submit" class="logout-btn">Registrar Pago</button>
                </div>
            </form>

            <div class="actividad-reciente">
                <h3>Pagos Registrados</h3>
                <div class="table-container">
                    <table class="tabla-actividad">
                        <thead>
                            <tr>
                                <th>Fecha</th>
                                <th>Cliente</th>
                                <th>Monto</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody id="tablaPagos">
                            <tr>
                                <td colspan="4" class="empty-table">No hay pagos registrados</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </section>
    </main>

    <!-- Pie de página común -->
    <jsp:include page="../components/Footer.jsp" />

    <script src="${pageContext.request.contextPath}/assets/js/pagos.js"></script>
</body>
</html>
