package control;

import dao.produccion_recetasDao;
import dao.recetasDao;
import modelo.produccion_recetas;
import modelo.recetas;
import modelo.produccion;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "produccionrecetasBean")
@ViewScoped
public class produccion_recetasBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<produccion_recetas> lstProduccionRecetas = new ArrayList<>();
    private List<recetas> lstRecetasActivas = new ArrayList<>();
    private int idProduccionActual;

    private final produccion_recetasDao dao = new produccion_recetasDao();
    private final recetasDao daoRecetas = new recetasDao();

    @PostConstruct
    public void init() {
        // Cargar todas las recetas activas (para mostrar nombres)
        lstRecetasActivas = daoRecetas.listarActivas();

        // Recuperar la producción seleccionada desde la sesión
        FacesContext fc = FacesContext.getCurrentInstance();
        produccion prod = (produccion) fc.getExternalContext()
                .getSessionMap().get("produccionSeleccionada");

        if (prod != null) {
            idProduccionActual = prod.getId_proc();
            cargarRecetasPorProduccion();

            // Limpiar la producción de la sesión para no dejar datos obsoletos
            //fc.getExternalContext().getSessionMap().remove("produccionSeleccionada");
        }
    }

    // 🔍 Cargar recetas asociadas a la producción actual
    private void cargarRecetasPorProduccion() {
        if (idProduccionActual > 0) {
            lstProduccionRecetas = dao.buscarPorProduccion(idProduccionActual);
        }
    }

    // 🔹 Obtener nombre de la receta por ID (para mostrar en la tabla)
    public String getNombreReceta(int id_rec) {
        for (recetas r : lstRecetasActivas) {
            if (r.getId_rec() == id_rec) {
                return r.getNombre();
            }
        }
        return "Desconocida";
    }
    
    public int obtenerIdRecetaPorProduccion(int idProduccion) {
    if (lstProduccionRecetas != null) {
        for (produccion_recetas pr : lstProduccionRecetas) {
            if (pr.getId_produccion() == idProduccion) { // <--- aquí el getter correcto
                return pr.getId_rec(); // Devuelve el id de la receta asociada
            }
        }
    }
    return -1; // Devuelve -1 si no encuentra ninguna receta
}


    // 🧩 Getters
    public List<produccion_recetas> getLstProduccionRecetas() {
        return lstProduccionRecetas;
    }

    public List<recetas> getLstRecetasActivas() {
        return lstRecetasActivas;
    }

    public int getIdProduccionActual() {
        return idProduccionActual;
    }
}
