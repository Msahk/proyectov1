package controllers;

import models.cliente;
import models.clienteDao;
import models.usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "clienteController", urlPatterns = {"/clienteController"})
public class clienteController extends HttpServlet {

    clienteDao dao = new clienteDao();
    cliente c = new cliente();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        switch (accion != null ? accion : "") {
            case "listar":
                List<cliente> lista = dao.listar();
                request.setAttribute("clientes", lista);
                request.getRequestDispatcher("views/dashboard/Clientes.jsp").forward(request, response);
                break;

            case "editarVista":
                int idEditar = Integer.parseInt(request.getParameter("idCliente"));
                cliente cliEditar = dao.obtenerPorId(idEditar);
                request.setAttribute("cliente", cliEditar);
                request.getRequestDispatcher("views/dashboard/Clientes.jsp").forward(request, response); // usa modal
                break;

            case "eliminar":
                int idEliminar = Integer.parseInt(request.getParameter("idCliente"));
                dao.eliminar(idEliminar);
                response.sendRedirect("clienteController?accion=listar");
                break;

            default:
                response.sendRedirect("clienteController?accion=listar");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        // Obtener usuario actual de sesión
        usuario usu = (usuario) request.getSession().getAttribute("usuario");
        if (usu == null) {
            response.sendRedirect("views/formularios/login.jsp");
            return;
        }

        switch (accion != null ? accion : "") {
            case "agregar":
                cliente nuevo = new cliente();
                nuevo.setNombre(request.getParameter("nombre"));
                nuevo.setApellidos(request.getParameter("apellidos"));
                nuevo.setTelefono(request.getParameter("telefono"));
                nuevo.setDireccion(request.getParameter("direccion"));
                nuevo.setCategoriaCrediticia(request.getParameter("categoriaCrediticia"));
                nuevo.setFechaRegistro(java.time.LocalDateTime.now()); // automática
                nuevo.setLimiteCreditos(Integer.parseInt(request.getParameter("limiteCreditos")));
                nuevo.setCreditosActuales(Integer.parseInt(request.getParameter("creditosActuales")));
                nuevo.setIdUsuario(usu.getIdUsuario()); // automático desde sesión
                dao.agregar(nuevo);
                response.sendRedirect("clienteController?accion=listar");
                break;

            case "actualizar":
                cliente actualizado = new cliente();
                actualizado.setIdCliente(Integer.parseInt(request.getParameter("idCliente")));
                actualizado.setNombre(request.getParameter("nombre"));
                actualizado.setApellidos(request.getParameter("apellidos"));
                actualizado.setTelefono(request.getParameter("telefono"));
                actualizado.setDireccion(request.getParameter("direccion"));
                actualizado.setCategoriaCrediticia(request.getParameter("categoriaCrediticia"));
                actualizado.setFechaRegistro(java.time.LocalDateTime.now()); // también se puede mantener actualizada
                actualizado.setLimiteCreditos(Integer.parseInt(request.getParameter("limiteCreditos")));
                actualizado.setCreditosActuales(Integer.parseInt(request.getParameter("creditosActuales")));
                actualizado.setIdUsuario(usu.getIdUsuario()); // desde sesión
                dao.actualizar(actualizado);
                response.sendRedirect("clienteController?accion=listar");
                break;

            default:
                response.sendRedirect("clienteController?accion=listar");
        }
    }
}

