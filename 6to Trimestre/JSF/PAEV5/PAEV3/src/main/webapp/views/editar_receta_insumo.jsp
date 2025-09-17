<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="models.receta_insumos" %>

<%
    receta_insumos ri = (receta_insumos) request.getAttribute("recetaInsumo");
    if (ri == null) {
        out.println("<p>Error: Insumo no encontrado.</p>");
        return;
    }
    double cantidad = ri.getCantidad();
    String cantidadStr = (cantidad >= 0) ? String.valueOf(cantidad) : "0";
%>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8" />
        <title>Editar Insumo de Receta</title>
        <style>
            /* Reset básico */
            *, *::before, *::after {
                box-sizing: border-box;
            }
            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background-color: #f9fafb;
                margin: 0;
                padding: 20px;
                color: #222;
            }
            form {
                max-width: 600px;
                margin: auto;
                background: #fff;
                padding: 25px 30px;
                border-radius: 12px;
                box-shadow: 0 4px 12px rgba(0,0,0,0.15);
            }
            h2 {
                text-align: center;
                color: #d62828;
                margin-bottom: 25px;
                font-weight: 700;
                font-size: 1.8rem;
            }
            label {
                display: block;
                margin-top: 15px;
                font-weight: 600;
                font-size: 1.1rem;
                color: #555;
            }
            input[type="text"], input[type="number"] {
                width: 100%;
                padding: 10px 12px;
                margin-top: 6px;
                font-size: 1rem;
                border: 1.8px solid #ccc;
                border-radius: 8px;
                transition: border-color 0.3s ease;
            }
            input[type="text"]:focus,
            input[type="number"]:focus {
                outline: none;
                border-color: #d62828;
                box-shadow: 0 0 6px rgba(214, 40, 40, 0.4);
            }
            .buttons {
                margin-top: 30px;
                display: flex;
                justify-content: space-between;
            }
            button {
                padding: 12px 28px;
                font-size: 1rem;
                border-radius: 10px;
                border: none;
                cursor: pointer;
                font-weight: 600;
                transition: background-color 0.3s ease;
                user-select: none;
            }
            button#guardarBtn {
                background-color: #d62828;
                color: white;
            }
            button#guardarBtn:hover {
                background-color: #b71c1c;
            }
            button#cancelarBtn {
                background-color: #aaa;
                color: white;
            }
            button#cancelarBtn:hover {
                background-color: #777;
            }
            /* Responsividad */
            @media (max-width: 640px) {
                form {
                    padding: 20px;
                }
                .buttons {
                    flex-direction: column;
                    gap: 15px;
                }
                button {
                    width: 100%;
                }
            }
        </style>
    </head>
    <body>


        <form action="<%= request.getContextPath()%>/receta_insumosController" method="post" id="formEditar">
                      

            
            

            <h2>Editar Insumo de Receta</h2>

            <input type="hidden" name="accion" value="editar" />
            <input type="hidden" name="id_ri" value="<%= ri.getId_rec_ins()%>" />
            <input type="hidden" name="id_rec" value="<%= ri.getId_rec()%>" />
            <input type="hidden" name="id_ins" value="<%= ri.getId_ins()%>" />


            <label for="nombre_insumo">Insumo:</label>
            <input type="text" id="nombre_insumo" name="nombre_insumo" value="<%= ri.getNombre_insumo()%>" readonly />

            <label for="cantidad">Cantidad:</label>
            <input
                type="text"
                id="cantidad"
                name="cantidad"
                value="<%= cantidadStr%>"
                required
                autocomplete="off"
                placeholder="Ej: 1.50"
                />

            <label for="unidad">Unidad:</label>
            <input
                type="text"
                id="unidad"
                name="unidad"
                value="<%= ri.getUnidad() != null ? ri.getUnidad() : ""%>"
                required
                autocomplete="off"
                placeholder="Ej: gramos, ml"
                />

            <div class="buttons">
                <button type="submit" id="guardarBtn">Guardar Cambios</button>
                <button type="button" id="cancelarBtn">Cancelar</button>
            </div>
        </form>

        <script>
            // Validar cantidad decimal positivo con hasta 2 decimales
            document.getElementById('formEditar').addEventListener('submit', function (e) {
                const cantidadInput = document.getElementById('cantidad');
                const valor = cantidadInput.value.trim();

                const regex = /^\d+(\.\d{1,2})?$/;

                if (!regex.test(valor)) {
                    alert('Por favor ingrese una cantidad válida (número positivo, con hasta dos decimales).');
                    cantidadInput.focus();
                    e.preventDefault();
                }
            });

            // Evento para cerrar modal (botón cancelar)
            document.getElementById('cancelarBtn').addEventListener('click', function () {
                // Asumiendo que esta vista está cargada dentro de un modal en la página padre,
                // aquí llamamos a una función global para cerrar ese modal.
                if (window.parent && typeof window.parent.closeEditModal === "function") {
                    window.parent.closeEditModal();
                } else {
                    // Por si acaso, cerramos la ventana actual (útil si está en ventana aparte)
                    window.close();
                }
            });
        </script>

    </body>
</html>
