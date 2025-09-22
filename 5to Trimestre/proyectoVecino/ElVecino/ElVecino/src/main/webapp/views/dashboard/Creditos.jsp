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
    <jsp:include page="../components/Header.jsp" />
    <style>
        h2 {
            margin-bottom: 1.5rem;
        }

        .main-content {
            padding: 2rem;
        }

        .formulario {
            background-color: #fff;
            padding: 20px;
            border-radius: 12px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            margin-bottom: 2rem;
            max-width: 800px;
            margin-left: auto;
            margin-right: auto;
        }

        .form-group {
            margin-bottom: 1rem;
        }

        label {
            font-weight: 500;
        }

        select, input[type="date"], input[type="number"] {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 6px;
        }

        .productos {
            margin: 1rem 0;
            padding: 1rem;
            background: #f8f9fa;
            border-radius: 8px;
        }

        .producto {
            display: grid;
            grid-template-columns: 1fr 1fr 1fr auto;
            align-items: center;
            gap: 10px;
            margin-bottom: 1rem;
        }

        .subtotal {
            font-weight: bold;
            color: #dc3545;
        }

        #agregarProductoBtn {
            background-color: #007bff;
            color: white;
            padding: 8px 12px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-weight: 500;
            margin-top: 10px;
        }

        .tabla-actividad {
            width: 100%;
            border-collapse: collapse;
        }

        .tabla-actividad th, .tabla-actividad td {
            padding: 12px;
            text-align: center;
            border: 1px solid #ccc;
        }

        .tabla-actividad th {
            background-color: #000;
            color: white;
        }

        .empty-table {
            text-align: center;
            color: #777;
            font-style: italic;
            padding: 20px;
        }

        .acciones button {
            background: none;
            border: none;
            cursor: pointer;
            font-size: 18px;
            margin: 0 5px;
        }

        .acciones .edit-btn {
            color: #007bff;
        }

        .acciones .delete-btn {
            color: #dc3545;
        }

        /* Modal eliminar */
        .modal {
            position: fixed;
            top: 0; left: 0; right: 0; bottom: 0;
            background: rgba(0,0,0,0.5);
            display: none;
            justify-content: center;
            align-items: center;
            z-index: 9999;
        }

        .modal-content {
            background: #fff;
            padding: 2rem;
            border-radius: 10px;
            width: 90%;
            max-width: 400px;
            text-align: center;
        }

        .modal-content button {
            padding: 10px 16px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            margin: 0 10px;
        }

        .confirm-btn {
            background-color: #dc3545;
            color: white;
        }

        .cancel-btn {
            background-color: #6c757d;
            color: white;
        }
    </style>
</head>
<body>
<jsp:include page="../components/Navbar.jsp" />

<main class="main-content">
    <section class="dashboard">
        <h2>Gestión de Créditos</h2>

        <form class="formulario" id="formCredito">
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
        </form>

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
                            <td>1</td>
                            <td>Maria Gómez</td>
                            <td>2025-07-05</td>
                            <td>2025-08-05</td>
                            <td>$320.000</td>
                            <td>Pendiente</td>
                            <td class="acciones">
                                <button class="edit-btn" onclick="alert('Función editar pendiente')">✏️</button>
                                <button class="delete-btn" onclick="abrirEliminar(1)">🗑️</button>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="7" class="empty-table">No hay más créditos registrados</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </section>
</main>

<!-- Modal Eliminar -->
<div class="modal" id="modalEliminar">
    <div class="modal-content">
        <h3>¿Estás seguro que deseas eliminar este crédito?</h3>
        <form action="creditoController" method="get">
            <input type="hidden" name="accion" value="eliminar">
            <input type="hidden" name="idCredito" id="idCreditoEliminar">
            <button type="submit" class="confirm-btn">Sí, eliminar</button>
            <button type="button" class="cancel-btn" onclick="cerrarEliminar()">Cancelar</button>
        </form>
    </div>
</div>

<script>
    function abrirEliminar(idCredito) {
        document.getElementById("idCreditoEliminar").value = idCredito;
        document.getElementById("modalEliminar").style.display = 'flex';
    }

    function cerrarEliminar() {
        document.getElementById("modalEliminar").style.display = 'none';
    }
</script>

<jsp:include page="../components/Footer.jsp" />
<script src="${pageContext.request.contextPath}/assets/js/creditos.js"></script>
</body>
</html>
