
/*package beans;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.usuarios;
import models.usuariosDao;
import models.ventas;
import models.ventasDao;


public class ventasController extends HttpServlet {

     ventas ven = new ventas();
     ventasDao v_dao = new ventasDao();
     usuariosDao uDao = new usuariosDao();
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
       
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        
        
        String accion = request.getParameter("accion");
        
        switch (accion != null ? accion : "") {
            case "listar":
                try {
                    List<ventas> listaVentas = v_dao.listar();
                    request.setAttribute("ventas",listaVentas);
                    
                    List<usuarios> vendedores = uDao.listarPorRol("EV");
                    request.setAttribute("vendedores",vendedores);
                   
                    
                    request.getRequestDispatcher("views/ventas.jsp").forward(request, response);
                    return;
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("error", "No se pudo listar ventas");
                }
                break;
            default:
                throw new AssertionError();
        }
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String accion = request.getParameter("accion");
        
        switch (accion != null ? accion : "") {
            case "agregar":
                ventas v = new ventas();
                break;
            default:
                throw new AssertionError();
        }
    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
*/