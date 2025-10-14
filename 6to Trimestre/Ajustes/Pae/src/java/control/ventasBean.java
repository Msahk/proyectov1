package control;

import dao.ventasDao;
import dao.usuariosDao;
import dao.clientesDao;
import dao.detalleVentaDao;
import dao.pedidosDao;
import dao.recetasDao;
import java.io.File;
import java.io.IOException;
import modelo.ventas;
import modelo.usuarios;
import modelo.clientes;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import javax.annotation.PostConstruct;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import javax.servlet.http.Part;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import modelo.DetalleVenta;
import modelo.recetas;
import dao.venta_recetasDao;
import dao.produccionDao;
import modelo.venta_recetas;
import modelo.produccion;
import modelo.venta_produccion;
import dao.venta_produccionDao;
import modelo.produccion_recetas;
import dao.produccion_recetasDao;

@ManagedBean(name = "ventasBean")
@SessionScoped
public class ventasBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<ventas> listaVentasFiltradas;
    private ventasDao ventasDao = new ventasDao();
    private usuariosDao usuariosDao = new usuariosDao();
    private clientesDao clienteDao = new clientesDao();
    private List<clientes> listaClientes = new ArrayList<>();
    private List<ventas> listaVentas = new ArrayList<>();
    private List<DetalleVenta> detallesVentaActual = new ArrayList<>();
    private ventas ventaNueva = new ventas();
    private ventas ventaSeleccionada;
    private boolean nuevoCliente = false;
    private Integer filtroIdVenta;
    private String filtroTipo;
    private String filtroCliente;
    private String filtroUsuario;
    private Double filtroTotalMin;
    private Double filtroTotalMax;
    private recetasDao recetasDao = new recetasDao();
    private String filtroEstado;
    private Date filtroFechaDesde;
    private Date filtroFechaHasta;
    private List<usuarios> listaUsuarios = new ArrayList<>();
    private clientes clienteNuevo = new clientes();
    private Part excelVentas;
    private List<DetalleVenta> detallesVenta = new ArrayList<>();
    private int recetaSeleccionada;
    private int cantidadEmpanada;
    private detalleVentaDao detalleVentaDao = new detalleVentaDao();
    private venta_recetasDao ventaRecetasDao = new venta_recetasDao();
    private produccionDao produccionDao = new produccionDao();
    private List<venta_recetas> listaVentaRecetas = new ArrayList<>();
    private venta_produccionDao ventaProduccionDao = new venta_produccionDao();
    private produccion_recetasDao produccionRecetasDao = new produccion_recetasDao();
    private List<recetas> listaRecetas = new ArrayList<>();

    public Part getExcelVentas() {
        return excelVentas;
    }

    public void setExcelVentas(Part excelVentas) {
        this.excelVentas = excelVentas;
    }

    public void exportarPDF() {
        try {
            String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/ventas.jasper");
            File jasper = new File(path);
            ventasDataSource vds = new ventasDataSource();

            JasperPrint jprint = JasperFillManager.fillReport(jasper.getPath(), null, vds);

            HttpServletResponse resp = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            resp.addHeader("Content-disposition", "attachment; filename=Ventas.pdf");

            try (ServletOutputStream stream = resp.getOutputStream()) {
                JasperExportManager.exportReportToPdfStream(jprint, stream);
                stream.flush();
            }

            FacesContext.getCurrentInstance().responseComplete();
        } catch (JRException | IOException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error creando reporte de ventas"));
            e.printStackTrace();
        }
    }

    public void migrarVentas() {
        try {
            Workbook libro = WorkbookFactory.create(excelVentas.getInputStream());
            Sheet hoja = libro.getSheetAt(0);
            Iterator<Row> itrFila = hoja.rowIterator();
            itrFila.next(); // Saltar cabecera

            while (itrFila.hasNext()) {
                Row fila = itrFila.next();
                Iterator<Cell> itrCelda = fila.cellIterator();
                ventas v = new ventas();
                int campo = 1;
                while (itrCelda.hasNext()) {
                    Cell celda = itrCelda.next();
                    switch (campo) {
                        case 1:
                            v.setTipo(celda.getRichStringCellValue().toString());
                            break;
                        case 2:
                            v.setFecha(celda.getDateCellValue());
                            break;
                        case 3:
                            v.setIdUsuario((int) celda.getNumericCellValue());
                            break;
                        case 4:
                            v.setIdCliente((int) celda.getNumericCellValue());
                            break;
                        case 5:
                            v.setTotal(celda.getNumericCellValue());
                            break;
                        case 6:
                            v.setEstado(celda.getRichStringCellValue().toString());
                            break;
                        case 7:
                            v.setObservaciones(celda.getRichStringCellValue().toString());
                            break;
                    }
                    campo++;
                }
                ventasDao.agregar(v); // Guarda la venta
            }
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Ventas migradas exitosamente"));
            cargarVentas();
        } catch (IOException | InvalidFormatException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Error migrando ventas"));
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void init() {
        ventaNueva = new ventas();
        ventaNueva.setFecha(new Date());
        cargarVentas();
        cargarUsuarios();
        cargarClientes(); // Asegúrate de cargar clientes si no lo haces ya
        cargarRecetas();  // 🔹 NUEVO método
    }
    
    private void cargarClientes() {
    listaClientes = clienteDao.listar();
}

    
    private void cargarRecetas() {
    try {
        listaRecetas = recetasDao.listarActivas();
        System.out.println("Recetas cargadas: " + listaRecetas.size());
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    public Integer getFiltroIdVenta() {
        return filtroIdVenta;
    }

    public void setFiltroIdVenta(Integer filtroIdVenta) {
        this.filtroIdVenta = filtroIdVenta;
    }

    public String getFiltroTipo() {
        return filtroTipo;
    }

    public void setFiltroTipo(String filtroTipo) {
        this.filtroTipo = filtroTipo;
    }

    public String getFiltroCliente() {
        return filtroCliente;
    }

    public void setFiltroCliente(String filtroCliente) {
        this.filtroCliente = filtroCliente;
    }

    public String getFiltroUsuario() {
        return filtroUsuario;
    }

    public void setFiltroUsuario(String filtroUsuario) {
        this.filtroUsuario = filtroUsuario;
    }

    public Double getFiltroTotalMin() {
        return filtroTotalMin;
    }

    public void setFiltroTotalMin(Double filtroTotalMin) {
        this.filtroTotalMin = filtroTotalMin;
    }

    public Double getFiltroTotalMax() {
        return filtroTotalMax;
    }

    public void setFiltroTotalMax(Double filtroTotalMax) {
        this.filtroTotalMax = filtroTotalMax;
    }

    public String getFiltroEstado() {
        return filtroEstado;
    }

    public void setFiltroEstado(String filtroEstado) {
        this.filtroEstado = filtroEstado;
    }

    public Date getFiltroFechaDesde() {
        return filtroFechaDesde;
    }

    public void setFiltroFechaDesde(Date filtroFechaDesde) {
        this.filtroFechaDesde = filtroFechaDesde;
    }

    public Date getFiltroFechaHasta() {
        return filtroFechaHasta;
    }

    public void setFiltroFechaHasta(Date filtroFechaHasta) {
        this.filtroFechaHasta = filtroFechaHasta;
    }

    public List<ventas> getListaVentas() {
        return listaVentas;
    }

    public void setListaVentas(List<ventas> listaVentas) {
        this.listaVentas = listaVentas;
    }

    public List<DetalleVenta> getDetallesVentaActual() {
        return detallesVentaActual;
    }

    public List<ventas> getListaVentasFiltradas() {
        return listaVentasFiltradas;
    }

    public void setListaVentasFiltradas(List<ventas> listaVentasFiltradas) {
        this.listaVentasFiltradas = listaVentasFiltradas;
    }

    public void filtrarVentas() {
        java.sql.Date sqlFechaDesde = filtroFechaDesde != null ? new java.sql.Date(filtroFechaDesde.getTime()) : null;
        java.sql.Date sqlFechaHasta = filtroFechaHasta != null ? new java.sql.Date(filtroFechaHasta.getTime()) : null;

        listaVentas = ventasDao.filtrarAvanzado(
                filtroIdVenta,
                filtroTipo,
                filtroCliente,
                filtroUsuario,
                filtroTotalMin,
                filtroTotalMax,
                filtroEstado,
                sqlFechaDesde,
                sqlFechaHasta
        );
    }

    // getters / setters para la parte de recetas/detalles
    public int getRecetaSeleccionada() {
        return recetaSeleccionada;
    }

    public void setRecetaSeleccionada(int recetaSeleccionada) {
        this.recetaSeleccionada = recetaSeleccionada;
    }

    public int getCantidadEmpanada() {
        return cantidadEmpanada;
    }

    public void setCantidadEmpanada(int cantidadEmpanada) {
        this.cantidadEmpanada = cantidadEmpanada;
    }

    public List<recetas> getListaRecetas() {
    return listaRecetas;
}

    public void setListaRecetas(List<recetas> listaRecetas) {
        this.listaRecetas = listaRecetas;
    }

    public List<DetalleVenta> getDetallesVenta() {
        return detallesVenta;
    }

    public void setDetallesVenta(List<DetalleVenta> detallesVenta) {
        this.detallesVenta = detallesVenta;
    }

    public void cargarVentas() {
        listaVentas = ventasDao.listar();
    }

    public boolean isNuevoCliente() {
        return nuevoCliente;
    }

    public void setNuevoCliente(boolean nuevoCliente) {
        this.nuevoCliente = nuevoCliente;
    }

    public List<clientes> getListaClientes() {
        if (listaClientes == null || listaClientes.isEmpty()) {
            listaClientes = clienteDao.listar();
        }
        return listaClientes;
    }

    public void setListaClientes(List<clientes> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public void cargarUsuarios() {
        listaUsuarios = usuariosDao.listar();
    }

    public List<usuarios> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<usuarios> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public clientes getClienteNuevo() {
        return clienteNuevo;
    }

    public void setClienteNuevo(clientes clienteNuevo) {
        this.clienteNuevo = clienteNuevo;
    }

    public ventas getVentaNueva() {
        return ventaNueva;
    }

    public void setVentaNueva(ventas ventaNueva) {
        this.ventaNueva = ventaNueva;
    }

    public ventas getVentaSeleccionada() {
        return ventaSeleccionada;
    }

    public void setVentaSeleccionada(ventas ventaSeleccionada) {
        this.ventaSeleccionada = ventaSeleccionada;
    }

    public String guardarVenta() {
        if (nuevoCliente) {
            int idClienteGenerado = clienteDao.agregar(clienteNuevo);
            if (idClienteGenerado > 0) {
                listaClientes = clienteDao.listar();
                ventaNueva.setIdCliente(idClienteGenerado);
                ventaNueva.setNombreCliente(clienteNuevo.getNombre());
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al registrar el nuevo cliente", null));
                return null;
            }
        }

        int idGenerado = ventasDao.agregar(ventaNueva);
        if (idGenerado > 0) {
            if ("pedido".equalsIgnoreCase(ventaNueva.getTipo())) {
                ventas v = ventasDao.obtenerPorId(idGenerado);
                if (v != null) {
                    dao.pedidosDao pedidosDao = new dao.pedidosDao();
                    if (!pedidosDao.existePedidoParaVenta(v.getIdVen())) {
                        modelo.pedidos nuevoPedido = new modelo.pedidos();
                        nuevoPedido.setIdVen(v.getIdVen());
                        nuevoPedido.setIdCliente(v.getIdCliente());
                        nuevoPedido.setNombreCliente(v.getNombreCliente());
                        nuevoPedido.setEstado("Pendiente");
                        nuevoPedido.setFechaEntrega(new java.util.Date());
                        nuevoPedido.setObservacionesPedido(v.getObservaciones());
                        pedidosDao.agregar(nuevoPedido);

                        control.pedidosBean pedidosBean = (control.pedidosBean) FacesContext.getCurrentInstance()
                                .getExternalContext().getSessionMap().get("pedidosBean");
                        if (pedidosBean != null) {
                            pedidosBean.cargarPedidos();
                        }
                    }
                }
            }
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Venta registrada correctamente"));
            ventaNueva = new ventas();
            ventaNueva.setFecha(new Date());
            cargarVentas();
            return "/views/Ventas/index.xhtml?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al registrar la venta", null));
            return null;
        }
    }

    public String actualizarVenta() {
        if (ventaSeleccionada != null) {
            ventas ventaAntes = ventasDao.obtenerPorId(ventaSeleccionada.getIdVen());
            boolean cambioADirectaAPedido = ventaAntes != null
                    && "directa".equalsIgnoreCase(ventaAntes.getTipo())
                    && "pedido".equalsIgnoreCase(ventaSeleccionada.getTipo());

            boolean actualizado;

            if ("pedido".equalsIgnoreCase(ventaSeleccionada.getTipo())) {
                actualizado = ventasDao.actualizarTipoVenta(ventaSeleccionada);
            } else {
                actualizado = ventasDao.actualizar(ventaSeleccionada);
            }

            if (actualizado) {
                if (cambioADirectaAPedido) {
                    dao.pedidosDao pedidosDao = new dao.pedidosDao();
                    if (!pedidosDao.existePedidoParaVenta(ventaSeleccionada.getIdVen())) {
                        modelo.pedidos nuevoPedido = new modelo.pedidos();
                        nuevoPedido.setIdVen(ventaSeleccionada.getIdVen());
                        nuevoPedido.setIdCliente(ventaSeleccionada.getIdCliente());
                        nuevoPedido.setNombreCliente(ventaSeleccionada.getNombreCliente());
                        nuevoPedido.setEstado("Pendiente");
                        nuevoPedido.setFechaEntrega(new java.util.Date());
                        nuevoPedido.setObservacionesPedido(ventaSeleccionada.getObservaciones());
                        pedidosDao.agregar(nuevoPedido);
                    }
                    control.pedidosBean pedidosBean = (control.pedidosBean) FacesContext.getCurrentInstance()
                            .getExternalContext().getSessionMap().get("pedidosBean");
                    if (pedidosBean != null) {
                        pedidosBean.cargarPedidos();
                    }
                }

                dao.pedidosDao pedidosDao = new dao.pedidosDao();
                if (pedidosDao.existePedidoParaVenta(ventaSeleccionada.getIdVen())) {
                    pedidosDao.actualizarEstadoYObservacionesPorVenta(
                            ventaSeleccionada.getIdVen(),
                            ventaSeleccionada.getEstado(),
                            ventaSeleccionada.getObservaciones()
                    );
                }

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage("Venta actualizada correctamente"));
                cargarVentas();
                return "/views/Ventas/index.xhtml?faces-redirect=true";
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al actualizar la venta", null));
                return null;
            }
        }
        return null;
    }

    public void agregarDetalleEmpanada() {
        recetas receta = recetasDao.obtenerPorId(recetaSeleccionada); // 👈 ahora con la instancia
        if (receta != null && cantidadEmpanada > 0) {
            DetalleVenta det = new DetalleVenta();
            det.setIdReceta(receta.getId_rec());
            det.setNombreEmpanada(receta.getNombre());
            det.setCantidad(cantidadEmpanada);
            detallesVenta.add(det);

            cantidadEmpanada = 0;
            recetaSeleccionada = 0;

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Empanada agregada al carrito"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Datos inválidos", "Selecciona receta y cantidad"));
        }
    }

    public String registrarVenta() {

    // 1️⃣ Insertar venta y obtener ID
    int idVenta = ventasDao.agregar(ventaNueva);
    if (idVenta <= 0) {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo registrar la venta"));
        return null;
    }
    ventaNueva.setIdVen(idVenta);

    // 2️⃣ Insertar detalles de la venta
    for (DetalleVenta det : detallesVenta) {
        det.setIdVen(idVenta);
        detalleVentaDao.agregar(det); // tabla detalle_venta
        venta_recetas vr = new venta_recetas();
        vr.setIdVenta(idVenta);
        vr.setIdReceta(det.getIdReceta());
        vr.setCantidad(det.getCantidad());
        ventaRecetasDao.agregar(vr); // tabla venta_recetas
    }

    // 3️⃣ Crear producción
    produccion prod = new produccion();
    prod.setFecha_produccion(new java.sql.Date(System.currentTimeMillis()));
    prod.setEstado("Pendiente");
    prod.setUsuario(ventaNueva.getIdUsuario());
    prod.setFecha_hora(new java.util.Date());
    int idProduccion = produccionDao.agregar(prod);

    // 4️⃣ Enlazar venta y producción
    venta_produccion vp = new venta_produccion();
    vp.setIdVenta(idVenta);
    vp.setIdProduccion(idProduccion);
    ventaProduccionDao.agregar(vp);

    // 5️⃣ Registrar producción_recetas
    for (DetalleVenta det : detallesVenta) {
        produccion_recetas pr = new produccion_recetas();
        pr.setId_produccion(idProduccion);
        pr.setId_rec(det.getIdReceta());
        pr.setCantidad(det.getCantidad());
        produccionRecetasDao.agregar(pr);
    }

    // 6️⃣ Limpiar estado del bean
    detallesVenta.clear();
    ventaNueva = new ventas();
    ventaNueva.setFecha(new Date());
    cargarVentas();

    FacesContext.getCurrentInstance().addMessage(null,
        new FacesMessage("Venta registrada correctamente y producción generada"));

    return "/views/Ventas/index.xhtml?faces-redirect=true";
}


    public void verDetalleVenta(int idVenta) {
        detallesVentaActual = ventasDao.obtenerDetallesPorVenta(idVenta);
    }

    public void eliminarVenta(int id) {
        pedidosDao pedidosDao = new pedidosDao();
        if (pedidosDao.existePedidoParaVenta(id)) {
            pedidosDao.eliminarPorVenta(id);
        }
        if (ventasDao.eliminar(id)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Venta eliminada correctamente"));
            control.pedidosBean pedidosBean = (control.pedidosBean) FacesContext.getCurrentInstance()
                    .getExternalContext().getSessionMap().get("pedidosBean");
            if (pedidosBean != null) {
                pedidosBean.cargarPedidos();
            }
            cargarVentas();
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al eliminar la venta", null));
        }
    }

    public String prepararEdicion(ventas v) {
        this.ventaSeleccionada = v;
        return "/views/Ventas/editarVentas.xhtml?faces-redirect=true";
    }

    public String volverALaLista() {
        return "/views/Ventas/index.xhtml?faces-redirect=true";
    }
    
    public void eliminarDetalle(DetalleVenta det) {
    detallesVenta.remove(det);
    FacesContext.getCurrentInstance().addMessage(null,
        new FacesMessage("Receta eliminada del carrito"));
}

}
