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
import dao.insumosDao;
import modelo.insumos;


@ManagedBean(name = "recetaInsumosBean")
@ViewScoped
public class receta_insumosBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private final receta_insumosDao dao = new receta_insumosDao();

    private int idReceta;                           // Receta seleccionada
    private receta_insumos recetaInsumo = new receta_insumos();
    private List<receta_insumos> listaInsumos = new ArrayList<>();

    private String filtro; 
    private List<insumos> listaInsumosDisponibles;
private final insumosDao iDao = new insumosDao();// 🔍 Filtro de búsqueda por nombre de insumo
// Propiedades


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
    recetaInsumo = new receta_insumos(); // limpia formulario
    cargarInsumos(); // carga lista de insumos de esta receta
}

    public receta_insumos getRecetaInsumo() { return recetaInsumo; }
    public void setRecetaInsumo(receta_insumos recetaInsumo) { this.recetaInsumo = recetaInsumo; }

    public List<receta_insumos> getListaInsumos() { return listaInsumos; }

    public String getFiltro() { return filtro; }
    public void setFiltro(String filtro) { this.filtro = filtro; }
    
    
public List<insumos> getListaInsumosDisponibles() {
    if (listaInsumosDisponibles == null) {
        listaInsumosDisponibles = iDao.listar(); // usar el listar() de insumosDao
    }
    return listaInsumosDisponibles;
}

// Método para agregar insumo a la receta
public void agregar() {
    if (idReceta <= 0) {
        addMessage("⚠️ Selecciona una receta primero.");
        return;
    }

    if (recetaInsumo.getId_ins() <= 0) {
        addMessage("⚠️ Selecciona un insumo.");
        return;
    }

    // Obtener la unidad del insumo seleccionado
    insumos seleccionado = iDao.obtenerPorId(recetaInsumo.getId_ins());
    if (seleccionado == null) {
        addMessage("❌ No se encontró el insumo seleccionado.");
        return;
    }

    // Crear un objeto independiente para guardar en la base de datos
    receta_insumos ri = new receta_insumos();
    ri.setId_rec(idReceta);
    ri.setId_ins(recetaInsumo.getId_ins());
    ri.setCantidad(recetaInsumo.getCantidad());
    ri.setUnidad(seleccionado.getUnidad_medida());

    // Llamar al DAO
    if (dao.agregar(ri)) {
        addMessage("✅ Insumo agregado a la receta.");
        cargarInsumos();

        // Limpiar campos del formulario para agregar otro insumo
        recetaInsumo.setId_ins(0);
        recetaInsumo.setCantidad(0);
    } else {
        addMessage("❌ Error al agregar insumo.");
    }
}

// Nuevo método


private List<receta_insumos> nuevosInsumos = new ArrayList<>();

public List<receta_insumos> getNuevosInsumos() {
    return nuevosInsumos;
}

public void agregarCampoInsumo() {
    receta_insumos ri = new receta_insumos();
    nuevosInsumos.add(ri);
}

public void guardarNuevosInsumos() {
    if (idReceta <= 0) {
        addMessage("⚠️ Selecciona una receta primero.");
        return;
    }

    for (receta_insumos ri : nuevosInsumos) {
        if (ri.getId_ins() > 0 && ri.getCantidad() > 0) {
            insumos seleccionado = iDao.obtenerPorId(ri.getId_ins());
            if (seleccionado != null) {
                ri.setUnidad(seleccionado.getUnidad_medida());
                ri.setId_rec(idReceta);
                dao.agregar(ri);
            }
        }
    }

    addMessage("✅ Todos los insumos agregados.");
    nuevosInsumos.clear();
    cargarInsumos();
}



public void prepararNuevoInsumo() {
    nuevosInsumos = new ArrayList<>();
    nuevosInsumos.add(new receta_insumos()); // fila inicial
}



}