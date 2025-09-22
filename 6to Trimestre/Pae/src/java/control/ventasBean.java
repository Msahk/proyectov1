package control;

import dao.ventasDao;
import dao.usuariosDao;
import dao.clientesDao;
import dao.pedidosDao;
import java.io.File;
import java.io.IOException;
import modelo.ventas;
import modelo.usuarios;
import modelo.clientes;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.File;

@ManagedBean(name = "ventasBean")
@SessionScoped
public class ventasBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private ventasDao ventasDao = new ventasDao();
    private usuariosDao usuariosDao = new usuariosDao();
    private clientesDao clienteDao = new clientesDao();
    private List<clientes> listaClientes = new ArrayList<>();
    private List<ventas> listaVentas = new ArrayList<>();
    private ventas ventaNueva = new ventas();
    private ventas ventaSeleccionada;
    private boolean nuevoCliente = false;
 private Integer filtroIdVenta;
    private String filtroTipo;
    private String filtroCliente;
    private String filtroUsuario;
    private Double filtroTotalMin;
    private Double filtroTotalMax;
    private String filtroEstado;
    private Date filtroFechaDesde;
    private Date filtroFechaHasta;

    private List<usuarios> listaUsuarios = new ArrayList<>();

    private clientes clienteNuevo = new clientes(); 
    
   public void exportarPDF() {
    try {
        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/ventas.jasper");
        File jasper = new File(path);
        ventasDataSource vds = new ventasDataSource();

        JasperPrint jprint = JasperFillManager.fillReport(jasper.getPath(), null, vds);

        HttpServletResponse resp = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        resp.addHeader("Content-disposition", "attachment; filename=Ventas.pdf");

        try (ServletOutputStream stream = resp.getOutputStream()) {
            JasperExportManager.exportReportToPdfStream(jprint, stream);
            stream.flush();
        }

        FacesContext.getCurrentInstance().responseComplete();
    } catch (JRException | IOException e) {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error creando reporte de ventas"));
        e.printStackTrace();
    }
}

    @PostConstruct
    public void init() {
        ventaNueva = new ventas();
        ventaNueva.setFecha(new Date());
        cargarVentas();
        cargarUsuarios();
    }
 public Integer getFiltroIdVenta() { return filtroIdVenta; }
    public void setFiltroIdVenta(Integer filtroIdVenta) { this.filtroIdVenta = filtroIdVenta; }

    public String getFiltroTipo() { return filtroTipo; }
    public void setFiltroTipo(String filtroTipo) { this.filtroTipo = filtroTipo; }

    public String getFiltroCliente() { return filtroCliente; }
    public void setFiltroCliente(String filtroCliente) { this.filtroCliente = filtroCliente; }

    public String getFiltroUsuario() { return filtroUsuario; }
    public void setFiltroUsuario(String filtroUsuario) { this.filtroUsuario = filtroUsuario; }

    public Double getFiltroTotalMin() { return filtroTotalMin; }
    public void setFiltroTotalMin(Double filtroTotalMin) { this.filtroTotalMin = filtroTotalMin; }

    public Double getFiltroTotalMax() { return filtroTotalMax; }
    public void setFiltroTotalMax(Double filtroTotalMax) { this.filtroTotalMax = filtroTotalMax; }

    public String getFiltroEstado() { return filtroEstado; }
    public void setFiltroEstado(String filtroEstado) { this.filtroEstado = filtroEstado; }

    public Date getFiltroFechaDesde() { return filtroFechaDesde; }
    public void setFiltroFechaDesde(Date filtroFechaDesde) { this.filtroFechaDesde = filtroFechaDesde; }

    public Date getFiltroFechaHasta() { return filtroFechaHasta; }
    public void setFiltroFechaHasta(Date filtroFechaHasta) { this.filtroFechaHasta = filtroFechaHasta; }

    public List<ventas> getListaVentas() { return listaVentas; }
    public void setListaVentas(List<ventas> listaVentas) { this.listaVentas = listaVentas; }

    // Método de filtro avanzado
    public void filtrarVentas() {
        java.sql.Date sqlFechaDesde = filtroFechaDesde != null ? new java.sql.Date(filtroFechaDesde.getTime()) : null;
        java.sql.Date sqlFechaHasta = filtroFechaHasta != null ? new java.sql.Date(filtroFechaHasta.getTime()) : null;

        listaVentas = ventasDao.filtrarAvanzado(
            filtroIdVenta,
            filtroTipo,
            filtroCliente,
            filtroUsuario,
            filtroTotalMin,
            filtroTotalMax,
            filtroEstado,
            sqlFechaDesde,
            sqlFechaHasta
        );
    }
    public void cargarVentas() {
        listaVentas = ventasDao.listar();
    }
    public boolean isNuevoCliente() {
        return nuevoCliente;
    }

    public void setNuevoCliente(boolean nuevoCliente) {
        this.nuevoCliente = nuevoCliente;
    }
        
    public List<clientes> getListaClientes() {
        if (listaClientes == null || listaClientes.isEmpty()) {
            listaClientes = clienteDao.listar(); 
        }
        return listaClientes;
    }
    public void setListaClientes(List<clientes> listaClientes) {
        this.listaClientes = listaClientes;
    }
    public void cargarUsuarios() {
        listaUsuarios = usuariosDao.listar();
    }

    public List<usuarios> getListaUsuarios() {
        return listaUsuarios;
    }
    public void setListaUsuarios(List<usuarios> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public clientes getClienteNuevo() {
        return clienteNuevo;
    }
    public void setClienteNuevo(clientes clienteNuevo) {
        this.clienteNuevo = clienteNuevo;
    }

    public ventas getVentaNueva() {
        return ventaNueva;
    }
    public void setVentaNueva(ventas ventaNueva) {
        this.ventaNueva = ventaNueva;
    }
    public ventas getVentaSeleccionada() {
        return ventaSeleccionada;
    }
    public void setVentaSeleccionada(ventas ventaSeleccionada) {
        this.ventaSeleccionada = ventaSeleccionada;
    }

   

  public String guardarVenta() {
    if (nuevoCliente) {
        
        int idClienteGenerado = clienteDao.agregar(clienteNuevo);
        if (idClienteGenerado > 0) {
            
            listaClientes = clienteDao.listar();

           
            ventaNueva.setIdCliente(idClienteGenerado);
            ventaNueva.setNombreCliente(clienteNuevo.getNombre());
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al registrar el nuevo cliente", null));
            return null;
        }
    }

   
    int idGenerado = ventasDao.agregar(ventaNueva);
    if (idGenerado > 0) {
        if ("pedido".equalsIgnoreCase(ventaNueva.getTipo())) {
            ventas v = ventasDao.obtenerPorId(idGenerado);
            if (v != null) {
                dao.pedidosDao pedidosDao = new dao.pedidosDao();
                if (!pedidosDao.existePedidoParaVenta(v.getIdVen())) {
                    modelo.pedidos nuevoPedido = new modelo.pedidos();
                    nuevoPedido.setIdVen(v.getIdVen());
                    nuevoPedido.setIdCliente(v.getIdCliente());
                    nuevoPedido.setNombreCliente(v.getNombreCliente());
                    nuevoPedido.setEstado("Pendiente");
                    nuevoPedido.setFechaEntrega(new java.util.Date());
                    nuevoPedido.setObservacionesPedido(v.getObservaciones());
                    pedidosDao.agregar(nuevoPedido);

                    
                    control.pedidosBean pedidosBean = (control.pedidosBean) FacesContext.getCurrentInstance()
                            .getExternalContext().getSessionMap().get("pedidosBean");
                    if (pedidosBean != null) {
                        pedidosBean.cargarPedidos();
                    }
                }
            }
        }

        
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Venta registrada correctamente"));
        ventaNueva = new ventas();
        ventaNueva.setFecha(new Date());
        cargarVentas();
        return "/views/Ventas/index.xhtml?faces-redirect=true";
    } else {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al registrar la venta", null));
        return null;
    }
}

   public String actualizarVenta() {
    if (ventaSeleccionada != null) {
        
        ventas ventaAntes = ventasDao.obtenerPorId(ventaSeleccionada.getIdVen());
        boolean cambioADirectaAPedido = ventaAntes != null
            && "directa".equalsIgnoreCase(ventaAntes.getTipo())
            && "pedido".equalsIgnoreCase(ventaSeleccionada.getTipo());

        boolean actualizado;

        if ("pedido".equalsIgnoreCase(ventaSeleccionada.getTipo())) {
            actualizado = ventasDao.actualizarTipoVenta(ventaSeleccionada);
        } else {
            actualizado = ventasDao.actualizar(ventaSeleccionada);
        }

        if (actualizado) {
            
            if (cambioADirectaAPedido) {
                dao.pedidosDao pedidosDao = new dao.pedidosDao();
                if (!pedidosDao.existePedidoParaVenta(ventaSeleccionada.getIdVen())) {
                    modelo.pedidos nuevoPedido = new modelo.pedidos();
                    nuevoPedido.setIdVen(ventaSeleccionada.getIdVen());
                    nuevoPedido.setIdCliente(ventaSeleccionada.getIdCliente());
                    nuevoPedido.setNombreCliente(ventaSeleccionada.getNombreCliente());
                    nuevoPedido.setEstado("Pendiente");
                    nuevoPedido.setFechaEntrega(new java.util.Date());
                    nuevoPedido.setObservacionesPedido(ventaSeleccionada.getObservaciones());
                    pedidosDao.agregar(nuevoPedido);
                }
                control.pedidosBean pedidosBean = (control.pedidosBean) FacesContext.getCurrentInstance()
                        .getExternalContext().getSessionMap().get("pedidosBean");
                if (pedidosBean != null) {
                    pedidosBean.cargarPedidos(); 
                }
            }

            
            dao.pedidosDao pedidosDao = new dao.pedidosDao();
            if (pedidosDao.existePedidoParaVenta(ventaSeleccionada.getIdVen())) {
                pedidosDao.actualizarEstadoYObservacionesPorVenta(
                    ventaSeleccionada.getIdVen(), 
                    ventaSeleccionada.getEstado(), 
                    ventaSeleccionada.getObservaciones()
                );
            }

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Venta actualizada correctamente"));
            cargarVentas();
            return "/views/Ventas/index.xhtml?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al actualizar la venta", null));
            return null;
        }
    }
    return null;
}
   

    public void eliminarVenta(int id) {
          pedidosDao pedidosDao = new pedidosDao();
           if (pedidosDao.existePedidoParaVenta(id)) {
                pedidosDao.eliminarPorVenta(id); 
           }
        if (ventasDao.eliminar(id)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Venta eliminada correctamente"));
            control.pedidosBean pedidosBean = (control.pedidosBean) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("pedidosBean");
            if (pedidosBean != null) {
                pedidosBean.cargarPedidos(); 
            }
            cargarVentas();
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al eliminar la venta", null));
        }
    }

    public String prepararEdicion(ventas v) {
        this.ventaSeleccionada = v;
        return "/views/Ventas/editarVentas.xhtml?faces-redirect=true";
    }

    public String volverALaLista() {
        return "/views/Ventas/index.xhtml?faces-redirect=true";
    }
}