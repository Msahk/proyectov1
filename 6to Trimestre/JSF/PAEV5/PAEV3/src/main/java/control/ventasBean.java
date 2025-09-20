package control;

import dao.ventasDao;
import models.ventas;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "ventasBean")
@SessionScoped
public class ventasBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<ventas> listaVentas;
    private ventas ventaNueva;
    private ventas ventaSeleccionada;

    private ventasDao ventasDao = new ventasDao();

    @PostConstruct
    public void init() {
        ventaNueva = new ventas();
        ventaNueva.setFecha(new Date());
        cargarVentas();
    }

    public void cargarVentas() {
        listaVentas = ventasDao.listar();
    }

   public String guardarVenta() {
    if (ventasDao.agregar(ventaNueva)) {
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
    System.out.println("Venta seleccionada: " + v.getIdVen() + ", Tipo: " + v.getTipo());
    this.ventaSeleccionada = v;
    return "/views/Ventas/editarVentas.xhtml?faces-redirect=true";
}

    
    public String volverALaLista() {
        return "/views/Ventas/index.xhtml?faces-redirect=true";
    }

   
    public List<ventas> getListaVentas() { return listaVentas; }
    public ventas getVentaNueva() { return ventaNueva; }
    public void setVentaNueva(ventas ventaNueva) { this.ventaNueva = ventaNueva; }
    public ventas getVentaSeleccionada() { return ventaSeleccionada; }
    public void setVentaSeleccionada(ventas ventaSeleccionada) { this.ventaSeleccionada = ventaSeleccionada; }
}