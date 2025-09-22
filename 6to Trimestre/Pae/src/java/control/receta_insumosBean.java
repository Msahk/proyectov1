package control;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import modelo.receta_insumos;
import dao.receta_insumosDao;

@ManagedBean(name = "recetaInsumosBean")
@ViewScoped
public class receta_insumosBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private final receta_insumosDao dao = new receta_insumosDao();

    private int idReceta;                           // Receta seleccionada
    private receta_insumos recetaInsumo = new receta_insumos();
    private List<receta_insumos> listaInsumos = new ArrayList<>();

    private String filtro;                          // 🔍 Filtro de búsqueda por nombre de insumo

    // =====================
    // INIT
    // =====================
    @PostConstruct
    public void init() {
        if (idReceta > 0) {
            cargarInsumos();
        }
    }

    // =====================
    // MÉTODOS CRUD
    // =====================

    // 📌 Listar insumos de la receta seleccionada
    public void cargarInsumos() {
        if (idReceta > 0) {
            listaInsumos = dao.listarPorReceta(idReceta);
        }
    }

    // 📌 Agregar insumo
    public void agregar() {
        if (idReceta <= 0) {
            addMessage("⚠️ Selecciona una receta primero.");
            return;
        }
        recetaInsumo.setId_rec(idReceta);
        if (dao.agregar(recetaInsumo)) {
            recetaInsumo = new receta_insumos();
            cargarInsumos();
            addMessage("✅ Insumo agregado a la receta.");
        } else {
            addMessage("❌ Error al agregar insumo.");
        }
    }

    // 📌 Editar insumo
    public void editar() {
        if (dao.actualizar(recetaInsumo)) {
            recetaInsumo = new receta_insumos();
            cargarInsumos();
            addMessage("✅ Insumo actualizado.");
        } else {
            addMessage("❌ Error al actualizar insumo.");
        }
    }

    // 📌 Eliminar insumo
    public void eliminar(int idRecIns) {
        if (dao.eliminarPorId(idRecIns)) {
            cargarInsumos();
            addMessage("✅ Insumo eliminado.");
        } else {
            addMessage("❌ Error al eliminar insumo.");
        }
    }

    // 📌 Cargar insumo en formulario para editar
    public void cargarParaEditar(receta_insumos ri) {
        this.recetaInsumo = ri;
    }

    // =====================
    // FILTRO
    // =====================
    public List<receta_insumos> getInsumosFiltrados() {
        if (filtro == null || filtro.trim().isEmpty()) {
            return listaInsumos;
        }
        String texto = filtro.toLowerCase();
        List<receta_insumos> filtrados = new ArrayList<>();
        for (receta_insumos ri : listaInsumos) {
            if (ri.getNombre_insumo() != null && ri.getNombre_insumo().toLowerCase().contains(texto)) {
                filtrados.add(ri);
            }
        }
        return filtrados;
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
    // GETTERS Y SETTERS
    // =====================
    public int getIdReceta() { return idReceta; }
    public void setIdReceta(int idReceta) {
        this.idReceta = idReceta;
        cargarInsumos();
    }

    public receta_insumos getRecetaInsumo() { return recetaInsumo; }
    public void setRecetaInsumo(receta_insumos recetaInsumo) { this.recetaInsumo = recetaInsumo; }

    public List<receta_insumos> getListaInsumos() { return listaInsumos; }

    public String getFiltro() { return filtro; }
    public void setFiltro(String filtro) { this.filtro = filtro; }
}
