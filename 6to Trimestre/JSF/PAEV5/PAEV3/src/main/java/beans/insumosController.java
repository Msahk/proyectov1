/*package beans;

import models.insumos;
import models.insumosDao;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class insumosController extends HttpServlet {

    private insumosDao dao = new insumosDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = "listar";
        }

        switch (accion) {
            case "listar":
                List<insumos> lista = dao.listar();
                request.setAttribute("listaInsumos", lista);
                request.getRequestDispatcher("views/inventario.jsp").forward(request, response);
                break;

            case "editar":
                int id = Integer.parseInt(request.getParameter("id"));
                insumos ins = dao.buscarPorId(id);
                request.setAttribute("insumo", ins);
                request.getRequestDispatcher("views/inventario.jsp").forward(request, response);
                break;

            case "eliminar":
                int idEliminar = Integer.parseInt(request.getParameter("id"));
                dao.eliminar(idEliminar);
                response.sendRedirect("insumosController?accion=listar");
                break;

            case "json":
                List<insumos> insumosJson = dao.listar();
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                StringBuilder json = new StringBuilder("[");
                for (int i = 0; i < insumosJson.size(); i++) {
                    insumos insumo = insumosJson.get(i);
                    json.append("{")
                            .append("\"id_ins\":").append(insumo.getId_ins()).append(",")
                            .append("\"nombre\":\"").append(insumo.getNombre()).append("\"")
                            .append("}");
                    if (i < insumosJson.size() - 1) {
                        json.append(",");
                    }
                }
                json.append("]");
                response.getWriter().write(json.toString());
                break;

            default:
                response.sendRedirect("error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        switch (accion) {
            case "crear":
                insumos nuevo = new insumos(
                        0,
                        request.getParameter("nombre"),
                        Double.parseDouble(request.getParameter("cantidad")),
                        request.getParameter("unidad_medida"),
                        Double.parseDouble(request.getParameter("stock_min"))
                );
                dao.agregar(nuevo);
                break;

            case "actualizar":
                insumos actualizado = new insumos(
                        Integer.parseInt(request.getParameter("id_ins")),
                        request.getParameter("nombre"),
                        Double.parseDouble(request.getParameter("cantidad")),
                        request.getParameter("unidad_medida"),
                        Double.parseDouble(request.getParameter("stock_min"))
                );
                dao.actualizar(actualizado);
                break;
        }

        response.sendRedirect("insumosController?accion=listar");
    }
}
*/