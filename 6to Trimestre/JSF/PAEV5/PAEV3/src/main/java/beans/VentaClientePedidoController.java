/*
package beans;

import com.google.gson.Gson;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.VentaClientePedido;
import models.VentaClientePedidoDao;
import models.usuarios;
import models.usuariosDao;
import models.ventas;


public class VentaClientePedidoController extends HttpServlet {

    VentaClientePedidoDao vcp_dao = new VentaClientePedidoDao();
    VentaClientePedido vcp = new VentaClientePedido();
    usuariosDao uDao = new usuariosDao();
    
    

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
       
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        
        
        String accion = request.getParameter("accion");
        
        switch (accion != null ? accion : "") {
            case "obtener":
                String idParam = request.getParameter("id");
                if (idParam == null || idParam.trim().isEmpty()) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID no proporcionado");
                    return;
                }

                int id = Integer.parseInt(idParam);
                VentaClientePedido venta = null;

                try {
                    venta = vcp_dao.obtenerPorId(id);
                } catch (SQLException ex) {
                    Logger.getLogger(VentaClientePedidoController.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (venta != null) {
                    Gson gson = new Gson();
                    String json = gson.toJson(venta);
                    response.setContentType("application/json");
                    response.getWriter().write(json);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Venta no encontrada");
                }
                break;


            default:
                throw new AssertionError();
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
                
                case "agregar": {
                    
                    usuariosDao uDao = new usuariosDao();
                    List<usuarios> vendedores = uDao.listarPorRol("EV");
                    request.setAttribute("vendedores", vendedores);
                    
                    
                    int idUsuario              = (Integer.parseInt(request.getParameter("vendedor")));
                    String nombreCliente       = request.getParameter("nombreCliente");
                    String telefonoCliente     = request.getParameter("telefonoCliente");
                    String correoCliente       = request.getParameter("correoCliente");
                    String tipoVenta           = request.getParameter("tipoVenta"); 
                    String totalVentaStr       = request.getParameter("totalVenta");
                    String estadoVenta         = request.getParameter("estadoVenta");
                    String obsVenta            = request.getParameter("observaciones");
                    String fechaEntregaStr     = request.getParameter("fechaEntregaPedido");   // "2025-07-06T09:00"
                    String obsPedido           = request.getParameter("observaciones");

                    // 2) Parsear a los tipos correctos
                    LocalDateTime fechaVenta = LocalDateTime.now();
                    LocalDateTime fechaEntrega = LocalDateTime.parse(fechaEntregaStr);
                    BigDecimal totalVenta = new BigDecimal(totalVentaStr);

                    // 3) Obtener el usuario logueado de sesión
                    usuarios usuarioSesion = (usuarios) request.getSession().getAttribute("usuarios");
                    if (usuarioSesion == null) {
                        response.sendRedirect(request.getContextPath() + "/index.jsp");
                        return;
                    }

                    // 4) Construir el modelo
                    VentaClientePedido vcp = new VentaClientePedido(
                        nombreCliente,
                        telefonoCliente,
                        correoCliente,
                        tipoVenta,
                        fechaVenta,
                        idUsuario,
                        totalVenta,
                        estadoVenta,
                        obsVenta,
                        fechaEntrega,
                        obsPedido
                    );

                    // 5) Llamar al DAO que ejecuta el SP
                    VentaClientePedidoDao vcpDao = new VentaClientePedidoDao();
                    boolean ok = vcpDao.insertar(vcp);

                    // 6) Redirigir según el resultado
                    if (ok) {
                        response.sendRedirect(request.getContextPath() + "/ventasController?accion=listar");
                    } else {
                        request.setAttribute("error", "No se pudo guardar la venta y pedido");
                        RequestDispatcher rd = request.getRequestDispatcher("/views/ventas/registroVenta.jsp");
                        rd.forward(request, response);
                    }
                    break;
                }
                    
                    case "actualizar": {
    // — IDs ocultos —
    int idVenta   = Integer.parseInt(request.getParameter("idVenta"));
    int idCliente = Integer.parseInt(request.getParameter("idCliente"));

    // — Parámetros del formulario —
    String nombreCliente   = request.getParameter("nombreCliente");
    String telefonoCliente = request.getParameter("telefonoCliente");
    String correoCliente   = request.getParameter("correoCliente");
    String tipoVenta       = request.getParameter("tipoVenta");
    String estadoVenta     = request.getParameter("estadoVenta");
    String totalStr        = request.getParameter("totalVenta");
    String obsVenta        = request.getParameter("observaciones");
    String fechaEntregaStr = request.getParameter("fechaEntregaPedido");
    String vendedorStr     = request.getParameter("vendedor");  // <-- nuevo

    // — Validar y parsear vendedor —
    int idVendedor;
    try {
        idVendedor = Integer.parseInt(vendedorStr);
    } catch (NumberFormatException | NullPointerException ex) {
        idVendedor = -1; // o maneja el error como prefieras
    }

    // — Parsear total y fecha —
    BigDecimal totalVenta = (totalStr == null || totalStr.isEmpty())
                             ? BigDecimal.ZERO
                             : new BigDecimal(totalStr);
    LocalDateTime fechaEntrega = LocalDateTime.parse(fechaEntregaStr);

    // — Reconstruir el modelo —
    VentaClientePedido vcp = new VentaClientePedido();
    vcp.setNombreCliente      (nombreCliente);
    vcp.setTelefonoCliente    (telefonoCliente);
    vcp.setCorreoCliente      (correoCliente);
    vcp.setTipoVenta          (tipoVenta);
    vcp.setEstadoVenta        (estadoVenta);
    vcp.setTotalVenta         (totalVenta);
    vcp.setObsVenta           (obsVenta);
    vcp.setFechaEntregaPedido (fechaEntrega);
    vcp.setIdUsuario          (idVendedor);  // <-- asigna aquí

    // — Llamar al DAO de actualización —
    boolean actualizado = vcp_dao.actualizar(idVenta, idCliente, vcp);

    if (actualizado) {
        response.sendRedirect(request.getContextPath()
            + "/VentaClientePedidoController?accion=listar&msg=actualizado");
    } else {
        request.setAttribute("error", "No se pudo actualizar la venta");
        doGet(request, response);
    }
    break;
}

                 default:
                response.sendRedirect("index.jsp");
            }
        
    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
*/