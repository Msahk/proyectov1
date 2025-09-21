package control;

import dao.pedidosDao;
import dao.ventasDao;
import modelo.pedidos;
import modelo.ventas;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "pedidosBean")
@SessionScoped
public class pedidosBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<pedidos> listaPedidos;
    private pedidos pedidoNuevo;
    private pedidos pedidoSeleccionado;
    private List<ventas> listaVentas;

    private pedidosDao pedidosDao = new pedidosDao();
    private ventasDao ventasDao = new ventasDao();

    @PostConstruct
    public void init() {
        pedidoNuevo = new pedidos();
        pedidoNuevo.setFechaEntrega(new Date());
        cargarPedidos();
        cargarVentas();
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
            
            if ("Tomado".equalsIgnoreCase(pedidoSeleccionado.getEstado()) || "Completado".equalsIgnoreCase(pedidoSeleccionado.getEstado())) {
                ventasDao.actualizarEstado(pedidoSeleccionado.getIdVen(), "Completada");
            }
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Pedido actualizado correctamente"));
            cargarPedidos();
            cargarVentas();
            return "/views/Pedidos/Index.xhtml?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al actualizar el pedido: " + pedidosDao.ultimoError, null));
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