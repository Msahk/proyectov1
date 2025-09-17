/*package beans;

import models.receta_insumos;
import models.receta_insumosDao;
import java.io.IOException;
import java.util.List;
import com.google.gson.Gson; // 📦 Para convertir listas a JSON
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "receta_insumosController", urlPatterns = {"/receta_insumosController"})
public class receta_insumosController extends HttpServlet {

    receta_insumosDao dao = new receta_insumosDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if (accion == null) {
            response.sendRedirect("recetas.jsp");
            return;
        }

        switch (accion) {
            case "listar":
                listar(request, response);
                break;
            case "listarJSON": // 📌 Nuevo método para AJAX
                listarJSON(request, response);
                break;
            case "editar":
                mostrarFormularioEdicion(request, response);
                break;
            default:
                response.sendRedirect("recetas.jsp");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if (accion == null) {
            response.sendRedirect("recetas.jsp");
            return;
        }

        switch (accion) {
            case "agregar":
                agregar(request, response);
                break;
            case "editar":
                editar(request, response);
                break;
            case "eliminar":
                eliminar(request, response);
                break;
            default:
                response.sendRedirect("recetas.jsp");
                break;
        }
    }

    // 📌 Listar insumos en JSP
    private void listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idReceta = Integer.parseInt(request.getParameter("id_rec"));
        List<receta_insumos> lista = dao.listarPorReceta(idReceta);
        request.setAttribute("listaInsumos", lista);
        request.setAttribute("idReceta", idReceta);
        request.getRequestDispatcher("views/receta_insumos.jsp").forward(request, response);
    }

    // 📌 Nuevo: Listar insumos en formato JSON (para AJAX)
    private void listarJSON(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        int idReceta = Integer.parseInt(request.getParameter("id_rec"));
        List<receta_insumos> lista = dao.listarPorReceta(idReceta);
        String json = new Gson().toJson(lista);
        response.getWriter().write(json);
    }

    // 📌 Agregar insumo
    private void agregar(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int idReceta = Integer.parseInt(request.getParameter("id_rec"));
        int idInsumo = Integer.parseInt(request.getParameter("id_ins"));
        double cantidad = Double.parseDouble(request.getParameter("cantidad"));
        String unidad = request.getParameter("unidad");

        receta_insumos ri = new receta_insumos();
        ri.setId_rec(idReceta);
        ri.setId_ins(idInsumo);
        ri.setCantidad(cantidad);
        ri.setUnidad(unidad);

        dao.agregar(ri);

        response.sendRedirect("views/receta_insumos.jsp?id_rec=" + idReceta);
    }

    // 📌 Editar insumo
    private void editar(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int idRecIns = Integer.parseInt(request.getParameter("id_ri"));
        int idReceta = Integer.parseInt(request.getParameter("id_rec"));
        int idInsumo = Integer.parseInt(request.getParameter("id_ins"));
        double cantidad = Double.parseDouble(request.getParameter("cantidad"));
        String unidad = request.getParameter("unidad");

        receta_insumos ri = dao.obtenerPorId(idRecIns);
        if (ri != null) {
            ri.setId_rec(idReceta);
            ri.setId_ins(idInsumo);
            ri.setCantidad(cantidad);
            ri.setUnidad(unidad);

            dao.actualizar(ri);
        }

        response.sendRedirect("views/receta_insumos.jsp?id_rec=" + idReceta);
    }

    // 📌 Eliminar insumo
    private void eliminar(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int idRecIns = Integer.parseInt(request.getParameter("id_ri"));

        receta_insumos ri = dao.obtenerPorId(idRecIns);
        if (ri != null) {
            int idReceta = ri.getId_rec();
            dao.eliminarPorId(idRecIns);
            response.sendRedirect("receta_insumosController?accion=listar&id_rec=" + idReceta);
        } else {
            response.sendRedirect("recetas.jsp");
        }
    }

    // 📌 Mostrar formulario de edición
    private void mostrarFormularioEdicion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idRecIns = Integer.parseInt(request.getParameter("id_ri"));
        receta_insumos ri = dao.obtenerPorId(idRecIns);
        if (ri != null) {
            request.setAttribute("recetaInsumo", ri);
            request.getRequestDispatcher("views/editar_receta_insumo.jsp").forward(request, response);
        } else {
            response.sendRedirect("receta_insumosController?accion=listar&id_rec=" + ri.getId_rec());
        }
    }
}
*/