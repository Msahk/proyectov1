/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.detalle_venta;
import models.detalle_ventaDao;
import models.produccionDao;
import models.usuarios;

/**
 *
 * @author USER
 */
public class detalle_ventaController extends HttpServlet {
    detalle_ventaDao dDao = new detalle_ventaDao();
    detalle_venta dv = new detalle_venta();
    produccionDao pDao = new produccionDao();

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
        response.setContentType("text/html;charset=UTF-8");
        
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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String accion = request.getParameter("accion");

        switch (accion != null ? accion : "") {
            case "listar":
                try {
                    List<detalle_venta> listaDetalle = dDao.listar();
                    request.setAttribute("detalle_venta", listaDetalle);
                    
                    
                    
                    request.getRequestDispatcher("views/detalle_venta.jsp").forward(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("error", "No se pudo listar detalle_venta.");
                    request.getRequestDispatcher("views/dashboard/error.jsp").forward(request, response);
                }
                break;
            case "agregar":
                detalle_venta d = new detalle_venta();
                
                d.setId_ven(Integer.parseInt(request.getParameter("id_ven")));
                d.setId_proc(Integer.parseInt(request.getParameter("id_proc")));
                d.setCantidad(Integer.parseInt(request.getParameter("cantidad")));
                dDao.agregar(d);
                
                request.getRequestDispatcher("detalle_ventaController?accion=listar").forward(request, response);
                //response.sendRedirect(request.getContextPath() + "/usuariosController?accion=listar");
                break;
            case "editar":
                String idStr = request.getParameter("id");
                if (idStr != null && !idStr.isEmpty()) {
                    int id = Integer.parseInt(idStr);
                    usuarios usu = null;
                    dv = dDao.obtenerPorId(id);

                    response.setContentType("application/json; charset=UTF-8");
                    response.setCharacterEncoding("UTF-8");

                    PrintWriter out = response.getWriter();
                    Gson gson = new Gson();
                    out.print(gson.toJson(dv));
                    out.flush();
                    return;
                }
                break;
            case "eliminar":
                int idd = Integer.parseInt(request.getParameter("id"));
                dDao.eliminar(idd);
                response.sendRedirect("detalle_ventaController?accion=listar");
                break;
            default:
                processRequest(request, response);
            
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
         request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String accion = request.getParameter("accion");
        
        switch (accion != null ? accion : "") {
            case "agregar":
                detalle_venta u = new detalle_venta();
                u.setId_ven(Integer.parseInt(request.getParameter("id_ven")));
                u.setId_proc(Integer.parseInt(request.getParameter("id_proc")));
                u.setCantidad(Integer.parseInt(request.getParameter("cantidad")));

                // Si no existen, continuar con el registro
                dDao.agregar(u);
                response.sendRedirect(request.getContextPath() + "/detalle_ventaController?accion=listar");
                break;

            case "actualizar":
                detalle_venta d = new detalle_venta();
                d.setId_ven(Integer.parseInt(request.getParameter("id_ven")));
                d.setId_proc(Integer.parseInt(request.getParameter("id_proc")));
                d.setCantidad(Integer.parseInt(request.getParameter("cantidad")));
                d.setId_detalle(Integer.parseInt(request.getParameter("id_detalle")));

                dDao.actualizar(d);

                

                // Luego redirige de forma segura
                response.sendRedirect(request.getContextPath() + "/detalle_ventaController?accion=listar");
                break;
            default:
                response.sendRedirect("views/index.jsp");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
