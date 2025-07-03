// Source code is decompiled from a .class file using FernFlower decompiler.
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

   public insumosController() {
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String accion = request.getParameter("accion");
      if (accion != null && !accion.equals("listar")) {
         int id;
         String idStr;
         if (accion.equals("editar")) {
            idStr = request.getParameter("id");
            if (idStr == null || idStr.trim().isEmpty()) {
               response.sendRedirect("insumosController?accion=listar&error=parametro");
               return;
            }

            try {
               id = Integer.parseInt(idStr);
               insumos insumoEditar = this.dao.obtenerPorId(id);
               request.setAttribute("insumoEditar", insumoEditar);
               request.getRequestDispatcher("views/editarInsumo.jsp").forward(request, response);
            } catch (NumberFormatException var8) {
               response.sendRedirect("insumosController?accion=listar&error=id_invalido");
            }
         } else if (accion.equals("eliminar")) {
            idStr = request.getParameter("id");
            if (idStr == null || idStr.trim().isEmpty()) {
               response.sendRedirect("insumosController?accion=listar&error=parametro");
               return;
            }

            try {
               id = Integer.parseInt(idStr);
               this.dao.eliminar(id);
               response.sendRedirect("insumosController?accion=listar&msg=eliminado");
            } catch (NumberFormatException var7) {
               response.sendRedirect("insumosController?accion=listar&error=id_invalido");
            }
         } else {
            response.sendRedirect("insumosController?accion=listar");
         }
      } else {
         List<insumos> lista = this.dao.listar();
         request.setAttribute("listaInsumos", lista);
         request.getRequestDispatcher("views/inventario.jsp").forward(request, response);
      }

   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String accion = request.getParameter("accion");
      if (accion == null) {
         response.sendRedirect("insumosController?accion=listar");
      } else {
         if (accion.equals("agregar")) {
            try {
               String nombre = request.getParameter("nombre");
               double cantidad = Double.parseDouble(request.getParameter("cantidad"));
               String unidad = request.getParameter("unidad_medida");
               double stockMin = Double.parseDouble(request.getParameter("stock_min"));
               insumos nuevo = new insumos();
               nuevo.setNombre(nombre);
               nuevo.setCantidad(cantidad);
               nuevo.setUnidad_medida(unidad);
               nuevo.setStock_min(stockMin);
               this.dao.agregar(nuevo);
               response.sendRedirect("insumosController?accion=listar&msg=ok");
            } catch (NumberFormatException var13) {
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
               this.dao.actualizar(actualizar);
               response.sendRedirect("insumosController?accion=listar&msg=actualizado");
            } catch (NumberFormatException var12) {
               response.sendRedirect("insumosController?accion=listar&error=datos");
            }
         }

      }
   }
}
