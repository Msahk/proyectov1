package controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import models.detalle_produccion;
import models.detalle_produccionDao;

@WebServlet(name = "ProduccionDetalleController", urlPatterns = {"/producciondetalle"})
public class producciondetalleController extends HttpServlet {

    detalle_produccionDao dao = new detalle_produccionDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if (accion == null) {
            response.sendRedirect("produccion.jsp");
            return;
        }

        switch (accion) {
            case "agregar":
                agregarDetalle(request, response);
                break;
            case "eliminar":
                eliminarDetalle(request, response);
                break;
            default:
                response.sendRedirect("produccion.jsp");
                break;
        }
    }

    private void agregarDetalle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id_proc = Integer.parseInt(request.getParameter("id_proc"));
            int id_sal = Integer.parseInt(request.getParameter("id_sal"));

            detalle_produccion d = new detalle_produccion();
            d.setId_proc(id_proc);
            d.setId_sal(id_sal);

            boolean agregado = dao.agregar(d);

            if (agregado) {
                request.setAttribute("mensaje", "Detalle de producción agregado con éxito");
            } else {
                request.setAttribute("error", "Error al agregar detalle");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Datos inválidos: " + e.getMessage());
        }

        request.getRequestDispatcher("produccion.jsp").forward(request, response);
    }

    private void eliminarDetalle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id_proc = Integer.parseInt(request.getParameter("id_proc"));
            boolean eliminado = dao.eliminarPorProduccion(id_proc);

            if (eliminado) {
                request.setAttribute("mensaje", "Detalle eliminado con éxito");
            } else {
                request.setAttribute("error", "No se pudo eliminar el detalle");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());
        }

        request.getRequestDispatcher("produccion.jsp").forward(request, response);
    }
}
