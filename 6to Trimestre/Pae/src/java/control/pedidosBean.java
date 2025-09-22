package control;

import dao.clientesDao;
import dao.pedidosDao;
import dao.ventasDao;
import modelo.pedidos;
import modelo.ventas;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import modelo.clientes;

@ManagedBean(name = "pedidosBean")
@SessionScoped
public class pedidosBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<pedidos> listaPedidos;
    private pedidos pedidoNuevo;
    private pedidos pedidoSeleccionado;
    private List<ventas> listaVentas;
    private clientesDao clienteDao = new clientesDao();
    private List<clientes> listaClientes = new ArrayList<>();
    private Integer filtroIdPedido;
private Integer filtroIdVenta;
private String filtroEstado;
private String filtroCliente;
private Date filtroFechaDesde;
private Date filtroFechaHasta;


    private pedidosDao pedidosDao = new pedidosDao();
    private ventasDao ventasDao = new ventasDao();

    @PostConstruct
    public void init() {
        pedidoNuevo = new pedidos();
        pedidoNuevo.setFechaEntrega(new Date());
        cargarPedidos();
        cargarVentas();
    }
 public Integer getFiltroIdPedido() { return filtroIdPedido; }
    public void setFiltroIdPedido(Integer filtroIdPedido) { this.filtroIdPedido = filtroIdPedido; }

    public Integer getFiltroIdVenta() { return filtroIdVenta; }
    public void setFiltroIdVenta(Integer filtroIdVenta) { this.filtroIdVenta = filtroIdVenta; }

    public String getFiltroEstado() { return filtroEstado; }
    public void setFiltroEstado(String filtroEstado) { this.filtroEstado = filtroEstado; }

    public String getFiltroCliente() { return filtroCliente; }
    public void setFiltroCliente(String filtroCliente) { this.filtroCliente = filtroCliente; }

    public Date getFiltroFechaDesde() { return filtroFechaDesde; }
    public void setFiltroFechaDesde(Date filtroFechaDesde) { this.filtroFechaDesde = filtroFechaDesde; }

    public Date getFiltroFechaHasta() { return filtroFechaHasta; }
    public void setFiltroFechaHasta(Date filtroFechaHasta) { this.filtroFechaHasta = filtroFechaHasta; }
   
    public void setListaPedidos(List<pedidos> listaPedidos) { this.listaPedidos = listaPedidos; }

    
    public void filtrarPedidos() {
        java.sql.Date sqlFechaDesde = filtroFechaDesde != null ? new java.sql.Date(filtroFechaDesde.getTime()) : null;
        java.sql.Date sqlFechaHasta = filtroFechaHasta != null ? new java.sql.Date(filtroFechaHasta.getTime()) : null;

        listaPedidos = pedidosDao.filtrarAvanzado(
            filtroIdPedido,
            filtroIdVenta,
            filtroEstado,
            filtroCliente,
            sqlFechaDesde,
            sqlFechaHasta
        );
    }

    public void cargarPedidos() {
        listaPedidos = pedidosDao.listar();
    }

    public void cargarVentas() {
        listaVentas = ventasDao.listarProcesando(); 
    }

    public String guardarPedido() {
        if (pedidosDao.agregar(pedidoNuevo)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Pedido registrado correctamente"));
            pedidoNuevo = new pedidos();
            pedidoNuevo.setFechaEntrega(new Date());
            cargarPedidos();
            cargarVentas();
            return "/views/Pedidos/Index.xhtml?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al registrar el pedido: " + pedidosDao.ultimoError, null));
            return null;
        }
    }

public String actualizarPedido() {
    if (pedidosDao.actualizar(pedidoSeleccionado)) {
        
        ventas venta = ventasDao.obtenerPorId(pedidoSeleccionado.getIdVen());
        if (venta != null) {
            listaClientes = clienteDao.listar(); 
            pedidoSeleccionado.setIdCliente(venta.getIdCliente());
            pedidoSeleccionado.setNombreCliente(venta.getNombreCliente());
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al obtener la venta asociada al pedido", null));
            return null;
        }

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Pedido actualizado correctamente"));
        cargarPedidos(); 
        cargarVentas();  
        return "/views/Pedidos/Index.xhtml?faces-redirect=true";
    } else {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al actualizar el pedido", null));
        return null;
    }
}

    public void eliminarPedido(int idPed) {
        if (pedidosDao.eliminar(idPed)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Pedido eliminado correctamente"));
            cargarPedidos();
            cargarVentas();
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al eliminar el pedido: " + pedidosDao.ultimoError, null));
        }
    }

    public String prepararEdicion(pedidos p) {
        this.pedidoSeleccionado = p;
        return "/views/Pedidos/editarPedido.xhtml?faces-redirect=true";
    }

    public String volverALaLista() {
        return "/views/Pedidos/Index.xhtml?faces-redirect=true";
    }

    public List<pedidos> getListaPedidos() { return listaPedidos; }
    public pedidos getPedidoNuevo() { return pedidoNuevo; }
    public void setPedidoNuevo(pedidos pedidoNuevo) { this.pedidoNuevo = pedidoNuevo; }
    public pedidos getPedidoSeleccionado() { return pedidoSeleccionado; }
    public void setPedidoSeleccionado(pedidos pedidoSeleccionado) { this.pedidoSeleccionado = pedidoSeleccionado; }
    public List<ventas> getListaVentas() { return listaVentas; }
    public void setListaVentas(List<ventas> listaVentas) { this.listaVentas = listaVentas; }
}