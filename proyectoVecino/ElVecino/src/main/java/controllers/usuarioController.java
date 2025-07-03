package controllers;

import models.usuarioDao;
import models.usuario;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class usuarioController extends HttpServlet {

    usuarioDao u_dao = new usuarioDao();
    usuario usu = new usuario();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.sendRedirect("views/formularios/login.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        switch (accion != null ? accion : "") {
            case "listar":
                try {
                    List<usuario> listaUsuarios = u_dao.listar();
                    request.setAttribute("usuarios", listaUsuarios);
                    request.getRequestDispatcher("views/dashboard/Usuario.jsp").forward(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("error", "No se pudo listar usuarios.");
                    request.getRequestDispatcher("views/dashboard/error.jsp").forward(request, response);
                }
                break;

            case "editar":
                String docEdit = request.getParameter("docUsuario");
                usuario userEdit = u_dao.obtenerPorDocumento(docEdit);
                request.setAttribute("usuario", userEdit);
                request.getRequestDispatcher("views/dashboard/editarUsuario.jsp").forward(request, response);
                break;

            case "editarVista":
                String docEditVista = request.getParameter("docUsuario");
                usuario userEditVista = u_dao.obtenerPorDocumento(docEditVista);
                request.setAttribute("usuario", userEditVista);
                request.getRequestDispatcher("views/dashboard/editarUsuario.jsp").forward(request, response);
                break;

            case "registrarVista":
                request.getRequestDispatcher("views/dashboard/registroUsuario.jsp").forward(request, response);
                break;

            case "eliminar":
                String docEliminar = request.getParameter("docUsuario");
                u_dao.eliminar(docEliminar);
                response.sendRedirect("usuarioController?accion=listar");
                break;

            default:
                processRequest(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        switch (accion != null ? accion : "") {
            case "Ingresar":
                String user = request.getParameter("email");
                String pass = request.getParameter("password");

                try {
                    usu = u_dao.Validar(user, pass);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(usuarioController.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (usu != null && usu.getEmail() != null && usu.getPassword() != null) {
                    String rol = usu.getRol();
                    request.getSession().setAttribute("usuario", usu);

                    if ("E".equalsIgnoreCase(rol)) {
                        response.sendRedirect("views/dashboard/Index.jsp");
                    } else if ("A".equalsIgnoreCase(rol)) {
                        response.sendRedirect("views/dashboard/Admin.jsp");
                    } else {
                        request.setAttribute("fail", "Rol no válido");
                        request.getRequestDispatcher("views/formularios/login.jsp").forward(request, response);
                    }
                } else {
                    request.setAttribute("fail", "Datos no existen en BD");
                    request.getRequestDispatcher("views/formularios/login.jsp").forward(request, response);
                }
                break;

            case "agregar":
                usuario nuevo = new usuario();
                nuevo.setDocUsuario(request.getParameter("docUsuario"));
                nuevo.setNombreUsuario(request.getParameter("nombreUsuario"));
                nuevo.setRol(request.getParameter("rol"));
                nuevo.setPassword(request.getParameter("password"));
                nuevo.setEmail(request.getParameter("email"));
                u_dao.agregar(nuevo);
                response.sendRedirect("usuarioController?accion=listar");
                break;

            case "actualizar":
                usuario actualizado = new usuario();
                actualizado.setDocUsuario(request.getParameter("docUsuario"));
                actualizado.setNombreUsuario(request.getParameter("nombreUsuario"));
                actualizado.setRol(request.getParameter("rol"));
                actualizado.setPassword(request.getParameter("password"));
                actualizado.setEmail(request.getParameter("email"));
                u_dao.actualizar(actualizado);
                response.sendRedirect("usuarioController?accion=listar");
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
