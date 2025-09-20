package control;

import dao.ventasDao;
import dao.usuariosDao;
import dao.clientesDao;
import models.ventas;
import models.usuarios;
import models.clientes;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

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

    private List<usuarios> listaUsuarios = new ArrayList<>();

    private clientes clienteNuevo = new clientes(); 

    @PostConstruct
    public void init() {
        ventaNueva = new ventas();
        ventaNueva.setFecha(new Date());
        cargarVentas();
        cargarUsuarios();
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

    public List<ventas> getListaVentas() {
        return listaVentas;
    }
    public void setListaVentas(List<ventas> listaVentas) {
        this.listaVentas = listaVentas;
    }

   public String guardarVenta() {
    
    int idGenerado = ventasDao.agregar(ventaNueva);
    if (idGenerado > 0) {
        if ("pedido".equalsIgnoreCase(ventaNueva.getTipo())) {
            
            ventas v = ventasDao.obtenerPorId(idGenerado);
            if (v != null) {
               
                dao.pedidosDao pedidosDao = new dao.pedidosDao();
                if (!pedidosDao.existePedidoParaVenta(v.getIdVen())) {
                    models.pedidos nuevoPedido = new models.pedidos();
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
            if ("pedido".equalsIgnoreCase(ventaSeleccionada.getTipo())) {
                if (ventasDao.actualizarTipoVenta(ventaSeleccionada)) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage("Venta actualizada correctamente y reflejada en pedidos."));
                    cargarVentas();
                    return "/views/Ventas/index.xhtml?faces-redirect=true";
                } else {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al actualizar la venta", null));
                    return null;
                }
            } else {
                if (ventasDao.actualizar(ventaSeleccionada)) {
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
        }
        return null;
    }

    public void eliminarVenta(int id) {
        if (ventasDao.eliminar(id)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Venta eliminada correctamente"));
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