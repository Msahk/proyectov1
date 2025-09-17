/*package beans;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import models.produccion;
import models.produccionDao;
import models.produccion_receta;
import models.recetas;
import models.recetasDao;

@WebServlet("/produccionController")
public class produccionController extends HttpServlet {

    produccionDao pdao = new produccionDao();
    recetasDao rdao = new recetasDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = "listar";
        }

        switch (accion) {
    case "listar":
        // Cargar siempre las producciones y recetas antes de ir a produccion.jsp
        List<produccion> lista = pdao.listar();
        List<recetas> recetasList = rdao.listar();

        System.out.println("Recetas encontradas: " + recetasList);

        request.setAttribute("producciones", lista);
        request.setAttribute("recetas", recetasList);
        request.getRequestDispatcher("views/produccion.jsp").forward(request, response);
        break;

    case "nuevo":
        // Este ya carga recetas también
        request.setAttribute("recetas", rdao.listar());
        request.getRequestDispatcher("views/produccion_form.jsp").forward(request, response);
        break;

    case "editar":
        int idEditar = Integer.parseInt(request.getParameter("id"));
        produccion prodEditar = pdao.buscarPorId(idEditar);
        request.setAttribute("produccion", prodEditar);

        List<produccion_receta> listaRecetas = pdao.listarRecetasPorProduccion(idEditar);
        request.setAttribute("recetasProduccion", listaRecetas);
        request.setAttribute("recetas", rdao.listar()); // También cargamos recetas aquí
        request.getRequestDispatcher("views/produccion_form.jsp").forward(request, response);
        break;

    case "eliminar":
        int idEliminar = Integer.parseInt(request.getParameter("id"));
        pdao.eliminarProduccion(idEliminar);
        // En lugar de ir directo al JSP, volvemos a listar
        response.sendRedirect("produccionController?accion=listar");
        break;

    case "finalizar":
        int idFinalizar = Integer.parseInt(request.getParameter("id"));
        boolean exito = pdao.finalizarProduccion(idFinalizar);

        if (exito) {
            request.getSession().setAttribute("mensaje", "Producción finalizada y stock actualizado correctamente.");
        } else {
            request.getSession().setAttribute("error", "Error al finalizar producción. Verifique el stock.");
        }

        // También volvemos a listar
        response.sendRedirect("produccionController?accion=listar");
        break;

    default:
        response.sendRedirect("produccionController?accion=listar");
        break;
}


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if ("registrar".equals(accion)) {
            registrarProduccion(request, response);
        } else if ("finalizar".equals(accion)) {
            finalizarProduccion(request, response);
        } else if ("actualizar".equals(accion)) {
    int idProd = Integer.parseInt(request.getParameter("id_proc"));
    String nuevoEstado = request.getParameter("estado");

    produccion prod = new produccion();
    prod.setId_proc(idProd);
    prod.setEstado(nuevoEstado);

    // Actualizar estado
    pdao.actualizarEstado(prod);

    // Actualizar recetas si vienen
    String[] recetasIds = request.getParameterValues("recetas[]");
    String[] cantidadesStr = request.getParameterValues("cantidades[]");

    if (recetasIds != null && cantidadesStr != null && recetasIds.length == cantidadesStr.length) {
        List<produccion_receta> listaRecetas = new ArrayList<>();
        for (int i = 0; i < recetasIds.length; i++) {
            int idRec = Integer.parseInt(recetasIds[i]);
            int cantidad = Integer.parseInt(cantidadesStr[i]);
            produccion_receta pr = new produccion_receta();
            pr.setId_rec(idRec);
            pr.setCantidad(cantidad);
            listaRecetas.add(pr);
        }
        // Método en pdao que actualice las recetas de la producción
        pdao.actualizarRecetasProduccion(idProd, listaRecetas);
    }

    response.sendRedirect("produccionController?accion=listar");
} else {
            response.sendRedirect("produccionController?accion=listar");
        }
    }

    // 🔹 Registrar producción
    private void registrarProduccion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String fechaStr = request.getParameter("fecha");
            Date fechaProduccion = Date.valueOf(fechaStr);
            String estado = "PENDIENTE";

            // Usuario desde sesión
            HttpSession session = request.getSession();
            int usuario = 1; // temporal por si no hay sesión
            if (session.getAttribute("usuarioId") != null) {
                usuario = (Integer) session.getAttribute("usuarioId");
            }

            // Recetas seleccionadas
            String[] recetasIds = request.getParameterValues("recetas[]");
            String[] cantidadesStr = request.getParameterValues("cantidades[]");

            if (recetasIds == null || recetasIds.length == 0) {
                request.setAttribute("error", "Debes seleccionar al menos una receta.");
                request.setAttribute("recetas", rdao.listar());
                request.getRequestDispatcher("views/produccion_form.jsp").forward(request, response);
                return;
            }

            List<produccion_receta> listaRecetas = new ArrayList<>();
            for (int i = 0; i < recetasIds.length; i++) {
                int idRec = Integer.parseInt(recetasIds[i]);
                int cantidad = Integer.parseInt(cantidadesStr[i]);

                produccion_receta pr = new produccion_receta();
                pr.setId_rec(idRec);
                pr.setCantidad(cantidad);
                listaRecetas.add(pr);
            }

            // Crear producción
            produccion prod = new produccion();
            prod.setFecha_produccion(fechaProduccion);
            prod.setEstado(estado);
            prod.setUsuario(usuario);
            prod.setRecetas(listaRecetas);

            boolean exito = pdao.guardarProduccion(prod);

            if (exito) {
                response.sendRedirect("produccionController?accion=listar");
            } else {
                request.setAttribute("error", "No se pudo guardar la producción.");
                request.setAttribute("recetas", rdao.listar());
                request.getRequestDispatcher("views/produccion_form.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error en los datos enviados: " + e.getMessage());
            request.setAttribute("recetas", rdao.listar());
            request.getRequestDispatcher("views/produccion_form.jsp").forward(request, response);
        }
    }

    // 🔹 Finalizar producción
    private void finalizarProduccion(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String idStr = request.getParameter("id");
        if (idStr != null) {
            int idProc = Integer.parseInt(idStr);
            produccion prod = new produccion();
            prod.setId_proc(idProc);
            prod.setEstado("FINALIZADA");

            pdao.actualizarEstado(prod);
        }

        response.sendRedirect("produccionController?accion=listar");
    }
}
*/