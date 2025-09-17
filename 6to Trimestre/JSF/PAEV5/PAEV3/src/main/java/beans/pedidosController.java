/*package beans;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.pedidos;
import models.pedidosDao;


public class pedidosController extends HttpServlet {

    pedidos ped = new pedidos();
    pedidosDao p_dao = new pedidosDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8"); // corregido de "UFT-8"
        response.setContentType("text/html; charset=UTF-8");

        String accion = request.getParameter("accion");
        
        

        switch (accion != null ? accion : "") {
            case "listar":
                try {
                    List<pedidos> listaPedidos = p_dao.listar();
                    request.setAttribute("pedidos", listaPedidos);
                    request.getRequestDispatcher("views/pedidos.jsp").forward(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("error", "No se pudo listar pedidos");
                    request.getRequestDispatcher("error.jsp").forward(request, response); // por si tienes error.jsp
                }
                break;

            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Acción no válida");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");

    String accion = request.getParameter("accion");

    if ("actualizar".equals(accion)) {
        try {
            int id = Integer.parseInt(request.getParameter("id_ped"));
            String estado = request.getParameter("estado");
            String fecha = request.getParameter("fecha_entrega");
            String observaciones = request.getParameter("observaciones");

            pedidos p = new pedidos();
            p.setId_ped(id);
            p.setEstado(estado);
            p.setFecha_entrega(fecha);
            p.setObservaciones_pedido(observaciones);

            boolean actualizado = p_dao.actualizar(p);
            if (actualizado) {
                response.sendRedirect("pedidosController?accion=listar");
            } else {
                response.sendRedirect("error.jsp");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}

    // Método común si más adelante quieres reutilizar lógica entre GET y POST
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Aquí puedes incluir lógica compartida entre doGet y doPost si lo necesitas
    }

    @Override
    public String getServletInfo() {
        return "Controlador de pedidos";
    }
}

*/