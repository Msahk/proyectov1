package control;

import dao.receta_insumosDao;
import dao.recetasDao;
import dao.insumosDao;
import modelo.receta_insumos;
import modelo.recetas;
import modelo.insumos;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

import org.primefaces.PrimeFaces;

@ManagedBean
@ViewScoped
public class receta_insumosBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private final receta_insumosDao dao = new receta_insumosDao();
    private final recetasDao rDao = new recetasDao();
    private final insumosDao iDao = new insumosDao();

    private receta_insumos recetaInsumo = new receta_insumos();
    private List<receta_insumos> lstRecetaInsumos;
    private List<recetas> lstRecetas;
    private List<insumos> lstInsumos;

    private recetas receta; // Receta actual para gestionar

    
    @PostConstruct
public void init() {
    // Recuperar receta seleccionada de la sesión
    receta = (recetas) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("recetaSeleccionada");

    cargarRecetas();  // Cargar todas las recetas (para dropdown si lo necesitas)
    cargarInsumos();  // Cargar insumos disponibles
    listar();         // Listar los insumos de la receta actual
}


    // 🔹 Listar registros de la receta seleccionada
    public void listar() {
        if (receta != null) {
            lstRecetaInsumos = dao.buscarPorReceta(receta.getId_rec());
            for (receta_insumos ri : lstRecetaInsumos) {
                ri.setReceta(rDao.obtenerPorId(ri.getId_rec()));
                ri.setInsumo(iDao.obtenerPorId(ri.getId_ins()));
            }
        }
    }

    // 🔹 Cargar recetas disponibles
    private void cargarRecetas() {
        lstRecetas = rDao.listar();
    }

    // 🔹 Cargar insumos disponibles
    private void cargarInsumos() {
        lstInsumos = iDao.listar();
    }

    // ➕ Agregar nuevo insumo a la receta
    public void agregar() {
        if (receta == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "No hay receta seleccionada"));
            return;
        }

        recetaInsumo.setId_rec(receta.getId_rec());

        if (dao.existeRelacion(recetaInsumo.getId_rec(), recetaInsumo.getId_ins())) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, "Duplicado", "Este insumo ya está asignado a la receta"));
            return;
        }

        if (dao.agregar(recetaInsumo)) {
            rDao.actualizarEstado(receta.getId_rec());
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Insumo agregado correctamente"));
            listar();
            limpiar();
            PrimeFaces.current().ajax().update("formRecetaInsumos:tablaRecetaInsumos, formRecetaInsumos:msj");
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo agregar el registro"));
        }
    }

    // ✏️ Preparar registro para edición
    public void editar(receta_insumos ri) {
        this.recetaInsumo = dao.obtenerPorId(ri.getId_rec_ins());
        this.recetaInsumo.setReceta(rDao.obtenerPorId(recetaInsumo.getId_rec()));
        this.recetaInsumo.setInsumo(iDao.obtenerPorId(recetaInsumo.getId_ins()));
    }

    // 💾 Actualizar registro
    public void actualizar() {
        if (dao.actualizar(recetaInsumo)) {
            rDao.actualizarEstado(receta.getId_rec());
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizado", "Registro modificado correctamente"));
            listar();
            limpiar();
            PrimeFaces.current().ajax().update("formRecetaInsumos:tablaRecetaInsumos, formRecetaInsumos:msj");
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo actualizar el registro"));
        }
    }

    // ❌ Eliminar registro
    public void eliminar(receta_insumos ri) {
        dao.eliminar(ri);
        rDao.actualizarEstado(receta.getId_rec());
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminado", "Registro eliminado correctamente"));
        listar();
        PrimeFaces.current().ajax().update("formRecetaInsumos:tablaRecetaInsumos, formRecetaInsumos:msj");
    }

    // 🔍 Buscar insumos por receta
    public void buscarPorReceta(int id_rec) {
        receta = rDao.obtenerPorId(id_rec);
        lstRecetaInsumos = dao.buscarPorReceta(id_rec);
        for (receta_insumos ri : lstRecetaInsumos) {
            ri.setReceta(rDao.obtenerPorId(ri.getId_rec()));
            ri.setInsumo(iDao.obtenerPorId(ri.getId_ins()));
        }

        if (lstRecetaInsumos.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, "Sin resultados", "Esta receta no tiene insumos asignados"));
        }
    }

    // 🧹 Limpiar formulario
    public void limpiar() {
        recetaInsumo = new receta_insumos();
    }

    // ✅ Getters y Setters
    public receta_insumos getRecetaInsumo() {
        return recetaInsumo;
    }

    public void setRecetaInsumo(receta_insumos recetaInsumo) {
        this.recetaInsumo = recetaInsumo;
    }

    public List<receta_insumos> getLstRecetaInsumos() {
        return lstRecetaInsumos;
    }

    public void setLstRecetaInsumos(List<receta_insumos> lstRecetaInsumos) {
        this.lstRecetaInsumos = lstRecetaInsumos;
    }

    public List<recetas> getLstRecetas() {
        return lstRecetas;
    }

    public void setLstRecetas(List<recetas> lstRecetas) {
        this.lstRecetas = lstRecetas;
    }

    public List<insumos> getLstInsumos() {
        return lstInsumos;
    }

    public void setLstInsumos(List<insumos> lstInsumos) {
        this.lstInsumos = lstInsumos;
    }

    public recetas getReceta() {
        return receta;
    }

    public void setReceta(recetas receta) {
        this.receta = receta;
    }
}
