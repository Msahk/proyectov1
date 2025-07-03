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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/creditos.css" />
</head>
<body>
    <!-- Barra de navegación común -->
    <jsp:include page="../components/Navbar.jsp" />

    <main class="main-content">
        <section class="dashboard">
            <h2>Gestión de Créditos</h2>

            <div class="formulario">
                <div class="form-group">
                    <label for="cliente">Cliente</label>
                    <select id="cliente" name="cliente" required>
                        <option value="">Seleccionar cliente</option>
                        <!-- Opciones dinámicas -->
                    </select>
                </div>
                <div class="form-group">
                    <label for="fechaVencimiento">Fecha de Vencimiento</label>
                    <input type="date" id="fechaVencimiento" name="fechaVencimiento" required />
                </div>
                <div class="productos">
                    <h3>Productos</h3>
                    <div id="productosContainer">
                        <!-- Aquí se agregan dinámicamente productos -->
                        <div class="producto">
                            <label>Producto:</label>
                            <select name="producto" required>
                                <option value="">Seleccionar producto</option>
                            </select>
                            <label>Cantidad:</label>
                            <input type="number" name="cantidad" value="1" min="1" required />
                            <span class="subtotal">Subtotal: $0.00</span>
                        </div>
                    </div>
                    <button type="button" id="agregarProductoBtn">Agregar otro producto</button>
                </div>
                <div class="form-group">
                    <strong>Total:</strong> <span id="totalCredito">$0.00</span>
                </div>
                <div class="form-group">
                    <button type="submit" class="logout-btn">Registrar Crédito</button>
                </div>
            </div>

            <div class="actividad-reciente">
                <h3>Créditos Registrados</h3>
                <div class="table-container">
                    <table class="tabla-actividad">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Cliente</th>
                                <th>Fecha</th>
                                <th>Vencimiento</th>
                                <th>Total</th>
                                <th>Estado</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody id="tablaCreditos">
                            <tr>
                                <td colspan="7" class="empty-table">No hay créditos registrados</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </section>
    </main>

    <!-- Pie de página común -->
    <jsp:include page="../components/Footer.jsp" />

    <script src="${pageContext.request.contextPath}/assets/js/creditos.js"></script>
</body>
</html>
