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
            max-width: 600px;
            margin-left: auto;
            margin-right: auto;
        }

        .form-group {
            margin-bottom: 1rem;
        }

        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: 500;
        }

        .form-group input, .form-group select {
            width: 100%;
            padding: 10px;
            border-radius: 6px;
            border: 1px solid #ccc;
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

        /* Modal básico para eliminar */
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
                            <td>2025-07-05</td>
                            <td>Juan Pérez</td>
                            <td>$120.000</td>
                            <td class="acciones">
                                <button class="edit-btn" onclick="alert('Función editar pendiente')">✏️</button>
                                <button class="delete-btn" onclick="abrirEliminar()">🗑️</button>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="4" class="empty-table">No hay más pagos registrados</td>
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
        <h3>¿Estás seguro que deseas eliminar este pago?</h3>
        <form action="pagoController" method="get">
            <input type="hidden" name="accion" value="eliminar">
            <input type="hidden" name="idPago" id="idPagoEliminar">
            <button type="submit" class="confirm-btn">Sí, eliminar</button>
            <button type="button" class="cancel-btn" onclick="cerrarEliminar()">Cancelar</button>
        </form>
    </div>
</div>

<script>
    function abrirEliminar(idPago) {
        document.getElementById("idPagoEliminar").value = idPago || '1';
        document.getElementById("modalEliminar").style.display = 'flex';
    }

    function cerrarEliminar() {
        document.getElementById("modalEliminar").style.display = 'none';
    }
</script>

<jsp:include page="../components/Footer.jsp" />
<script src="${pageContext.request.contextPath}/assets/js/pagos.js"></script>
</body>
</html>
