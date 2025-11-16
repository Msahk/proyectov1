package control;

import dao.detalle_insumoDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import modelo.detalle_insumo;
import modelo.historial;
import dao.historialDao;
import dao.receta_insumosDao;
import dao.recetasDao;



@ManagedBean
@ViewScoped
public class DetalleInsumoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private final detalle_insumoDao dao = new detalle_insumoDao();
    private detalle_insumo detalle = new detalle_insumo();
    private List<detalle_insumo> lstDetalle = new ArrayList<>();
    private List<detalle_insumo> lstDetalleFiltrado;
    private detalle_insumo loteAEliminar;
    private String motivoEliminacion;
    private int insumoSeleccionadoId;
    private List<historial> lstHistorial;
    private historialDao historialDao = new historialDao();
    private List<detalle_insumo> lstLotesEliminados = new ArrayList<>();

    @ManagedProperty("#{insumosBean}")
    private insumosBean insumosBean;

    public void setInsumosBean(insumosBean insumosBean) {
        this.insumosBean = insumosBean;
    }

    @PostConstruct
    public void init() {
        Map<String, String> params = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap();
        String id = params.get("idInsumo");
        if (id != null) {
            insumoSeleccionadoId = Integer.parseInt(id);
            cargarLotesPorInsumo(insumoSeleccionadoId);
            cargarHistorial(insumoSeleccionadoId);
        } else {
            lstDetalle = new ArrayList<>();
            lstHistorial = new ArrayList<>();
        }
    }

    public void cargarLotesPorInsumo(int id_insumo) {
        this.insumoSeleccionadoId = id_insumo;
        lstDetalle = dao.listarPorInsumoYEstado(id_insumo, "Activo,Vencido");
        detalle = new detalle_insumo();
    }

    public void cargarEliminados() {
        lstLotesEliminados = dao.listarPorInsumoYEstado(insumoSeleccionadoId, "Eliminado");
    }

    // 🔹 Método central para actualizar stock y estado del insumo
    private void actualizarStockYEstado(int id_insumo) {
        double stockActual = dao.calcularStockActual(id_insumo);
        String nuevoEstado = stockActual > 0 ? "Activo" : "Stock insuficiente";
        dao.actualizarEstadoInsumo(id_insumo, nuevoEstado); // Implementar en DAO
        insumosBean.listar(); // Refrescar vista de insumos
    }

    public void prepararAgregar() {
        detalle = new detalle_insumo();
        detalle.setId_ins(insumoSeleccionadoId);
    }

    public void agregar() {
        detalle.setId_ins(insumoSeleccionadoId);
        detalle.setEstado(dao.calcularEstado(detalle));

        boolean ok = dao.agregar(detalle);
        if (ok) {
            historial h = new historial();
            h.setAccion("Entrada");
            h.setFecha(new Date());
            h.setId_ins(detalle.getId_ins());
            h.setId_detalle(detalle.getId_detalle());
            h.setNovedad("Se agregó lote de " + detalle.getCantidad() + " unidades");
            historialDao.agregar(h);

            actualizarStockYEstado(insumoSeleccionadoId);
            sincronizarRecetasPorInsumo(insumoSeleccionadoId);
            cargarLotesPorInsumo(insumoSeleccionadoId);
            limpiar();

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Lote agregado correctamente"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo agregar el lote"));
        }
    }

    public void editar(detalle_insumo d) {
        detalle = dao.obtenerPorId(d.getId_detalle());
    }

    public void actualizar() {
        boolean ok = dao.actualizar(detalle);
        if (ok) {
            actualizarStockYEstado(detalle.getId_ins());
            sincronizarRecetasPorInsumo(insumoSeleccionadoId);
            cargarLotesPorInsumo(insumoSeleccionadoId);
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Detalle actualizado correctamente"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo actualizar"));
        }
    }

    public void prepararEliminar(detalle_insumo lote) {
        this.loteAEliminar = lote;
        this.motivoEliminacion = "";
    }

    public void eliminarConMotivo() {
        if (loteAEliminar == null || motivoEliminacion == null || motivoEliminacion.trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe ingresar un motivo para eliminar"));
            return;
        }

        historial h = new historial();
        h.setFecha(new Date());
        h.setAccion("Salida");
        h.setNovedad(motivoEliminacion);
        h.setId_ins(loteAEliminar.getId_ins());
        h.setId_detalle(loteAEliminar.getId_detalle());

        boolean okHistorial = historialDao.agregar(h);
        if (!okHistorial) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo registrar la eliminación en historial"));
            return;
        }

        String estadoAnterior = loteAEliminar.getEstado();
        loteAEliminar.setEstado("Eliminado");
        boolean okActualizar = dao.actualizar(loteAEliminar);

        if (okActualizar) {
            if ("Activo".equalsIgnoreCase(estadoAnterior)) {
                dao.actualizarStock(loteAEliminar.getId_ins(), -loteAEliminar.getCantidad());
            }

            actualizarStockYEstado(loteAEliminar.getId_ins());
            sincronizarRecetasPorInsumo(insumoSeleccionadoId);
            cargarHistorial(loteAEliminar.getId_ins());
            cargarLotesPorInsumo(insumoSeleccionadoId);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Lote eliminado",
                            "El lote fue marcado como eliminado, registrado en historial y descontado del stock si estaba activo"));

            loteAEliminar = null;
            motivoEliminacion = "";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo marcar el lote como eliminado"));
        }
    }

    // 🔹 Nuevo método para descontar stock de varios lotes (por producción, por ejemplo)
    // 🔹 Nuevo método para descontar stock de varios lotes (por producción, por ejemplo)
public void descontarDeLotes(int cantidad) {
    // Traer todos los lotes activos del insumo seleccionado
    List<detalle_insumo> lotes = dao.listarPorInsumoYEstado(insumoSeleccionadoId, "Activo");
    int restante = cantidad;

    for (detalle_insumo lote : lotes) {
        if (restante <= 0) break;

        // Calcular cuánto descontar del lote actual
        int aDescontar = (int) Math.min(restante, lote.getCantidad());

        // Actualizar la cantidad del lote
        lote.setCantidad(lote.getCantidad() - aDescontar);
        dao.actualizar(lote);

        // Registrar la salida en historial
        historial h = new historial();
        h.setFecha(new Date());
        h.setAccion("Salida");
        h.setId_ins(lote.getId_ins());
        h.setId_detalle(lote.getId_detalle());
        h.setNovedad("Se descontaron " + aDescontar + " unidades para producción");
        historialDao.agregar(h);

        // Reducir la cantidad restante por descontar
        restante -= aDescontar;
    }

    // Actualizar stock y estado del insumo
    actualizarStockYEstado(insumoSeleccionadoId);
    sincronizarRecetasPorInsumo(insumoSeleccionadoId);

    // Refrescar la lista de lotes
    cargarLotesPorInsumo(insumoSeleccionadoId);

    FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito",
                    "Se descontaron " + cantidad + " unidades de los lotes activos"));
}


    public void cargarHistorial(int id_ins) {
        lstHistorial = historialDao.listarPorInsumo(id_ins);
    }

    public void limpiar() {
        detalle = new detalle_insumo();
    }
    
    // 🔹 Método para sincronizar los estados de receta_insumos después de actualizar stock
private void sincronizarRecetasPorInsumo(int id_insumo) {
    // 1️⃣ Actualiza todos los receta_insumos según el stock/estado del insumo
    receta_insumosDao riDao = new receta_insumosDao();
    riDao.sincronizarEstadosPorInsumo();

    // 2️⃣ Actualiza el estado de todas las recetas afectadas
    recetasDao rDao = new recetasDao();
    rDao.sincronizarEstadosRecetas();
}


    // ---------------- Getters y Setters ----------------
    public detalle_insumo getDetalle() { return detalle; }
    public void setDetalle(detalle_insumo detalle) { this.detalle = detalle; }
    public List<detalle_insumo> getLstDetalle() { return lstDetalle; }
    public void setLstDetalle(List<detalle_insumo> lstDetalle) { this.lstDetalle = lstDetalle; }
    public List<detalle_insumo> getLstDetalleFiltrado() { return lstDetalleFiltrado; }
    public void setLstDetalleFiltrado(List<detalle_insumo> lstDetalleFiltrado) { this.lstDetalleFiltrado = lstDetalleFiltrado; }
    public int getInsumoSeleccionadoId() { return insumoSeleccionadoId; }
    public void setInsumoSeleccionadoId(int insumoSeleccionadoId) { this.insumoSeleccionadoId = insumoSeleccionadoId; }
    public List<historial> getLstHistorial() { return lstHistorial; }
    public detalle_insumo getLoteAEliminar() { return loteAEliminar; }
    public void setLoteAEliminar(detalle_insumo loteAEliminar) { this.loteAEliminar = loteAEliminar; }
    public String getMotivoEliminacion() { return motivoEliminacion; }
    public void setMotivoEliminacion(String motivoEliminacion) { this.motivoEliminacion = motivoEliminacion; }
    public List<detalle_insumo> getLstLotesEliminados() { return lstLotesEliminados; }
    public void setLstLotesEliminados(List<detalle_insumo> lstLotesEliminados) { this.lstLotesEliminados = lstLotesEliminados; }

}
