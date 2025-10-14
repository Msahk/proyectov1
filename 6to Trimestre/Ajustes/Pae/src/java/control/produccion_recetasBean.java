package control;

import dao.produccion_recetasDao;
import dao.recetasDao;
import modelo.produccion_recetas;
import modelo.recetas;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class produccion_recetasBean implements Serializable {

    private produccion_recetas objProduccionReceta = new produccion_recetas();
    private List<produccion_recetas> lstProduccionRecetas = new ArrayList<>();
    private List<recetas> lstRecetasActivas = new ArrayList<>();

    private produccion_recetasDao dao = new produccion_recetasDao();
    private recetasDao daoRecetas = new recetasDao();

    private int idProduccionActual;

    @PostConstruct
    public void init() {
        listarRecetasActivas(); // Cargar recetas activas para el select
        // Capturar idProduccion desde la URL
        FacesContext fc = FacesContext.getCurrentInstance();
        String idProdParam = fc.getExternalContext().getRequestParameterMap().get("idProduccion");
        if (idProdParam != null && !idProdParam.isEmpty()) {
            try {
                idProduccionActual = Integer.parseInt(idProdParam);
                cargarRecetasPorProduccion(); // Cargar recetas asociadas a esta producción
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    // 🔹 Cargar recetas activas disponibles
    public void listarRecetasActivas() {
        lstRecetasActivas = daoRecetas.listarActivas();
    }

    // 🔍 Cargar recetas de la producción actual
    public void cargarRecetasPorProduccion() {
        if (idProduccionActual > 0) {
            lstProduccionRecetas = dao.buscarPorProduccion(idProduccionActual);
            if (lstProduccionRecetas.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Sin recetas",
                    "Esta producción no tiene recetas asociadas aún"));
            }
        }
    }

    // ➕ Agregar relación producción - receta
    public void agregar() {
        try {
            objProduccionReceta.setId_produccion(idProduccionActual);

            if (dao.existeRelacion(idProduccionActual, objProduccionReceta.getId_rec())) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Duplicado",
                    "Esta receta ya está asociada a la producción"));
                return;
            }

            if (objProduccionReceta.getCantidad() <= 0) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Cantidad inválida",
                    "Debe ingresar una cantidad mayor a cero"));
                return;
            }

            if (dao.agregar(objProduccionReceta)) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito",
                    "Receta asociada correctamente"));
                limpiar();
                cargarRecetasPorProduccion();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ❌ Eliminar relación
    public void eliminar(produccion_recetas pr) {
        if (dao.eliminar(pr.getId_detalle())) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito",
                "Receta eliminada correctamente"));
            cargarRecetasPorProduccion();
        }
    }

    // 🔹 Obtener nombre de la receta para mostrar en la tabla
    public String getNombreReceta(int id_rec) {
        for (recetas r : lstRecetasActivas) {
            if (r.getId_rec() == id_rec) {
                return r.getNombre();
            }
        }
        return "Desconocida";
    }

    // 🔄 Limpiar campos
    public void limpiar() {
        objProduccionReceta = new produccion_recetas();
    }

    // 🧩 Getters y Setters
    public produccion_recetas getObjProduccionReceta() { return objProduccionReceta; }
    public void setObjProduccionReceta(produccion_recetas objProduccionReceta) { this.objProduccionReceta = objProduccionReceta; }

    public List<produccion_recetas> getLstProduccionRecetas() { return lstProduccionRecetas; }
    public void setLstProduccionRecetas(List<produccion_recetas> lstProduccionRecetas) { this.lstProduccionRecetas = lstProduccionRecetas; }

    public List<recetas> getLstRecetasActivas() { return lstRecetasActivas; }
    public void setLstRecetasActivas(List<recetas> lstRecetasActivas) { this.lstRecetasActivas = lstRecetasActivas; }

    public int getIdProduccionActual() { return idProduccionActual; }
    public void setIdProduccionActual(int idProduccionActual) { this.idProduccionActual = idProduccionActual; }
}
