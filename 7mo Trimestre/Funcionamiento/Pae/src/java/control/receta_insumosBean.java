package control;

import dao.receta_insumosDao;
import dao.recetasDao;
import dao.insumosDao;
import modelo.receta_insumos;
import modelo.recetas;
import modelo.insumos;
import modelo.produccion;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

import org.primefaces.PrimeFaces;

@ManagedBean
@ViewScoped
public class receta_insumosBean implements Serializable {

    private static final long serialVersionUID = 1L;

    // 🔹 Inyección del Bean de Producción/Recetas
    @ManagedProperty(value = "#{produccionrecetasBean}")
    private produccion_recetasBean produccion_recetasBean;

    private final receta_insumosDao dao = new receta_insumosDao();
    private final recetasDao rDao = new recetasDao();
    private final insumosDao iDao = new insumosDao();

    private receta_insumos recetaInsumo = new receta_insumos();
    private List<receta_insumos> lstRecetaInsumos;
    private List<recetas> lstRecetas;
    private List<insumos> lstInsumos;

    private recetas receta; // Receta actual seleccionada
    private int recetaSeleccionadaId; // ID de receta seleccionada desde producción

    // 🔹 Init automático al cargar la página
    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();

        // 1️⃣ Recuperar producción seleccionada desde la sesión
        produccion prod = (produccion) fc.getExternalContext()
                .getSessionMap().get("produccionSeleccionada");

        if (prod != null && produccion_recetasBean != null) {
            // 2️⃣ Obtener receta asociada a la producción
            int idReceta = produccion_recetasBean.obtenerIdRecetaPorProduccion(prod.getId_proc());

            if (idReceta > 0) {
                this.recetaSeleccionadaId = idReceta;
                cargarInsumosProduccion(idReceta);
            } else {
                System.err.println("⚠️ La producción no tiene receta asociada.");
            }

            // 3️⃣ Limpiar producción de la sesión
            fc.getExternalContext().getSessionMap().remove("produccionSeleccionada");
        } else {
            // Modo edición normal: cargar receta desde sesión
            receta = (recetas) fc.getExternalContext().getSessionMap().get("recetaSeleccionada");
            if (receta != null) {
                this.recetaSeleccionadaId = receta.getId_rec();
                cargarInsumosProduccion(receta.getId_rec());
            }
        }

        // 4️⃣ Cargar listas generales
        cargarRecetas();
        cargarInsumos();

        // 5️⃣ Listar insumos si ya hay receta
        listar();
    }

    // 🔹 Listar los insumos asociados a la receta actual y actualizar sus estados
// 🔹 Listar los insumos asociados a la receta actual y actualizar sus estados
public void listar() {
    if (receta != null) {
        try {
            // 🔁 1️⃣ Sincroniza los estados de receta_insumos según los insumos
            dao.sincronizarEstadosPorInsumo();

            // 🔁 2️⃣ Sincroniza el estado general de las recetas (Activo/Inactivo)
            rDao.sincronizarEstadosRecetas();

            // 🔹 3️⃣ Obtiene los registros actualizados de receta_insumos para la receta actual
            lstRecetaInsumos = dao.buscarPorReceta(receta.getId_rec());

            // 🔹 4️⃣ Carga los objetos relacionados (receta e insumo)
            for (receta_insumos ri : lstRecetaInsumos) {
                ri.setReceta(rDao.obtenerPorId(ri.getId_rec()));
                ri.setInsumo(iDao.obtenerPorId(ri.getId_ins()));
            }

            // 🔄 5️⃣ Actualiza la tabla en la vista JSF
            PrimeFaces.current().ajax().update("formRecetaInsumos:tablaRecetaInsumos");

            System.out.println("✅ Listado actualizado y estados sincronizados para la receta: " + receta.getNombre());

        } catch (Exception e) {
            System.err.println("⚠️ Error al listar receta_insumos: " + e.getMessage());
            e.printStackTrace();
        }
    } else {
        System.err.println("⚠️ No hay receta seleccionada para listar sus insumos.");
    }
}




    // 🔹 Cargar todas las recetas (para uso en selectOneMenu si se requiere)
    private void cargarRecetas() {
        lstRecetas = rDao.listar();
    }

    // 🔹 Cargar insumos activos disponibles
    private void cargarInsumos() {
        lstInsumos = iDao.listarInsumosActivos();
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
               dao.sincronizarEstadosPorInsumo(); // 🔁 sincroniza después de agregar
            rDao.actualizarEstado(receta.getId_rec());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Insumo agregado correctamente"));
            listar();
            limpiar();
            PrimeFaces.current().ajax().update("formRecetaInsumos:tablaRecetaInsumos", "formRecetaInsumos:msj");
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo agregar el registro"));
        }
    }

    // ✏️ Cargar datos para edición
    public void editar(receta_insumos ri) {
        this.recetaInsumo = dao.obtenerPorId(ri.getId_rec_ins());
        this.recetaInsumo.setReceta(rDao.obtenerPorId(recetaInsumo.getId_rec()));
        this.recetaInsumo.setInsumo(iDao.obtenerPorId(recetaInsumo.getId_ins()));
    }

    // 💾 Actualizar registro
    public void actualizar() {
        if (dao.actualizar(recetaInsumo)) {
               dao.sincronizarEstadosPorInsumo(); // 🔁 sincroniza después de agregar
            rDao.actualizarEstado(receta.getId_rec());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizado", "Registro modificado correctamente"));
            listar();
            limpiar();
            PrimeFaces.current().ajax().update("formRecetaInsumos:tablaRecetaInsumos", "formRecetaInsumos:msj");
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo actualizar el registro"));
        }
    }

    // ❌ Eliminar registro
    public void eliminar(receta_insumos ri) {
        dao.eliminar(ri);
         dao.sincronizarEstadosPorInsumo(); // 🔁 sincroniza después de eliminar
        rDao.actualizarEstado(receta.getId_rec());
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminado", "Registro eliminado correctamente"));
        listar();
        PrimeFaces.current().ajax().update("formRecetaInsumos:tablaRecetaInsumos", "formRecetaInsumos:msj");
    }

    // 🔍 Buscar insumos por receta específica
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

    // 🔁 Actualizar automáticamente la unidad de medida al seleccionar un insumo
   public void actualizarUnidad() {
    if (recetaInsumo != null && lstInsumos != null && recetaInsumo.getId_ins() > 0) {
        for (insumos i : lstInsumos) {
            if (i.getId_ins() == recetaInsumo.getId_ins()) {
                recetaInsumo.setUnidad(i.getUnidad_medida());
                return;
            }
        }
    } else {
        recetaInsumo.setUnidad("");
    }
}
   
   
   public void cargarInsumosDesdeProduccion(int idProduccion) {
    // 1️⃣ Obtener la receta asociada a la producción
    int idReceta = produccion_recetasBean.obtenerIdRecetaPorProduccion(idProduccion);
    
    if(idReceta <= 0) {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "La producción no tiene receta asociada"));
        return;
    }

    // 2️⃣ Guardar el ID de receta seleccionada
    this.recetaSeleccionadaId = idReceta;

    // 3️⃣ Cargar los insumos de esa receta
    cargarInsumosProduccion(idReceta);
}


// 🔍 Cargar insumos de una receta para vista de Producción (solo lectura)
public void cargarInsumosProduccion(int idReceta) {
        receta = rDao.obtenerPorId(idReceta);
        lstRecetaInsumos = dao.buscarPorReceta(idReceta);

        for (receta_insumos ri : lstRecetaInsumos) {
            ri.setReceta(rDao.obtenerPorId(ri.getId_rec()));
            ri.setInsumo(iDao.obtenerPorId(ri.getId_ins()));
        }

        if (lstRecetaInsumos.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Sin resultados",
                            "Esta receta no tiene insumos asignados"));
        }

        // Actualizar tabla de insumos en la vista
        PrimeFaces.current().ajax().update("formProduccionLectura:tablaRecetaInsumos");
    }




public void seleccionarReceta(int idReceta) {
    this.recetaSeleccionadaId = idReceta;
    cargarInsumosProduccion(idReceta);
}



    // 🧹 Limpiar formulario
    public void limpiar() {
        recetaInsumo = new receta_insumos();
    }

    // 🔄 Refrescar la lista completa (para botón “Recargar” o al volver)
    public void refrescar() {
        cargarInsumos();
        listar();
        PrimeFaces.current().ajax().update("formRecetaInsumos:tablaRecetaInsumos", "formRecetaInsumos:msj");
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
    
    public void setProduccion_recetasBean(produccion_recetasBean produccion_recetasBean) {
        this.produccion_recetasBean = produccion_recetasBean;
    }
    
    public produccion_recetasBean getProduccion_recetasBean() {
    return produccion_recetasBean;
}

}
