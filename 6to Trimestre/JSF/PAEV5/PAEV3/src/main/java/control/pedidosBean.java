package control;

import dao.pedidosDao;
import dao.ventasDao;
import models.pedidos;
import models.ventas;
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
        listaVentas = ventasDao.listar(); 
    }

    public String guardarPedido() {
        if (pedidosDao.agregar(pedidoNuevo)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Pedido registrado correctamente"));
            pedidoNuevo = new pedidos();
            pedidoNuevo.setFechaEntrega(new Date());
            cargarPedidos();
            return "/views/Pedidos/Index.xhtml?faces-redirect=true"; 
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al registrar el pedido: " + pedidosDao.ultimoError, null));
            return null; 
        }
    }

    public String actualizarPedido() {
        System.out.println("¡Método actualizarPedido llamado!");
        if (pedidosDao.actualizar(pedidoSeleccionado)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Pedido actualizado correctamente"));
            cargarPedidos();
            return "/views/Pedidos/index.xhtml?faces-redirect=true"; 
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
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al eliminar el pedido: " + pedidosDao.ultimoError, null));
        }
    }

   public String prepararEdicion(pedidos p) {
    System.out.println("Pedido seleccionado: " + p.getIdPed());
    this.pedidoSeleccionado = p;
    return "/views/Pedidos/editarPedido.xhtml?faces-redirect=true";
}

    public String volverALaLista() {
        return "/views/Pedidos/index.xhtml?faces-redirect=true";
    }

    public List<pedidos> getListaPedidos() { return listaPedidos; }
    public pedidos getPedidoNuevo() { return pedidoNuevo; }
    public void setPedidoNuevo(pedidos pedidoNuevo) { this.pedidoNuevo = pedidoNuevo; }
    public pedidos getPedidoSeleccionado() { return pedidoSeleccionado; }
    public void setPedidoSeleccionado(pedidos pedidoSeleccionado) { this.pedidoSeleccionado = pedidoSeleccionado; }
    public List<ventas> getListaVentas() { return listaVentas; }
    public void setListaVentas(List<ventas> listaVentas) { this.listaVentas = listaVentas; }
}