package control;

import dao.venta_recetasDao;
import dao.recetasDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import modelo.venta_recetas;
import modelo.recetas;
import org.primefaces.PrimeFaces;

@ManagedBean
@SessionScoped
public class venta_recetasBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private final venta_recetasDao vrDao = new venta_recetasDao();
    private final recetasDao recetaDao = new recetasDao(); // ✅ Para obtener el precio actual

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

    // ✅ Método para agregar con cálculo automático
    public void agregar() {
        try {
            // 1️⃣ Consultar el precio actual de la receta seleccionada
            recetas receta = recetaDao.obtenerPorId(ventaReceta.getIdReceta());
            if (receta != null) {
                ventaReceta.setPrecio(receta.getPrecio()); // Guardar precio actual
                ventaReceta.setSubtotal(receta.getPrecio() * ventaReceta.getCantidad()); // Calcular subtotal
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se encontró el precio de la receta"));
                return;
            }

            // 2️⃣ Asociar la venta seleccionada
            ventaReceta.setIdVenta(idVentaSeleccionada);

            // 3️⃣ Guardar en BD
            if (vrDao.agregar(ventaReceta)) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Receta agregada correctamente"));
                listar();
                limpiar();
                PrimeFaces.current().ajax().update("formVentas"); // Actualiza la vista
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo agregar la receta"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ocurrió un error al agregar la receta"));
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
        if (vrDao.agregar(ventaReceta)) {
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

    // Getters y setters
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
