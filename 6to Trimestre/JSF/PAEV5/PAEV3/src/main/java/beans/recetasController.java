/*package beans;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import models.recetas;
import models.recetasDao;

@WebServlet("/recetasController")
public class recetasController extends HttpServlet {

    recetasDao dao = new recetasDao();
    recetas receta = new recetas();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if (accion == null) accion = "listar";

        switch (accion) {
            case "listar":
                List<recetas> lista = dao.listar();
                request.setAttribute("recetas", lista);
                request.getRequestDispatcher("views/recetas.jsp").forward(request, response);
                break;

            case "editar":
                int idEditar = Integer.parseInt(request.getParameter("id_rec"));
                receta = dao.buscarPorId(idEditar);
                request.setAttribute("receta", receta);
                request.getRequestDispatcher("views/recetas.jsp").forward(request, response);
                break;

            case "eliminar":
                int idEliminar = Integer.parseInt(request.getParameter("id_rec"));
                dao.eliminar(idEliminar);
                response.sendRedirect("recetasController?accion=listar");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = request.getParameter("id_rec") != null && !request.getParameter("id_rec").isEmpty()
                ? Integer.parseInt(request.getParameter("id_rec")) : 0;

        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");

        receta.setNombre(nombre);
        receta.setDescripcion(descripcion);

        if (id > 0) {
            receta.setId_rec(id);
            dao.actualizar(receta);
        } else {
            dao.agregar(receta);
        }

        response.sendRedirect("recetasController?accion=listar");
    }
}
*/