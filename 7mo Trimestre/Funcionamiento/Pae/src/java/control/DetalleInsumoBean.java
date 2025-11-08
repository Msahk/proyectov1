package control;

import dao.detalle_insumoDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import modelo.detalle_insumo;
import modelo.historial;
import dao.historialDao;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped; // ✅ Esto es para JSF antiguo

@ManagedBean
@ViewScoped
public class DetalleInsumoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private final detalle_insumoDao dao = new detalle_insumoDao();
    private detalle_insumo detalle = new detalle_insumo();       // Lote actual (para agregar/editar)
    private List<detalle_insumo> lstDetalle = new ArrayList<>();  // Lotes mostrados en la tabla
    private List<detalle_insumo> lstDetalleFiltrado;
    private detalle_insumo loteAEliminar;
    private String motivoEliminacion;
    private int insumoSeleccionadoId;  // ID del insumo seleccionado para ver sus lotes
    private List<historial> lstHistorial;
    private historialDao historialDao = new historialDao();
    private List<detalle_insumo> lstLotesEliminados = new ArrayList<>();

    @PostConstruct
    public void init() {
        Map<String, String> params = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap();
        String id = params.get("idInsumo");
        if (id != null) {
            insumoSeleccionadoId = Integer.parseInt(id);  // ⚡ Asignar al campo
            cargarLotesPorInsumo(insumoSeleccionadoId);  // Cargar lotes
            cargarHistorial(insumoSeleccionadoId);       // Cargar historial general
        } else {
            lstDetalle = new ArrayList<>();
            lstHistorial = new ArrayList<>();
        }
    }

    public void cargarLotesPorInsumo(int id_insumo) {
        this.insumoSeleccionadoId = id_insumo;

        // Solo lotes que NO estén eliminados
        lstDetalle = dao.listarPorInsumoYEstado(id_insumo, "Activo,Vencido");

        detalle = new detalle_insumo(); // Limpiar formulario
    }

    public void agregar() {
    detalle.setId_ins(insumoSeleccionadoId);  // ⚡ Asignar el insumo

    // 🔹 Calcular estado automáticamente antes de agregar
    detalle.setEstado(dao.calcularEstado(detalle));

    boolean ok = dao.agregar(detalle);
    if (ok) {
        // ➕ Solo actualizar stock si el lote está activo
        if ("Activo".equalsIgnoreCase(detalle.getEstado())) {
            dao.actualizarStock(detalle.getId_ins(), detalle.getCantidad());
        }

        // ✅ Registrar historial como "Entrada"
        historial h = new historial();
        h.setAccion("Entrada"); // valor válido
        h.setFecha(new Date());
        h.setId_ins(detalle.getId_ins());
        h.setId_detalle(detalle.getId_detalle());
        h.setNovedad("Se agregó lote de " + detalle.getCantidad() + " unidades");
        historialDao.agregar(h);

        cargarLotesPorInsumo(insumoSeleccionadoId); // Refrescar lista
        limpiar();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Lote agregado correctamente"));
    } else {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo agregar el lote"));
    }
}


    // Cargar solo lotes eliminados de un insumo
    public void cargarEliminados() {
    lstLotesEliminados = dao.listarPorInsumoYEstado(insumoSeleccionadoId, "Eliminado");
}
    
    

    public void prepararAgregar() {
        detalle = new detalle_insumo();
        detalle.setId_ins(insumoSeleccionadoId); // Asignar insumo automáticamente
    }

    public void editar(detalle_insumo d) {
        detalle = dao.obtenerPorId(d.getId_detalle());
    }

    public void actualizar() {
        boolean ok = dao.actualizar(detalle);
        if (ok) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Detalle actualizado correctamente"));
            cargarLotesPorInsumo(insumoSeleccionadoId);
            limpiar();
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo actualizar"));
        }
    }

    public void eliminar(detalle_insumo d) {
        boolean ok = dao.eliminar(d);
        if (ok) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Eliminado", "Detalle eliminado"));
            cargarLotesPorInsumo(insumoSeleccionadoId);
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar"));
        }
    }

    public void eliminarLoteVencido(detalle_insumo lote) {
        Date hoy = new Date();
        if (lote.getFecha_vencimiento() != null && lote.getFecha_vencimiento().before(hoy)) {
            boolean eliminado = dao.eliminar(lote);
            if (eliminado) {
                historial h = new historial();
                h.setFecha(new Date());
                h.setAccion("Eliminación");
                h.setNovedad("Se eliminó lote vencido de " + lote.getCantidad()
                        + " unidades del insumo '" + lote.getNombre_insumo() + "'");
                h.setId_ins(lote.getId_ins());
                h.setId_detalle(lote.getId_detalle());
                new historialDao().agregar(h);

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Lote eliminado", "Lote vencido eliminado y registrado en historial"));
                cargarLotesPorInsumo(insumoSeleccionadoId);
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar el lote vencido"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "El lote no está vencido"));
        }
    }

    // Preparar eliminación (abre diálogo)
    public void prepararEliminar(detalle_insumo lote) {
        this.loteAEliminar = lote;
        this.motivoEliminacion = ""; // limpiar el campo de motivo
    }

// Marcar lote como eliminado con motivo
// Marcar lote como eliminado con motivo
public void eliminarConMotivo() {
    if (loteAEliminar == null || motivoEliminacion == null || motivoEliminacion.trim().isEmpty()) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe ingresar un motivo para eliminar"));
        return;
    }

    // Registrar en historial antes de marcar como eliminado
    historial h = new historial();
    h.setFecha(new Date());
    h.setAccion("Salida"); // ⚡ Valor válido para la columna ENUM
    h.setNovedad(motivoEliminacion);
    h.setId_ins(loteAEliminar.getId_ins());
    h.setId_detalle(loteAEliminar.getId_detalle());

    boolean okHistorial = historialDao.agregar(h);
    if (!okHistorial) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo registrar la eliminación en historial"));
        return;
    }

    // ⚡ Guardar estado actual antes de cambiarlo
    String estadoAnterior = loteAEliminar.getEstado();

    // Cambiar estado del lote a "Eliminado" en lugar de eliminarlo físicamente
    loteAEliminar.setEstado("Eliminado");
    boolean okActualizar = dao.actualizar(loteAEliminar); // Usar método actualizar
    if (okActualizar) {
        // ⚡ Restar la cantidad del lote eliminado del stock solo si estaba activo
        if ("Activo".equalsIgnoreCase(estadoAnterior)) {
            dao.actualizarStock(loteAEliminar.getId_ins(), -loteAEliminar.getCantidad());
        }

        // ⚡ Recargar historial y tabla de lotes activos
        cargarHistorial(loteAEliminar.getId_ins());
        cargarLotesPorInsumo(insumoSeleccionadoId);

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, "Lote eliminado",
                        "El lote fue marcado como eliminado, registrado en historial y descontado del stock si estaba activo"));

        // Limpiar variables temporales
        loteAEliminar = null;
        motivoEliminacion = "";
    } else {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo marcar el lote como eliminado"));
    }
}


// Cargar historial de un insumo
    public void cargarHistorial(int id_ins) {
        lstHistorial = historialDao.listarPorInsumo(id_ins); // necesitas implementar este método en historialDao
    }

    public void limpiar() {
        detalle = new detalle_insumo();
    }

    // ---------------- Getters y Setters ----------------
    public detalle_insumo getDetalle() {
        return detalle;
    }

    public void setDetalle(detalle_insumo detalle) {
        this.detalle = detalle;
    }

    public List<detalle_insumo> getLstDetalle() {
        return lstDetalle;
    }

    public void setLstDetalle(List<detalle_insumo> lstDetalle) {
        this.lstDetalle = lstDetalle;
    }

    public List<detalle_insumo> getLstDetalleFiltrado() {
        return lstDetalleFiltrado;
    }

    public void setLstDetalleFiltrado(List<detalle_insumo> lstDetalleFiltrado) {
        this.lstDetalleFiltrado = lstDetalleFiltrado;
    }

    public int getInsumoSeleccionadoId() {
        return insumoSeleccionadoId;
    }

    public void setInsumoSeleccionadoId(int insumoSeleccionadoId) {
        this.insumoSeleccionadoId = insumoSeleccionadoId;
    }

    public List<historial> getLstHistorial() {
        return lstHistorial;
    }

    public detalle_insumo getLoteAEliminar() {
        return loteAEliminar;
    }

    public void setLoteAEliminar(detalle_insumo loteAEliminar) {
        this.loteAEliminar = loteAEliminar;
    }

    public String getMotivoEliminacion() {
        return motivoEliminacion;
    }

    public void setMotivoEliminacion(String motivoEliminacion) {
        this.motivoEliminacion = motivoEliminacion;
    }
    
    // Getter y Setter
public List<detalle_insumo> getLstLotesEliminados() {
    return lstLotesEliminados;
}

public void setLstLotesEliminados(List<detalle_insumo> lstLotesEliminados) {
    this.lstLotesEliminados = lstLotesEliminados;
}
}
