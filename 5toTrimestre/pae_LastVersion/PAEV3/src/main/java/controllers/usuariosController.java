/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.SOAPFault;
import com.google.gson.Gson;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;

import models.usuarios;
import models.usuariosDao;

/**
 *
 * @author USER
 */
public class usuariosController extends HttpServlet {

    usuariosDao u_dao = new usuariosDao();
    usuarios usu = new usuarios();

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
        // Redirección por defecto
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
                    List<usuarios> listaUsuarios = u_dao.listar();
                    request.setAttribute("usuarios", listaUsuarios);
                    request.getRequestDispatcher("views/RegistroUsuarios.jsp").forward(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("error", "No se pudo listar usuarios.");
                    request.getRequestDispatcher("views/dashboard/error.jsp").forward(request, response);
                }
                break;
            case "agregar":
                usuarios u = new usuarios();
                u.setDocumento(Integer.parseInt(request.getParameter("documento")));
                u.setNombres(request.getParameter("nombres"));
                u.setApellidos(request.getParameter("apellidos"));
                u.setTelefono(Long.parseLong(request.getParameter("telefono")));
                u.setDireccion(request.getParameter("direccion"));
                u.setCorreo(request.getParameter("correo"));
                u.setRol(request.getParameter("rol"));
                u.setPassword(request.getParameter("password"));
                u_dao.agregar(u);
                
                request.getRequestDispatcher("usuariosController?accion=listar").forward(request, response);
                //response.sendRedirect(request.getContextPath() + "/usuariosController?accion=listar");
                break;
            case "editar":
                String idStr = request.getParameter("id");
                if (idStr != null && !idStr.isEmpty()) {
                    int id = Integer.parseInt(idStr);
                    usuarios usu = null;
                    try {
                        usu = u_dao.obtenerPorId(id);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(usuariosController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    response.setContentType("application/json; charset=UTF-8");
                    response.setCharacterEncoding("UTF-8");

                    PrintWriter out = response.getWriter();
                    Gson gson = new Gson();
                    out.print(gson.toJson(usu));
                    out.flush();
                    return;
                }
                break;
            case "cambiarEstado":
                String idEstadoStr = request.getParameter("id");
                String nuevoEstado = request.getParameter("nuevoEstado");

                if (idEstadoStr != null && nuevoEstado != null && !idEstadoStr.isEmpty()) {
                    int id = Integer.parseInt(idEstadoStr);
                    boolean actualizado = u_dao.cambiarEstado(id, nuevoEstado);

                    if (actualizado) {
                        response.sendRedirect(request.getContextPath() + "/usuariosController?accion=listar");
                    }
                }
                break;

            case "eliminar":
                int idd = Integer.parseInt(request.getParameter("id"));
                u_dao.eliminarUsuario(idd);
                response.sendRedirect("usuariosController?accion=listar");
                break;
            /*
            case "editarVista":
                String docEditVista = request.getParameter("docUsuario");
                usuario userEditVista = u_dao.obtenerPorDocumento(docEditVista);
                request.setAttribute("usuario", userEditVista);
                request.getRequestDispatcher("views/dashboard/editarUsuario.jsp").forward(request, response);
                break;

            case "registrarVista":
                request.getRequestDispatcher("views/dashboard/registroUsuario.jsp").forward(request, response);
                break;

             */
            default:
                processRequest(request, response);
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
            case "Ingresar":
                String correo = request.getParameter("correo");
                String pass = request.getParameter("password");

                try {
                    usu = u_dao.Validar(correo, pass); // Validación de usuario

                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(usuariosController.class
                            .getName()).log(Level.SEVERE, null, ex);
                }

                if (usu != null && usu.getCorreo() != null && usu.getPassword() != null) {
                    String rol = usu.getRol();
                    String estado = usu.getEstado();

                    if ("I".equalsIgnoreCase(estado)) {
                        System.out.println("NO TIENE ROL ACTIOV PAPA");
                        request.setAttribute("loginError", "Estado Inactivo");
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    } else if ("A".equalsIgnoreCase(estado)) {
                        
                            request.getSession().setAttribute("usuarios", usu);
                            request.getRequestDispatcher("views/dashboard.jsp").forward(request, response);
                            
                            return;
                          
                    }
                } else {
                    System.out.println("NO SE PUDO PAPI");
                    request.setAttribute("loginError", "Usuario o contraseña incorrectos");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
                break;

            case "agregar":
                usuarios u = new usuarios();
                u.setDocumento(Integer.parseInt(request.getParameter("documento")));
                u.setTelefono(Long.parseLong(request.getParameter("telefono")));
                u.setNombres(request.getParameter("nombres"));
                u.setApellidos(request.getParameter("apellidos"));
                u.setDireccion(request.getParameter("direccion"));
                u.setCorreo(request.getParameter("correo"));
                u.setRol(request.getParameter("rol"));
                u.setPassword(request.getParameter("password"));

                // Validar si el correo o documento ya existen
                if (u_dao.existeCorreoODocumento(u.getCorreo(), u.getDocumento())) {
                    request.setAttribute("errorRegistro", "El correo o documento ya están registrados.");
                    request.setAttribute("usuarioTemp", u); // Para mantener los datos del formulario

                    // Cargar lista de usuarios para que no desaparezca
                    List<usuarios> listaUsuarios = u_dao.listar();
                    request.setAttribute("usuarios", listaUsuarios);

                    request.getRequestDispatcher("views/RegistroUsuarios.jsp").forward(request, response);
                    return;
                }

                // Si no existen, continuar con el registro
                u_dao.agregar(u);
                response.sendRedirect(request.getContextPath() + "/usuariosController?accion=listar");
                break;

            case "actualizar":
                usuarios a = new usuarios();
                a.setNombres(request.getParameter("nombres"));
                a.setApellidos(request.getParameter("apellidos"));
                a.setRol(request.getParameter("rol"));
                a.setPassword(request.getParameter("password"));
                a.setCorreo(request.getParameter("correo"));
                a.setDireccion(request.getParameter("direccion"));
                a.setTelefono(Long.parseLong(request.getParameter("telefono")));
                a.setDocumento(Integer.parseInt(request.getParameter("documento")));
                a.setId_usu(Integer.parseInt(request.getParameter("id_usu")));

                u_dao.actualizar(a);

                // Si el usuario que se actualiza es el mismo que está en sesión, actualiza la sesión
                usuarios usuSesion = (usuarios) request.getSession().getAttribute("usuarios");
                if (usuSesion != null && usuSesion.getId_usu() == a.getId_usu()) {
                    try {
                        usuarios actualizado = u_dao.obtenerPorId(a.getId_usu());
                        
                        request.getSession().setAttribute("usuarios", actualizado);
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }

                // Luego redirige de forma segura
                response.sendRedirect(request.getContextPath() + "/usuariosController?accion=listar");
                break;
                
            case "olvidar":
                String correin = request.getParameter("correo");
                int documento = (Integer.parseInt(request.getParameter("documento")));
                
                try {
                    usu = u_dao.olvidar(correin, documento);
                } catch (Exception e) {
                    System.out.println("No funciona mi loco");
                }
                
                if (usu != null) {
                    String passin = request.getParameter("password");
                    u_dao.actualizarContra(usu.getId_usu(), passin);
                    
                    request.setAttribute("Exitosamente", "la contraseña se actualizo correctamente");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                    
                } else {
                    request.setAttribute("loginError", "El correo o documento no se encuentran en la base de datos");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
                
                break;
            default:
                response.sendRedirect("views/formularios/login.jsp");
        }
    }

    @Override
    public String getServletInfo() {
        return "Controlador de usuarios con CRUD completo";
    }
}
