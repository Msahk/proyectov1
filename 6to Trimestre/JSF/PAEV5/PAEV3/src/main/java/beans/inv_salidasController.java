/*package beans;

import models.produccionDao;
import java.io.IOException;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;
import models.inv_salidas;

public class inv_salidasController extends HttpServlet {

    produccionDao dao = new produccionDao();

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
        // Usar método correcto para listar salidas
        List<inv_salidas> lista = dao.listarSalidas();
        request.setAttribute("salidas", lista);
        request.getRequestDispatcher("views/inv_salidas.jsp").forward(request, response);
    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        // Llamar al método correcto para eliminar una salida
        dao.eliminarSalida(id);
        response.sendRedirect("inv_salidasController?accion=listar");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
*/