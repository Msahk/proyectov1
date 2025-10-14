package control;

import dao.venta_recetasDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import modelo.venta_recetas;
import org.primefaces.PrimeFaces;

@ManagedBean
@SessionScoped
public class venta_recetasBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private final venta_recetasDao vrDao = new venta_recetasDao();
    private venta_recetas ventaReceta = new venta_recetas();
    private List<venta_recetas> lstVentaRecetas = new ArrayList<>();
    private List<venta_recetas> lstFiltradas;

    private int idVentaSeleccionada;

    @PostConstruct
    public void init() {
        listar();
    }

    public void listar() {
        if (idVentaSeleccionada > 0) {
            lstVentaRecetas = vrDao.listarPorVenta(idVentaSeleccionada);
        } else {
            lstVentaRecetas = new ArrayList<>();
        }
    }

    public void agregar() {
        if (vrDao.agregar(ventaReceta)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Receta agregada correctamente"));
            listar();
            limpiar();
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo agregar la receta"));
        }
    }

    public void eliminar(venta_recetas vr) {
        vrDao.eliminar(vr);
        listar();
    }

    public void eliminarPorVenta(int idVenta) {
        vrDao.eliminarPorVenta(idVenta);
        listar();
    }

    public void editar(venta_recetas vr) {
        this.ventaReceta = vr;
    }

    public void actualizar() {
        if (vrDao.agregar(ventaReceta)) { // reutilizamos agregar ya que puede actualizar
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizado", "Receta actualizada"));
            listar();
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo actualizar"));
        }
    }

    public void limpiar() {
        ventaReceta = new venta_recetas();
    }

    // ---------- Getters y Setters ----------
    public venta_recetas getVentaReceta() {
        return ventaReceta;
    }

    public void setVentaReceta(venta_recetas ventaReceta) {
        this.ventaReceta = ventaReceta;
    }

    public List<venta_recetas> getLstVentaRecetas() {
        return lstVentaRecetas;
    }

    public void setLstVentaRecetas(List<venta_recetas> lstVentaRecetas) {
        this.lstVentaRecetas = lstVentaRecetas;
    }

    public List<venta_recetas> getLstFiltradas() {
        return lstFiltradas;
    }

    public void setLstFiltradas(List<venta_recetas> lstFiltradas) {
        this.lstFiltradas = lstFiltradas;
    }

    public int getIdVentaSeleccionada() {
        return idVentaSeleccionada;
    }

    public void setIdVentaSeleccionada(int idVentaSeleccionada) {
        this.idVentaSeleccionada = idVentaSeleccionada;
    }
}
