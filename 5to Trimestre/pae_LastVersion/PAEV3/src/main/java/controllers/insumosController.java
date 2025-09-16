package controllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.insumos;
import models.insumosDao;

@WebServlet(
   name = "insumosController",
   urlPatterns = {"/insumosController"}
)
public class insumosController extends HttpServlet {
   insumosDao dao = new insumosDao();

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String accion = request.getParameter("accion");

      if (accion != null && !accion.equals("listar")) {
         String idStr = request.getParameter("id");

         if (accion.equals("editar")) {
            if (idStr == null || idStr.trim().isEmpty()) {
               response.sendRedirect("insumosController?accion=listar&error=parametro");
               return;
            }

            try {
               int id = Integer.parseInt(idStr);
               insumos insumoEditar = dao.obtenerPorId(id);
               request.setAttribute("insumoEditar", insumoEditar);
               request.getRequestDispatcher("views/editarInsumo.jsp").forward(request, response);
            } catch (NumberFormatException e) {
               response.sendRedirect("insumosController?accion=listar&error=id_invalido");
            }

         } else if (accion.equals("eliminar")) {
            if (idStr == null || idStr.trim().isEmpty()) {
               response.sendRedirect("insumosController?accion=listar&error=parametro");
               return;
            }

            try {
               int id = Integer.parseInt(idStr);
               dao.eliminar(id);
               response.sendRedirect("insumosController?accion=listar&msg=eliminado");
            } catch (NumberFormatException e) {
               response.sendRedirect("insumosController?accion=listar&error=id_invalido");
            }

         } else {
            response.sendRedirect("insumosController?accion=listar");
         }

      } else {
         // Acción listar
         List<insumos> lista = dao.listar();
         request.setAttribute("listaInsumos", lista);
         request.getRequestDispatcher("views/inventario.jsp").forward(request, response);
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String accion = request.getParameter("accion");

      if (accion == null) {
         response.sendRedirect("insumosController?accion=listar");
         return;
      }

      if (accion.equals("agregar")) {
         try {
            // Obtener datos del formulario
            String nombre = request.getParameter("nombre");
            double cantidad = Double.parseDouble(request.getParameter("cantidad"));
            String unidad = request.getParameter("unidad_medida");
            double stockMin = Double.parseDouble(request.getParameter("stock_min"));

            // Crear objeto
            insumos nuevo = new insumos();
            nuevo.setNombre(nombre);
            nuevo.setCantidad(cantidad);
            nuevo.setUnidad_medida(unidad);
            nuevo.setStock_min(stockMin);

            // Insertar en insumos e inv_entradas
            int nuevoId = dao.agregar(nuevo);

            if (nuevoId > 0) {
               response.sendRedirect("insumosController?accion=listar&msg=ok");
            } else {
               response.sendRedirect("insumosController?accion=listar&error=insert");
            }

         } catch (NumberFormatException e) {
            response.sendRedirect("insumosController?accion=listar&error=datos");
         }

      } else if (accion.equals("actualizar")) {
         try {
            int id = Integer.parseInt(request.getParameter("id_ins"));
            String nombre = request.getParameter("nombre");
            double cantidad = Double.parseDouble(request.getParameter("cantidad"));
            String unidad = request.getParameter("unidad_medida");
            double stockMin = Double.parseDouble(request.getParameter("stock_min"));

            insumos actualizar = new insumos();
            actualizar.setId_ins(id);
            actualizar.setNombre(nombre);
            actualizar.setCantidad(cantidad);
            actualizar.setUnidad_medida(unidad);
            actualizar.setStock_min(stockMin);

            dao.actualizar(actualizar);
            response.sendRedirect("insumosController?accion=listar&msg=actualizado");

         } catch (NumberFormatException e) {
            response.sendRedirect("insumosController?accion=listar&error=datos");
         }
      }
   }
}
