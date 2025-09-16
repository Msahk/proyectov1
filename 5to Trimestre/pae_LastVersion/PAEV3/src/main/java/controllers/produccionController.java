package controllers;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.produccion;
import models.detalleProduccion;
import models.produccionDao;
import models.usuarios;

@WebServlet("/produccionController")
public class produccionController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            request.setCharacterEncoding("UTF-8");

            String accion = request.getParameter("accion");
            String fechaStr = request.getParameter("fecha_produccion");
            String tipo = request.getParameter("tipo");
            int cantidad = Integer.parseInt(request.getParameter("cantidad"));
            String estado = request.getParameter("estado");

            usuarios usu = (usuarios) request.getSession().getAttribute("usuarios");
            int idRes = usu.getId_usu();

            produccion prod = new produccion();
            prod.setFecha_produccion(Date.valueOf(fechaStr));
            prod.setTipo(tipo);
            prod.setCantidad(cantidad);
            prod.setId_res(idRes);
            prod.setEstado(estado);

            // Capturar detalles
            String[] idInsumos = request.getParameterValues("id_ins[]");
            String[] cantidades = request.getParameterValues("cantidad_ins[]");

            List<detalleProduccion> listaDetalles = new ArrayList<>();
            if (idInsumos != null && cantidades != null) {
                for (int i = 0; i < idInsumos.length; i++) {
                    detalleProduccion d = new detalleProduccion();
                    d.setId_ins(Integer.parseInt(idInsumos[i]));
                    d.setCantidadNecesaria(Double.parseDouble(cantidades[i]));
                    listaDetalles.add(d);
                }
            }

            prod.setDetalles(listaDetalles);
            produccionDao dao = new produccionDao();

            if ("editar".equalsIgnoreCase(accion)) {
                int id = Integer.parseInt(request.getParameter("id_proc"));
                prod.setId_proc(id);

                // 1. Actualizar la tabla producción
                dao.actualizarProduccion(prod);

                // 2. Actualizar los detalles
                dao.actualizarDetallesProduccion(id, listaDetalles);

                response.sendRedirect("produccionController");
            } else {
                boolean exito = dao.registrarProduccion(prod);
                if (exito) {
                    response.sendRedirect("produccionController");
                } else {
                    response.sendRedirect("views/produccion.jsp?msg=error");
                }
            }

        } catch (SQLException sqle) {
            if (sqle.getMessage() != null && sqle.getMessage().contains("No hay stock suficiente")) {
                response.sendRedirect("views/produccion.jsp?error=stock");
            } else {
                sqle.printStackTrace();
                response.sendRedirect("views/produccion.jsp?msg=error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("views/produccion.jsp?msg=error");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            usuarios usu = (usuarios) request.getSession().getAttribute("usuarios");
            if (usu == null) {
                response.sendRedirect("index.jsp");
                return;
            }

            String accion = request.getParameter("accion");
            produccionDao dao = new produccionDao();

            if ("eliminar".equalsIgnoreCase(accion)) {
                int id = Integer.parseInt(request.getParameter("id"));
                dao.eliminarProduccion(id);
                response.sendRedirect("produccionController");
                return;
            }

            // Por defecto: listar
            List<produccion> lista = dao.listarTodasProducciones();
            request.setAttribute("listaProduccion", lista);
            request.getRequestDispatcher("views/produccion.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("views/produccion.jsp?msg=error");
        }
    }
}
