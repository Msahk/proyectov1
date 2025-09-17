package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;
import models.receta_insumos;
import models.receta_insumosDao;

@ManagedBean(name = "recetaInsumosBean")
@ViewScoped
public class receta_insumosBean implements Serializable {

    private receta_insumosDao dao = new receta_insumosDao();

    private int idReceta; // Receta seleccionada
    private receta_insumos recetaInsumo = new receta_insumos();
    private List<receta_insumos> listaInsumos;

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

    // =====================
    // MÉTODOS
    // =====================

    // 📌 Listar insumos de la receta seleccionada
    public void cargarInsumos() {
        if (idReceta > 0) {
            listaInsumos = dao.listarPorReceta(idReceta);
        }
    }

    // 📌 Agregar
    public void agregar() {
        recetaInsumo.setId_rec(idReceta);
        dao.agregar(recetaInsumo);

        // 🔥 En vez de solo limpiar, recargamos lista
        recetaInsumo = new receta_insumos(); 
        cargarInsumos();
    }

    // 📌 Editar
    public void editar() {
        dao.actualizar(recetaInsumo);

        recetaInsumo = new receta_insumos();
        cargarInsumos(); // 🔥 importante
    }

    // 📌 Eliminar
    public void eliminar(int idRecIns) {
        dao.eliminarPorId(idRecIns);

        // 🔥 actualizar lista inmediatamente
        cargarInsumos();
    }

    // 📌 Cargar insumo en formulario para editar
    public void cargarParaEditar(receta_insumos ri) {
        this.recetaInsumo = ri;
    }
}
