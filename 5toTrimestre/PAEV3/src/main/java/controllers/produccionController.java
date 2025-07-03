package controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import models.produccion;
import models.produccionDao;

@WebServlet(name = "produccionController", urlPatterns = {"/produccionController"})
public class produccionController extends HttpServlet {

    produccionDao dao = new produccionDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if (accion == null || accion.equals("listar")) {
            List<produccion> lista = dao.listar();
            request.setAttribute("listaProduccion", lista);
            request.getRequestDispatcher("views/produccion.jsp").forward(request, response);

        } else if (accion.equals("editar")) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                produccion p = dao.obtenerPorId(id);
                request.setAttribute("produccionEditar", p);
                request.setAttribute("listaProduccion", dao.listar());
                request.getRequestDispatcher("views/produccion.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                response.sendRedirect("produccionController?accion=listar&error=invalid_id");
            }

        } else if (accion.equals("eliminar")) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                boolean exito = dao.eliminar(id);
                if (exito) {
                    response.sendRedirect("produccionController?accion=listar&msg=eliminado");
                } else {
                    response.sendRedirect("produccionController?accion=listar&error=no_encontrado");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect("produccionController?accion=listar&error=invalid_id");
            }

        } else {
            response.sendRedirect("produccionController?accion=listar");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if (accion == null) {
            response.sendRedirect("produccionController?accion=listar");
            return;
        }

        if (accion.equals("agregar")) {
            try {
                String fechaStr = request.getParameter("fecha_produccion");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date fechaUtil = sdf.parse(fechaStr);
                java.sql.Date fecha = new java.sql.Date(fechaUtil.getTime());

                int totalEmp = Integer.parseInt(request.getParameter("total_emp"));
                String tipo = request.getParameter("tipo");
                int cantidad = totalEmp;

                String idProtStr = request.getParameter("id_prot");
                Integer idProt = (idProtStr == null || idProtStr.isEmpty()) ? null : Integer.parseInt(idProtStr);

                String idResStr = request.getParameter("id_res");
                Integer idRes = null;
                if (idResStr != null && !idResStr.trim().isEmpty()) {
                    try {
                        idRes = Integer.parseInt(idResStr);
                    } catch (NumberFormatException e) {
                        idRes = null;
                    }
                }

                String estado = request.getParameter("estado");

                produccion nueva = new produccion();
                nueva.setFecha_produccion(fecha);
                nueva.setTotal_emp(totalEmp);
                nueva.setTipo(tipo);
                nueva.setCantidad(cantidad);
                nueva.setId_prot(idProt);
                nueva.setId_res(idRes);
                nueva.setEstado(estado);

                boolean exito = dao.registrarProduccionCompleta(nueva);

                if (exito) {
                    response.sendRedirect("produccionController?accion=listar&msg=ok");
                } else {
                    response.sendRedirect("produccionController?accion=listar&error=stock");
                }

            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("produccionController?accion=listar&error=datos");
            }

        } else if (accion.equals("actualizar")) {
            try {
                int id = Integer.parseInt(request.getParameter("id_proc"));
                String fechaStr = request.getParameter("fecha_produccion");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date fechaUtil = sdf.parse(fechaStr);
                java.sql.Date fecha = new java.sql.Date(fechaUtil.getTime());

                int totalEmp = Integer.parseInt(request.getParameter("total_emp"));
                String tipo = request.getParameter("tipo");
                int cantidad = totalEmp;

                String idProtStr = request.getParameter("id_prot");
                Integer idProt = (idProtStr == null || idProtStr.isEmpty()) ? null : Integer.parseInt(idProtStr);

                String idResStr = request.getParameter("id_res");
                Integer idRes = null;
                if (idResStr != null && !idResStr.trim().isEmpty()) {
                    try {
                        idRes = Integer.parseInt(idResStr);
                    } catch (NumberFormatException e) {
                        idRes = null;
                    }
                }

                String estado = request.getParameter("estado");

                produccion actualizar = new produccion();
                actualizar.setId_proc(id);
                actualizar.setFecha_produccion(fecha);
                actualizar.setTotal_emp(totalEmp);
                actualizar.setTipo(tipo);
                actualizar.setCantidad(cantidad);
                actualizar.setId_prot(idProt);
                actualizar.setId_res(idRes);
                actualizar.setEstado(estado);

                // Intentar actualización normal
                boolean exito = dao.actualizarProduccionCompleta(actualizar);

                if (exito) {
                    response.sendRedirect("produccionController?accion=listar&msg=actualizado");
                } else {
                    // Simular actualización si no hay stock
                    //boolean simulado = dao.actualizarProduccionCompleta(actualizar, true);
                    //if (simulado) {
                    //    response.sendRedirect("produccionController?accion=listar&msg=simulado");
                    //} else {
                    //    response.sendRedirect("produccionController?accion=listar&error=stock");
                    //}
                }

            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("produccionController?accion=listar&error=datos");
            }
        }
    }
}