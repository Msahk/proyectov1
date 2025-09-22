package control;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import modelo.recetas;
import dao.recetasDao;
import modelo.insumos;
import dao.insumosDao;
import modelo.receta_insumos;
import dao.receta_insumosDao;

@ManagedBean(name = "recetasBean")
@ViewScoped
public class recetasBean implements Serializable {

    private static final long serialVersionUID = 1L;

    // =====================
    // ATRIBUTOS
    // =====================
    private recetas receta = new recetas();
    private List<recetas> listaRecetas = new ArrayList<>();
    private List<receta_insumos> listaInsumosReceta = new ArrayList<>();
    private List<insumos> listaInsumos = new ArrayList<>();

    private int idReceta;                // receta seleccionada
    private int insumoSeleccionado;      // id insumo seleccionado
    private double cantidad;             // cantidad del insumo
    private String unidad;               // unidad de medida

    private String filtro;               // 🔍 filtro de búsqueda

    // DAOs
    private final recetasDao recetaDao = new recetasDao();
    private final insumosDao insumoDao = new insumosDao();
    private final receta_insumosDao recetaInsumosDao = new receta_insumosDao();

    // =====================
    // INIT
    // =====================
    @PostConstruct
    public void init() {
        listaRecetas = recetaDao.listar();
        listaInsumos = insumoDao.listar();
    }

    // =====================
    // CRUD RECETA
    // =====================
    public void agregarReceta() {
        if (recetaDao.agregar(receta)) {
            receta = new recetas();
            listaRecetas = recetaDao.listar();
            addMessage("✅ Receta agregada correctamente.");
        } else {
            addMessage("❌ Error al agregar receta.");
        }
    }

    public void editarReceta() {
        if (recetaDao.actualizar(receta)) {
            receta = new recetas();
            listaRecetas = recetaDao.listar();
            addMessage("✅ Receta actualizada correctamente.");
        } else {
            addMessage("❌ Error al actualizar receta.");
        }
    }

    public void eliminarReceta() {
        if (idReceta > 0) {
            if (recetaDao.eliminar(idReceta)) {
                listaRecetas = recetaDao.listar();
                listaInsumosReceta = null;
                addMessage("✅ Receta eliminada correctamente.");
            } else {
                addMessage("❌ Error al eliminar receta.");
            }
            idReceta = 0; // limpiar id temporal
        } else {
            addMessage("⚠️ No se seleccionó ninguna receta.");
        }
    }

    // =====================
    // CRUD INSUMOS EN RECETA
    // =====================
    public void agregarInsumoAReceta() {
        if (idReceta <= 0) {
            addMessage("⚠️ Selecciona una receta primero.");
            return;
        }
        if (insumoSeleccionado <= 0 || cantidad <= 0 || unidad == null || unidad.trim().isEmpty()) {
            addMessage("⚠️ Datos inválidos para insumo.");
            return;
        }

        receta_insumos ri = new receta_insumos();
        ri.setId_rec(idReceta);
        ri.setId_ins(insumoSeleccionado);
        ri.setCantidad(cantidad);
        ri.setUnidad(unidad);

        if (recetaInsumosDao.agregar(ri)) {
            listaInsumosReceta = recetaInsumosDao.listarPorReceta(idReceta);

            // reset campos
            insumoSeleccionado = 0;
            cantidad = 0;
            unidad = "";

            addMessage("✅ Insumo agregado a la receta.");
        } else {
            addMessage("❌ Error al agregar insumo.");
        }
    }

    public void eliminarInsumoDeReceta(int idRecIns) {
        if (recetaInsumosDao.eliminarPorId(idRecIns)) {
            listaInsumosReceta = recetaInsumosDao.listarPorReceta(idReceta);
            addMessage("✅ Insumo eliminado.");
        } else {
            addMessage("❌ Error al eliminar insumo.");
        }
    }

    // =====================
    // FILTRO
    // =====================
    public List<recetas> getRecetasFiltradas() {
        if (filtro == null || filtro.trim().isEmpty()) {
            return listaRecetas;
        }
        String texto = filtro.toLowerCase();
        List<recetas> filtradas = new ArrayList<>();
        for (recetas r : listaRecetas) {
            if ((r.getNombre() != null && r.getNombre().toLowerCase().contains(texto)) ||
                (r.getDescripcion() != null && r.getDescripcion().toLowerCase().contains(texto))) {
                filtradas.add(r);
            }
        }
        return filtradas;
    }

    // =====================
    // UTILS
    // =====================
    private void addMessage(String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, null));
        System.out.println(mensaje);
    }

    // =====================
    // GETTERS / SETTERS
    // =====================
    public recetas getReceta() { return receta; }
    public void setReceta(recetas receta) { this.receta = receta; }

    public int getIdReceta() { return idReceta; }
    public void setIdReceta(int idReceta) {
        this.idReceta = idReceta;
        if (idReceta > 0) {
            listaInsumosReceta = recetaInsumosDao.listarPorReceta(idReceta);
        }
    }

    public int getInsumoSeleccionado() { return insumoSeleccionado; }
    public void setInsumoSeleccionado(int insumoSeleccionado) { this.insumoSeleccionado = insumoSeleccionado; }

    public double getCantidad() { return cantidad; }
    public void setCantidad(double cantidad) { this.cantidad = cantidad; }

    public String getUnidad() { return unidad; }
    public void setUnidad(String unidad) { this.unidad = unidad; }

    public List<recetas> getListaRecetas() { return listaRecetas; }
    public List<insumos> getListaInsumos() { return listaInsumos; }
    public List<receta_insumos> getListaInsumosReceta() { return listaInsumosReceta; }

    public String getFiltro() { return filtro; }
    public void setFiltro(String filtro) { this.filtro = filtro; }
    
    // Cargar receta en el modal de edición
public void prepararEdicion(recetas r) {
    if (r != null) {
        this.receta = r;
    }
}

public void eliminarRecetaPorId(int id) {
    setIdReceta(id); // selecciona la receta
    eliminarReceta(); // elimina
}

}
