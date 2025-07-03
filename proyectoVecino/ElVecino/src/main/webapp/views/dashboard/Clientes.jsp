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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/clientes.css" />
</head>
<body>
    <!-- Barra de navegación común -->
    <jsp:include page="../components/Navbar.jsp" />

    <main class="main-content">
        <section class="dashboard">
            <h2>Gestión de Clientes</h2>

            <div class="action-panel">
                <div class="search-bar">
                    <input type="text" id="searchClient" placeholder="Buscar cliente..." />
                    <button class="search-btn">Buscar</button>
                </div>
                <button type="button" class="add-btn" id="addClientBtn">Agregar Cliente</button>
            </div>

            <div class="table-container">
                <table class="tabla-actividad">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nombre</th>
                            <th>Apellidos</th>
                            <th>Teléfono</th>
                            <th>Dirección</th>
                            <th>Categoría</th>
                            <th>Fecha Registro</th>
                            <th>Límite Crédito</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody id="tablaClientes">
                        <tr>
                            <td colspan="9" class="empty-table">No hay clientes registrados</td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <!-- Modal -->
            <div class="modal" id="clientModal">
                <div class="modal-content">
                    <div class="modal-header">
                        <h3>Agregar Nuevo Cliente</h3>
                        <span class="close-modal">&times;</span>
                    </div>
                    <div class="modal-body">
                        <form id="clientForm">
                            <div class="form-group">
                                <label for="nombre">Nombre:</label>
                                <input type="text" id="nombre" name="nombre" required />
                            </div>
                            <div class="form-group">
                                <label for="apellidos">Apellidos:</label>
                                <input type="text" id="apellidos" name="apellidos" required />
                            </div>
                            <div class="form-group">
                                <label for="telefono">Teléfono:</label>
                                <input type="tel" id="telefono" name="telefono" required />
                            </div>
                            <div class="form-group">
                                <label for="direccion">Dirección:</label>
                                <input type="text" id="direccion" name="direccion" required />
                            </div>
                            <div class="form-group">
                                <label for="categoria">Categoría Crediticia:</label>
                                <select id="categoria" name="categoria" required>
                                    <option value="A">A</option>
                                    <option value="B">B</option>
                                    <option value="C">C</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="limite">Límite de Crédito:</label>
                                <input type="number" id="limite" name="limite" required />
                            </div>
                            <div class="form-actions">
                                <button type="submit" class="save-btn">Guardar</button>
                                <button type="button" class="cancel-btn">Cancelar</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

        </section>
    </main>

    <!-- Pie de página común -->
    <jsp:include page="../components/Footer.jsp" />

    <script src="${pageContext.request.contextPath}/assets/js/clientes.js"></script>
</body>
</html>
