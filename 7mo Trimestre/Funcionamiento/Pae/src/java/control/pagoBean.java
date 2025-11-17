package control;

import dao.*;
import modelo.*;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

@ManagedBean(name = "pagoBean")
@ViewScoped
public class pagoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    // DAOs
    private pagoDao pagoDao;
    private ventasDao ventasDao;
    private produccionDao produccionDao;
    private venta_produccionDao ventaProduccionDao;
    private produccion_recetasDao produccionRecetasDao;
    private venta_recetasDao ventaRecetasDao; // ✅ agregado

    // Variables de control
    private pago pagoNuevo;
    private ventas ventaSeleccionada;
    private List<venta_recetas> recetasVenta; // ✅ ahora usamos esta lista
    private List<pago> listaPagos;
    private double totalPagado;
    private int idVenta;
    private Date fechaActual = new Date();
    private pago pagoSeleccionado;

    @PostConstruct
    public void init() {
        pagoDao = new pagoDao();
        ventasDao = new ventasDao();
        produccionDao = new produccionDao();
        ventaProduccionDao = new venta_produccionDao();
        produccionRecetasDao = new produccion_recetasDao();
        ventaRecetasDao = new venta_recetasDao(); // ✅ inicializado

        pagoNuevo = new pago();
        listaPagos = new ArrayList<>();
        recetasVenta = new ArrayList<>();
    }

    // 🔹 Seleccionar una venta
    public void seleccionarVenta(ventas venta) {
        this.ventaSeleccionada = venta;
        this.recetasVenta = ventaRecetasDao.listarPorVenta(venta.getIdVen());
        this.listaPagos = pagoDao.listarPorVenta(venta.getIdVen());
        this.totalPagado = pagoDao.totalPagosVenta(venta.getIdVen());
        this.pagoNuevo = new pago();
    }

    // 🔹 Registrar pago
    public void registrarPago() {
        try {
            if (ventaSeleccionada == null) {
                mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error", "No hay venta seleccionada.");
                return;
            }

            if (pagoNuevo.getMonto() <= 0) {
                mostrarMensaje(FacesMessage.SEVERITY_WARN, "Monto inválido", "El monto del pago debe ser mayor a 0");
                return;
            }

            double saldoPendiente = ventaSeleccionada.getTotal() - totalPagado;
            if (pagoNuevo.getMonto() > saldoPendiente) {
                mostrarMensaje(FacesMessage.SEVERITY_WARN, "Monto excede saldo", "El pago no puede ser mayor al saldo pendiente");
                return;
            }

            // Registrar pago
            pagoNuevo.setIdVenta(ventaSeleccionada.getIdVen());
            pagoNuevo.setFechaPago(new Date());
            int idGenerado = pagoDao.agregar(pagoNuevo);

            if (idGenerado <= 0) {
                mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo registrar el pago");
                return;
            }

            // Recalcular totales
            listaPagos = pagoDao.listarPorVenta(ventaSeleccionada.getIdVen());
            totalPagado = pagoDao.totalPagosVenta(ventaSeleccionada.getIdVen());

            // Actualizar estado venta
            if (totalPagado <= 0) {
                ventaSeleccionada.setEstado("Pago pendiente");
            } else if (totalPagado < ventaSeleccionada.getTotal()) {
                ventaSeleccionada.setEstado("Procesando");
            } else {
                ventaSeleccionada.setEstado("Pago completo");
            }
            ventasDao.actualizar(ventaSeleccionada);

            // ✅ Generar producción solo si está pago completo
            if (totalPagado >= ventaSeleccionada.getTotal()) {
                generarProduccion();
                mostrarMensaje(FacesMessage.SEVERITY_INFO, "Éxito", "Pago completo recibido. Producción generada.");
            } else {
                mostrarMensaje(FacesMessage.SEVERITY_INFO, "Éxito", "Pago registrado correctamente. Estado actualizado.");
            }

            pagoNuevo = new pago();

        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error", "Error al registrar pago: " + e.getMessage());
        }
    }

    // 🔹 Generar producción con recetas
private void generarProduccion() throws Exception {
    System.out.println("🧩 Iniciando generación de producción para venta ID: " + ventaSeleccionada.getIdVen());

    // 🔎 Verificar si ya existe producción asociada
    if (ventaProduccionDao.existePorVenta(ventaSeleccionada.getIdVen())) {
        System.out.println("⚠️ Ya existe una producción para esta venta. No se generará otra.");
        return;
    }

    // 🏭 Crear producción
    produccion prod = new produccion();
    prod.setFecha_hora(new java.sql.Timestamp(System.currentTimeMillis()));
    prod.setEstado("Pendiente");

    // ✅ Usuario que creó la venta
    prod.setId_usu(ventaSeleccionada.getIdUsuario());
    prod.setNombreUsuario(ventaSeleccionada.getNombreUsuario() + " " + ventaSeleccionada.getApellidoUsuario());

    // ✅ Usuario asignado (Empleado de producción)
    prod.setId_asignado(ventaSeleccionada.getIdAsignado());
    prod.setNombreAsignado(ventaSeleccionada.getNombreAsignado() + " " + ventaSeleccionada.getApellidoAsignado());

    // 💾 Insertar producción en la base de datos
    int idProduccion = produccionDao.agregar(prod);
    System.out.println("✅ Producción creada con ID: " + idProduccion
            + " por " + prod.getNombreUsuario()
            + " asignada a " + prod.getNombreAsignado());

    // 🔗 Asociar venta con producción
    venta_produccion vp = new venta_produccion();
    vp.setIdVenta(ventaSeleccionada.getIdVen());
    vp.setIdProduccion(idProduccion);
    ventaProduccionDao.agregar(vp);
    System.out.println("🔗 Asociación venta-producción creada correctamente.");

    // 🍳 Obtener recetas asociadas a la venta
    recetasVenta = ventaRecetasDao.listarPorVenta(ventaSeleccionada.getIdVen());
    System.out.println("🍳 Recetas encontradas en la venta: " + recetasVenta.size());

    if (recetasVenta.isEmpty()) {
        System.out.println("⚠️ No se encontraron recetas asociadas a la venta.");
        return;
    }

    // 📋 Crear registros en produccion_recetas
    for (venta_recetas vr : recetasVenta) {
        produccion_recetas pr = new produccion_recetas();
        pr.setId_produccion(idProduccion);
        pr.setId_rec(vr.getIdReceta());
        pr.setCantidad(vr.getCantidad());
        produccionRecetasDao.agregar(pr);
        System.out.println("✅ Receta agregada a producción: " + vr.getNombreReceta());
    }

    System.out.println("🎯 Generación de producción completada exitosamente para venta ID: " + ventaSeleccionada.getIdVen());
}



    // 🔹 Eliminar pago
    public void eliminarPago(pago p) {
        try {
            if (pagoDao.eliminar(p.getIdPago())) {
                mostrarMensaje(FacesMessage.SEVERITY_INFO, "Éxito", "Pago eliminado correctamente");
                listaPagos = pagoDao.listarPorVenta(ventaSeleccionada.getIdVen());
                totalPagado = pagoDao.totalPagosVenta(ventaSeleccionada.getIdVen());
            } else {
                mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar el pago");
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error", "Error al eliminar pago: " + e.getMessage());
        }
    }

    // 🔹 Cargar datos al entrar a la vista
    public void cargarPagosPorId() {
        try {
            System.out.println("✅ cargarPagosPorId() llamado con idVenta = " + idVenta);

            if (idVenta <= 0) {
                mostrarMensaje(FacesMessage.SEVERITY_WARN, "ID inválido", "No se recibió un ID de venta válido");
                return;
            }

            ventaSeleccionada = ventasDao.obtenerPorId(idVenta);
            if (ventaSeleccionada == null) {
                mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error", "No se encontró la venta con ID " + idVenta);
                return;
            }

            recetasVenta = ventaRecetasDao.listarPorVenta(idVenta);
            listaPagos = pagoDao.listarPorVenta(idVenta);
            totalPagado = pagoDao.totalPagosVenta(idVenta);

            pagoNuevo = new pago();
            pagoNuevo.setIdVenta(idVenta);
            pagoNuevo.setFechaPago(new Date());

            System.out.println("💰 Pagos cargados: " + listaPagos.size() + " | Total pagado: " + totalPagado);
            System.out.println("🍳 Recetas asociadas a la venta: " + recetasVenta.size());

        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error al cargar pagos", e.getMessage());
        }
    }

    // 🔹 Utilidades
    private void mostrarMensaje(FacesMessage.Severity tipo, String titulo, String detalle) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(tipo, titulo, detalle));
    }
    
    public void eliminarPagoAction(javax.faces.event.AjaxBehaviorEvent event) {
    try {
        eliminarPago(pagoSeleccionado);
    } catch (Exception e) {
        e.printStackTrace();
    }
}


    // Getters y Setters
    public int getIdVenta() { return idVenta; }
    public void setIdVenta(int idVenta) { this.idVenta = idVenta; }

    public pago getPagoNuevo() { return pagoNuevo; }
    public void setPagoNuevo(pago pagoNuevo) { this.pagoNuevo = pagoNuevo; }

    public ventas getVentaSeleccionada() { return ventaSeleccionada; }
    public void setVentaSeleccionada(ventas ventaSeleccionada) { this.ventaSeleccionada = ventaSeleccionada; }

    public List<pago> getListaPagos() { return listaPagos; }
    public void setListaPagos(List<pago> listaPagos) { this.listaPagos = listaPagos; }

    public double getTotalPagado() { return totalPagado; }
    public void setTotalPagado(double totalPagado) { this.totalPagado = totalPagado; }

    public pago getPagoSeleccionado() { return pagoSeleccionado; }
    public void setPagoSeleccionado(pago pagoSeleccionado) { this.pagoSeleccionado = pagoSeleccionado; }

    public List<venta_recetas> getRecetasVenta() { return recetasVenta; }
    public void setRecetasVenta(List<venta_recetas> recetasVenta) { this.recetasVenta = recetasVenta; }

    public String getFechaActual() {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(fechaActual);
    }
}