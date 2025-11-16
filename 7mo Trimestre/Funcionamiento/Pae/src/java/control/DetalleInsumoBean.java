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
import java.util.Arrays;

@ManagedBean
@ViewScoped
public class DetalleInsumoBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String motivoEdicion;
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
            String estadoHistorial = "Ingreso de lote"; // Estado para el historial de agregar

            registrarHistorial(
                    "Entrada",
                    "Se agregó lote de " + detalle.getCantidad() + " unidades",
                    detalle,
                    (int) detalle.getCantidad(),
                    estadoHistorial
            );

            actualizarStockYEstado(insumoSeleccionadoId);
            sincronizarRecetasPorInsumo(insumoSeleccionadoId);
            cargarLotesPorInsumo(insumoSeleccionadoId);
            cargarHistorial(insumoSeleccionadoId);
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
         motivoEdicion = ""; 
    }

    public void actualizar() {
    if (motivoEdicion == null || motivoEdicion.trim().isEmpty()) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe ingresar un motivo de edición."));
        return;
    }

    boolean ok = dao.actualizar(detalle);
    if (ok) {
        registrarHistorial(
                "Edición",
                motivoEdicion, // aquí va el motivo que ingresa el usuario
                detalle,
                (int) detalle.getCantidad(),
                "Edición"
        );

        actualizarStockYEstado(detalle.getId_ins());
        sincronizarRecetasPorInsumo(insumoSeleccionadoId);
        cargarLotesPorInsumo(insumoSeleccionadoId);

        motivoEdicion = null; // limpiar
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

        String estadoAnterior = loteAEliminar.getEstado();

        // Asignar el estado para el historial según estado del lote
        String estadoHistorial = "Activo".equalsIgnoreCase(estadoAnterior) ? "Eliminado"
                : "Vencido".equalsIgnoreCase(estadoAnterior) ? "Vencimiento"
                : "Eliminado"; // default

        loteAEliminar.setEstado("Eliminado"); // Esto sigue siendo el estado del lote en tabla detalle_insumo
        boolean okActualizar = dao.actualizar(loteAEliminar);

        if (okActualizar) {
            if ("Activo".equalsIgnoreCase(estadoAnterior)) {
                dao.actualizarStock(loteAEliminar.getId_ins(), -loteAEliminar.getCantidad());
            }

            // Registrar historial con estado correcto
            registrarHistorial("Salida",
                    motivoEliminacion,
                    loteAEliminar,
                    (int) loteAEliminar.getCantidad(),
                    estadoHistorial);

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
    public void descontarDeLotes(int cantidad) {
        List<detalle_insumo> lotes = dao.listarPorInsumoYEstado(insumoSeleccionadoId, "Activo");
        int restante = cantidad;

        for (detalle_insumo lote : lotes) {
            if (restante <= 0) {
                break;
            }

            // Calcular cuánto descontar del lote actual
            int aDescontar = (int) Math.min(restante, lote.getCantidad());

            // Actualizar la cantidad del lote
            lote.setCantidad(lote.getCantidad() - aDescontar);
            dao.actualizar(lote);

            // Registrar la salida en historial **por cada lote descontado**
            String estadoHistorial = "Producción"; // Para este tipo de salida

            registrarHistorial(
                    "Salida",
                    "Se descontaron " + aDescontar + " unidades para producción",
                    lote,
                    aDescontar,
                    estadoHistorial
            );

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

    private void registrarHistorial(String accion, String novedad, detalle_insumo lote, int cantidad, String estadoHistorial) {
        try {
            historial h = new historial();
            h.setFecha(new Date());
            h.setAccion(accion);
            h.setId_ins(lote.getId_ins());
            h.setId_detalle(lote.getId_detalle());
            h.setNovedad(novedad);
            h.setCantidad(cantidad);

            // ============================
            // VALIDAR ESTADO PERMITIDO
            // ============================
            List<String> estadosValidos = Arrays.asList(
                    "Producción",
                    "Vencimiento",
                    "Edición",
                    "Ingreso de lote",
                    "Eliminado"
            );

            if (!estadosValidos.contains(estadoHistorial)) {
                System.out.println("⚠ Estado inválido recibido: " + estadoHistorial + " → se cambia a 'Ingreso de lote'");
                estadoHistorial = "Ingreso de lote";
            }

            h.setEstado(estadoHistorial);

            // ============================
            // CALCULAR STOCK
            // ============================
            int stockActual = (int) dao.calcularStockActual(lote.getId_ins());

            if ("Entrada".equalsIgnoreCase(accion)) {
                stockActual += cantidad;
            } else if ("Salida".equalsIgnoreCase(accion)) {
                stockActual -= cantidad;
                if (stockActual < 0) {
                    stockActual = 0;
                }
            }

            h.setStockActual(stockActual);

            // ============================
            // LOGS
            // ============================
            System.out.println("=== Registrando historial ===");
            System.out.println("Accion: " + accion);
            System.out.println("Novedad: " + novedad);
            System.out.println("Id_ins: " + lote.getId_ins());
            System.out.println("Id_detalle: " + lote.getId_detalle());
            System.out.println("Estado FINAL (validado): " + estadoHistorial);
            System.out.println("Cantidad: " + cantidad);
            System.out.println("Stock calculado: " + stockActual);

            // ============================
            // INSERTAR
            // ============================
            boolean agregado = historialDao.agregar(h);
            System.out.println("Historial agregado: " + agregado);

        } catch (Exception e) {
            System.err.println("Error al registrar historial: " + e.getMessage());
            e.printStackTrace();
        }
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

    public List<detalle_insumo> getLstLotesEliminados() {
        return lstLotesEliminados;
    }

    public void setLstLotesEliminados(List<detalle_insumo> lstLotesEliminados) {
        this.lstLotesEliminados = lstLotesEliminados;
    }

    // Getter
    public String getMotivoEdicion() {
        return motivoEdicion;
    }

// Setter
    public void setMotivoEdicion(String motivoEdicion) {
        this.motivoEdicion = motivoEdicion;
    }

}
