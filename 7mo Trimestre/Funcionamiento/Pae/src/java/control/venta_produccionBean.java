package control;

import dao.venta_produccionDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import modelo.venta_produccion;
import org.primefaces.PrimeFaces;

@ManagedBean
@SessionScoped
public class venta_produccionBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private final venta_produccionDao vpDao = new venta_produccionDao();
    private venta_produccion ventaProduccion = new venta_produccion();
    private List<venta_produccion> lstVentaProduccion = new ArrayList<>();
    private List<venta_produccion> lstFiltradas;

    private int idVentaSeleccionada;

    @PostConstruct
    public void init() {
        listar();
    }

    public void listar() {
        if (idVentaSeleccionada > 0) {
            lstVentaProduccion = vpDao.listarPorVenta(idVentaSeleccionada);
        } else {
            lstVentaProduccion = new ArrayList<>();
        }
    }

    public void agregar() {
        if (vpDao.agregar(ventaProduccion)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Producción agregada correctamente"));
            listar();
            limpiar();
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo agregar la producción"));
        }
    }

    public void eliminar(venta_produccion vp) {
        vpDao.eliminar(vp);
        listar();
    }

    public void eliminarPorVenta(int idVenta) {
        vpDao.eliminarPorVenta(idVenta);
        listar();
    }

    public void editar(venta_produccion vp) {
        this.ventaProduccion = vp;
    }

    public void actualizar() {
        if (vpDao.agregar(ventaProduccion)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizado", "Producción actualizada"));
            listar();
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo actualizar"));
        }
    }

    public void limpiar() {
        ventaProduccion = new venta_produccion();
    }

    // ---------- Getters y Setters ----------
    public venta_produccion getVentaProduccion() {
        return ventaProduccion;
    }

    public void setVentaProduccion(venta_produccion ventaProduccion) {
        this.ventaProduccion = ventaProduccion;
    }

    public List<venta_produccion> getLstVentaProduccion() {
        return lstVentaProduccion;
    }

    public void setLstVentaProduccion(List<venta_produccion> lstVentaProduccion) {
        this.lstVentaProduccion = lstVentaProduccion;
    }

    public List<venta_produccion> getLstFiltradas() {
        return lstFiltradas;
    }

    public void setLstFiltradas(List<venta_produccion> lstFiltradas) {
        this.lstFiltradas = lstFiltradas;
    }

    public int getIdVentaSeleccionada() {
        return idVentaSeleccionada;
    }

    public void setIdVentaSeleccionada(int idVentaSeleccionada) {
        this.idVentaSeleccionada = idVentaSeleccionada;
    }
}
