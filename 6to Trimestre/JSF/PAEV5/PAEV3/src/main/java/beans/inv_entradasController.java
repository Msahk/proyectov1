/*package beans;

import models.inv_entradas;
import models.insumosDao;
import java.io.IOException;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;

public class inv_entradasController extends HttpServlet {

    insumosDao dao = new insumosDao(); // Usamos insumosDao que contiene listarEntradas y demás

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion == null) accion = "listar";

        switch (accion) {
            case "listar":
                listar(request, response);
                break;
            case "eliminar":
                eliminar(request, response);
                break;
            default:
                listar(request, response);
        }
    }

    private void listar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Usamos el método de insumosDao que devuelve entradas
        List<inv_entradas> lista = dao.listarEntradas();
        request.setAttribute("listaEntradas", lista); // coincide con el JSP
        request.getRequestDispatcher("views/inv_entradas.jsp").forward(request, response);
    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        dao.eliminarEntrada(id); // Debes tener este método en insumosDao
        response.sendRedirect("inv_entradasController?accion=listar");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
*/