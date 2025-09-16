
/*


/*
  * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
  * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;  // Paquete donde está ubicado el servlet

import com.google.gson.Gson;  // Librería para convertir objetos Java a JSON
import java.io.IOException;  // Excepciones para entrada/salida
import java.io.PrintWriter;  // Para imprimir la respuesta al cliente
import java.util.List;  // Para trabajar con listas
import java.util.logging.Level;  // Para manejo de logs (no usado aquí)
import java.util.logging.Logger;  // Para manejo de logs (no usado aquí)
import javax.servlet.ServletException;  // Para manejo de excepciones del servlet
import javax.servlet.http.HttpServlet;  // Clase base para servlets HTTP
import javax.servlet.http.HttpServletRequest;  // Para manejar la solicitud
import javax.servlet.http.HttpServletResponse;  // Para manejar la respuesta
import models.detalle_venta;  // Modelo detalle_venta
import models.detalle_ventaDao;  // DAO de detalle_venta
import models.produccionDao;  // DAO de producción (relacionado)
import models.usuarios;  // Modelo usuarios (no usado realmente)

/**
 *
 * @author USER
 */
public class detalle_ventaController extends HttpServlet {  // Servlet que maneja operaciones sobre detalle_venta

    detalle_ventaDao dDao = new detalle_ventaDao();  // Instancia DAO para detalle_venta
    detalle_venta dv = new detalle_venta();  // Instancia modelo detalle_venta reutilizable
    produccionDao pDao = new produccionDao();  // Instancia DAO de producción (puede usarse para relacionar)

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");  // Establece tipo y codificación de respuesta
        // Método general no implementado, se usa para casos por defecto
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");  // Define codificación para leer datos enviados
        response.setCharacterEncoding("UTF-8");  // Define codificación para enviar respuesta
        response.setContentType("text/html; charset=UTF-8");  // Tipo y charset de la respuesta
        String accion = request.getParameter("accion");  // Obtener acción solicitada

        switch (accion != null ? accion : "") {  // Evalúa la acción o vacío si es null
            case "listar":  // Caso para listar todos los detalles de venta
                try {
                    List<detalle_venta> listaDetalle = dDao.listar();  // Obtener lista desde DAO
                    request.setAttribute("detalle_venta", listaDetalle);  // Guardar lista en atributo para JSP
                    request.getRequestDispatcher("views/detalle_venta.jsp").forward(request, response);  // Enviar a JSP
                } catch (Exception e) {  // Capturar errores
                    e.printStackTrace();  // Imprimir error en consola
                    request.setAttribute("error", "No se pudo listar detalle_venta.");  // Mensaje de error
                    request.getRequestDispatcher("views/dashboard/error.jsp").forward(request, response);  // Enviar a página error
                }
                break;  // Termina caso listar

            case "agregar":  // Caso para agregar un nuevo detalle_venta
                detalle_venta d = new detalle_venta();  // Crear nuevo objeto detalle_venta

                d.setId_ven(Integer.parseInt(request.getParameter("id_ven")));  // Establecer id_ven desde parámetro
                d.setId_proc(Integer.parseInt(request.getParameter("id_proc")));  // Establecer id_proc
                d.setCantidad(Integer.parseInt(request.getParameter("cantidad")));  // Establecer cantidad
                dDao.agregar(d);  // Llamar DAO para agregar

                request.getRequestDispatcher("detalle_ventaController?accion=listar").forward(request, response);  // Redirigir a listado
                //response.sendRedirect(request.getContextPath() + "/usuariosController?accion=listar");
                break;  // Termina caso agregar

            case "editar":  // Caso para editar: retorna JSON del detalle_venta solicitado
                String idStr = request.getParameter("id");  // Obtener id a editar
                if (idStr != null && !idStr.isEmpty()) {  // Validar que id no sea nulo o vacío
                    int id = Integer.parseInt(idStr);  // Convertir id a entero
                    usuarios usu = null;  // No usado realmente pero declarado
                    dv = dDao.obtenerPorId(id);  // Obtener detalle_venta por id

                    response.setContentType("application/json; charset=UTF-8");  // Respuesta JSON
                    response.setCharacterEncoding("UTF-8");  // Codificación UTF-8

                    PrintWriter out = response.getWriter();  // Obtener escritor de respuesta
                    Gson gson = new Gson();  // Crear objeto Gson para JSON
                    out.print(gson.toJson(dv));  // Convertir detalle_venta a JSON y enviar
                    out.flush();  // Vaciar buffer de salida
                    return;  // Termina método para no continuar
                }
                break;  // Termina caso editar

            case "eliminar":  // Caso para eliminar un detalle_venta
                int idd = Integer.parseInt(request.getParameter("id"));  // Obtener id a eliminar
                dDao.eliminar(idd);  // Llamar DAO para eliminar
                response.sendRedirect("detalle_ventaController?accion=listar");  // Redirigir a lista
                break;  // Termina caso eliminar

            default:  // Caso por defecto
                processRequest(request, response);  // Llama método por defecto
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");  // Codificación para leer datos
        response.setCharacterEncoding("UTF-8");  // Codificación para responder
        response.setContentType("text/html; charset=UTF-8");  // Tipo de respuesta

        String accion = request.getParameter("accion");  // Obtener acción del POST

        switch (accion != null ? accion : "") {  // Evaluar acción o vacío
            case "agregar":  // Caso agregar nuevo detalle_venta
                detalle_venta u = new detalle_venta();  // Nuevo objeto detalle_venta
                u.setId_ven(Integer.parseInt(request.getParameter("id_ven")));  // Establecer id_ven
                u.setId_proc(Integer.parseInt(request.getParameter("id_proc")));  // Establecer id_proc
                u.setCantidad(Integer.parseInt(request.getParameter("cantidad")));  // Establecer cantidad

                dDao.agregar(u);  // Agregar en BD
                response.sendRedirect(request.getContextPath() + "/detalle_ventaController?accion=listar");  // Redirigir a lista
                break;  // Termina caso agregar

            case "actualizar":  // Caso actualizar detalle_venta existente
                detalle_venta d = new detalle_venta();  // Nuevo objeto detalle_venta
                d.setId_ven(Integer.parseInt(request.getParameter("id_ven")));  // Establecer id_ven
                d.setId_proc(Integer.parseInt(request.getParameter("id_proc")));  // Establecer id_proc
                d.setCantidad(Integer.parseInt(request.getParameter("cantidad")));  // Establecer cantidad
                d.setId_detalle(Integer.parseInt(request.getParameter("id_detalle")));  // Establecer id_detalle

                dDao.actualizar(d);  // Actualizar en BD

                response.sendRedirect(request.getContextPath() + "/detalle_ventaController?accion=listar");  // Redirigir a lista
                break;  // Termina caso actualizar

            default:  // Si la acción no coincide
                response.sendRedirect("views/index.jsp");  // Redirigir a página principal
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";  // Descripción corta del servlet
    }// </editor-fold>

}