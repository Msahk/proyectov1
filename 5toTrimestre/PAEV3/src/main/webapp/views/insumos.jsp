<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="models.usuarios" %>

<%
    usuarios usu = (usuarios) session.getAttribute("usuarios");
    if (usu == null || ("EV".equals(usu.getRol()))) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
%>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Insumos</title>
    <link rel="stylesheet" href="../assets/css/insumos.css">
</head>
<body>
   <jsp:include page="navbar.jsp" />
   

    <div class="container">
        <div class="page-header">
            <h1>📦 Gestión de Insumos</h1>
           
        </div>

        <div class="alert alert-success" id="alertSuccess">
            Operación realizada exitosamente
        </div>

        
        <div class="stats-grid">
            <div class="stat-card">
                <div class="stat-number" id="totalInsumos">0</div>
                <div class="stat-label">Total Insumos</div>
            </div>
            <div class="stat-card">
                <div class="stat-number" id="stockBajo">0</div>
                <div class="stat-label">Stock Bajo</div>
            </div>
            <div class="stat-card">
                <div class="stat-number" id="valorInventario">0</div>
                <div class="stat-label">Valor Inventario</div>
            </div>
            <div class="stat-card">
                <div class="stat-number" id="proveedores">0</div>
                <div class="stat-label">Proveedores</div>
            </div>
        </div>

        <div class="actions-bar">
            <div class="filters">
                <div class="filter-group">
                   
                </div>
                <div class="filter-group">
                    <label>Stock:</label>
                    <select id="filtroStock" class="filter-select" onchange="filtrarInsumos()">
                        <option value="">Todos</option>
                        <option value="bajo">Stock Bajo</option>
                        <option value="medio">Stock Medio</option>
                        <option value="alto">Stock Alto</option>
                    </select>
                </div>
                <div class="filter-group">
                    <label>Buscar:</label>
                    <input type="text" id="buscarInsumo" class="filter-input" placeholder="Nombre del insumo..." onkeyup="filtrarInsumos()">
                </div>
            </div>
            <div>
                
                <button class="btn btn-success" onclick="generarReporte()">
                     Generar Reporte
                </button>
            </div>
        </div>

        
        <div class="insumos-table">
            <table class="table" id="tablaInsumos">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Insumo</th>
                        <th>Stock Actual</th>
                        <th>Unidad</th>
                        <th>Stock Mínimo</th>
                        <th>Estado</th>
                        <th>Precio Unitario</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <tr data-categoria="carnes" data-stock="alto">
                        <td>001</td>
                        <td><strong>Carne Molida</strong></td>                    
                        <td>15.5</td>
                        <td>kg</td>
                        <td>5.0</td>
                        <td><span class="stock-badge stock-alto">Alto</span></td>
                        <td>$12,000</td>
                        
                        <td>
                            <div class="actions">
                                <button class="btn btn-edit btn-small" onclick="editarInsumo(1)">✏️</button>
                                <button class="btn btn-stock btn-small" onclick="actualizarStock(1)">📊</button>
                            </div>
                        </td>
                    </tr>
                    <tr data-categoria="carnes" data-stock="medio">
                        <td>002</td>
                        <td><strong>Pollo Desmechado</strong></td>
                        <td>8.2</td>
                        <td>kg</td>
                        <td>4.0</td>
                        <td><span class="stock-badge stock-medio">Medio</span></td>
                        <td>$14,500</td>
                       
                        <td>
                            <div class="actions">
                                <button class="btn btn-edit btn-small" onclick="editarInsumo(2)">✏️</button>
                                <button class="btn btn-stock btn-small" onclick="actualizarStock(2)">📊</button>
                            </div>
                        </td>
                    </tr>
                    <tr data-categoria="vegetales" data-stock="bajo">
                        <td>003</td>
                        <td><strong>Cebolla Blanca</strong></td>
                        <td>2.1</td>
                        <td>kg</td>
                        <td>3.0</td>
                        <td><span class="stock-badge stock-bajo">Bajo</span></td>
                        <td>$3,500</td>
                        
                        <td>
                            <div class="actions">
                                <button class="btn btn-edit btn-small" onclick="editarInsumo(3)">✏️</button>
                                <button class="btn btn-stock btn-small" onclick="actualizarStock(3)">📊</button>
                            </div>
                        </td>
                    </tr>
                    <tr data-categoria="condimentos" data-stock="alto">
                        <td>004</td>
                        <td><strong>Sal</strong></td>
                        <td>5.8</td>
                        <td>kg</td>
                        <td>1.0</td>
                        <td><span class="stock-badge stock-alto">Alto</span></td>
                        <td>$1,200</td>
                       
                        <td>
                            <div class="actions">
                                <button class="btn btn-edit btn-small" onclick="editarInsumo(4)">✏️</button>
                                <button class="btn btn-stock btn-small" onclick="actualizarStock(4)">📊</button>
                            </div>
                        </td>
                    </tr>
                    <tr data-categoria="masas" data-stock="medio">
                        <td>005</td>
                        <td><strong>Harina de Maíz</strong></td>
                        <td>12.0</td>
                        <td>kg</td>
                        <td>8.0</td>
                        <td><span class="stock-badge stock-medio">Medio</span></td>
                        <td>$4,800</td>
                        
                        <td>
                            <div class="actions">
                                <button class="btn btn-edit btn-small" onclick="editarInsumo(5)">✏️</button>
                                <button class="btn btn-stock btn-small" onclick="actualizarStock(5)">📊</button>
                            </div>
                        </td>
                    </tr>
                    <tr data-categoria="aceites" data-stock="alto">
                        <td>006</td>
                        <td><strong>Aceite de Girasol</strong></td>

                        <td>25.0</td>
                        <td>lt</td>
                        <td>10.0</td>
                        <td><span class="stock-badge stock-alto">Alto</span></td>
                        <td>$6,200</td>
                        
                        <td>
                            <div class="actions">
                                <button class="btn btn-edit btn-small" onclick="editarInsumo(6)">✏️</button>
                                <button class="btn btn-stock btn-small" onclick="actualizarStock(6)">📊</button>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>

    
    <div id="modalInsumo" class="modal">
        <div class="modal-content">
            <div class="modal-header">
                <h3 id="tituloModal">Agregar Insumo</h3>
                <button class="close" onclick="cerrarModal()">&times;</button>
            </div>
            <form id="formInsumo">
                <div class="form-group">
                    <label>Nombre del Insumo *</label>
                    <input type="text" id="nombreInsumo" required>
                </div>
                <div class="form-group">
                   
                </div>
                <div class="form-group">
                    <label>Stock Actual *</label>
                    <input type="number" id="stockActual" step="0.1" required>
                </div>
                <div class="form-group">
                    <label>Unidad de Medida *</label>
                    <select id="unidadMedida" required>
                        <option value="">Seleccionar unidad</option>
                        <option value="kg">Kilogramos (kg)</option>
                        <option value="lt">Litros (lt)</option>
                        <option value="und">Unidades (und)</option>
                        <option value="gr">Gramos (gr)</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>Stock Mínimo *</label>
                    <input type="number" id="stockMinimo" step="0.1" required>
                </div>
                <div class="form-group">
                    <label>Precio Unitario *</label>
                    <input type="number" id="precioUnitario" required>
                </div>
             
                <div style="display: flex; gap: 1rem; margin-top: 1.5rem;">
                    <button type="submit" class="btn btn-primary" style="flex: 1;">Guardar</button>
                    <button type="button" class="btn" style="background-color: #757575; color: white; flex: 1;" onclick="cerrarModal()">Cancelar</button>
                </div>
            </form>
        </div>
    </div>

    
    <div id="modalStock" class="modal">
        <div class="modal-content">
            <div class="modal-header">
                <h3>Actualizar Stock</h3>
                <button class="close" onclick="cerrarModalStock()">&times;</button>
            </div>
            <form id="formStock">
                <div class="form-group">
                    <label>Insumo</label>
                    <input type="text" id="nombreInsumoStock" readonly>
                </div>
                <div class="form-group">
                    <label>Stock Actual</label>
                    <input type="number" id="stockActualMostrar" step="0.1" readonly>
                </div>
                <div class="form-group">
                    <label>Tipo de Movimiento *</label>
                    <select id="tipoMovimiento" required>
                        <option value="">Seleccionar tipo</option>
                        <option value="entrada">Entrada (Compra/Recepción)</option>
                        <option value="salida">Salida (Consumo/Pérdida)</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>Cantidad *</label>
                    <input type="number" id="cantidadMovimiento" step="0.1" required>
                </div>
                <div class="form-group">
                    <label>Observaciones</label>
                    <textarea id="observacionesStock" rows="3" placeholder="Motivo del movimiento..."></textarea>
                </div>
                <div style="display: flex; gap: 1rem; margin-top: 1.5rem;">
                    <button type="submit" class="btn btn-success" style="flex: 1;">Actualizar Stock</button>
                    <button type="button" class="btn" style="background-color: #757575; color: white; flex: 1;" onclick="cerrarModalStock()">Cancelar</button>
                </div>
            </form>
        </div>
    </div>

    <footer class="footer">
        <p>&copy; 2025 Péguele a la Empanada. Sistema de Insumos.</p>
    </footer>

    <script>
        
        let insumos = [
            {
                id: 1,
                nombre: 'Carne Molida',
                categoria: 'carnes',
                stockActual: 15.5,
                unidad: 'kg',
                stockMinimo: 5.0,
                precioUnitario: 12000,
                proveedor: 'Frigorífico Del Valle'
            },
            {
                id: 2,
                nombre: 'Pollo Desmechado',
                categoria: 'carnes',
                stockActual: 8.2,
                unidad: 'kg',
                stockMinimo: 4.0,
                precioUnitario: 14500,
                proveedor: 'Avícola Santa Fe'
            }
        ];

        let insumoEditando = null;

        function filtrarInsumos() {
            const categoria = document.getElementById('filtroCategoria').value;
            const stock = document.getElementById('filtroStock').value;
            const busqueda = document.getElementById('buscarInsumo').value.toLowerCase();
            
            const filas = document.querySelectorAll('#tablaInsumos tbody tr');
            
            filas.forEach(fila => {
                const categoriaFila = fila.getAttribute('data-categoria');
                const stockFila = fila.getAttribute('data-stock');
                const textoFila = fila.textContent.toLowerCase();
                
                let mostrar = true;
                
                if (categoria && categoriaFila !== categoria) mostrar = false;
                if (stock && stockFila !== stock) mostrar = false;
                if (busqueda && !textoFila.includes(busqueda)) mostrar = false;
                
                fila.style.display = mostrar ? '' : 'none';
            });
        }

        function abrirModalInsumo(id = null) {
            insumoEditando = id;
            const modal = document.getElementById('modalInsumo');
            const titulo = document.getElementById('tituloModal');
            
            if (id) {
                titulo.textContent = 'Editar Insumo';
                // Aquí cargarías los datos del insumo para editar
            } else {
                titulo.textContent = 'Agregar Insumo';
                document.getElementById('formInsumo').reset();
            }
            
            modal.style.display = 'block';
        }

        function cerrarModal() {
            document.getElementById('modalInsumo').style.display = 'none';
            insumoEditando = null;
        }

        function editarInsumo(id) {
            abrirModalInsumo(id);
        }

        function actualizarStock(id) {
            const modal = document.getElementById('modalStock');
            document.getElementById('nombreInsumoStock').value = `Insumo #${id}`;
            document.getElementById('stockActualMostrar').value = '10.5';
            document.getElementById('formStock').reset();
            modal.style.display = 'block';
        }

        function cerrarModalStock() {
            document.getElementById('modalStock').style.display = 'none';
        }

        function generarReporte() {
            mostrarAlerta('Generando reporte de inventario...');
        }

        function mostrarAlerta(mensaje) {
            const alerta = document.getElementById('alertSuccess');
            alerta.textContent = mensaje;
            alerta.style.display = 'block';
            setTimeout(() => {
                alerta.style.display = 'none';
            }, 3000);
        }

        
        document.getElementById('formInsumo').addEventListener('submit', function(e) {
            e.preventDefault();
            mostrarAlerta(insumoEditando ? 'Insumo actualizado exitosamente' : 'Insumo agregado exitosamente');
            cerrarModal();
        });

        document.getElementById('formStock').addEventListener('submit', function(e) {
            e.preventDefault();
            mostrarAlerta('Stock actualizado exitosamente');
            cerrarModalStock();
        });

        
        window.addEventListener('click', function(event) {
            const modalInsumo = document.getElementById('modalInsumo');
            const modalStock = document.getElementById('modalStock');
            
            if (event.target === modalInsumo) {
                cerrarModal();
            }
            if (event.target === modalStock) {
                cerrarModalStock();
            }
        });
    </script>
</body>
</html>