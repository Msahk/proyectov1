package control;

import dao.ventasDao;
import dao.usuariosDao;
import dao.clientesDao;
import dao.detalleVentaDao;
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

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

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
import javax.faces.event.ComponentSystemEvent;
import modelo.clientes;
import javax.faces.bean.ManagedProperty;
import modelo.usuarios;
import java.util.stream.Collectors;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.ventas;
import modelo.usuarios;
import java.util.List;
import modelo.usuarios;
import dao.pagoDao; // Asegúrate de importar tu DAO
import modelo.pago;  // Si necesitas usar la clase pago
// si lo usas en exportarPDF

@Named("ventasBean")
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
    private int originalIdUsuario;
    private int originalIdCliente;
    private double precioRecetaSeleccionada;
    private double subtotalEmpanada;
    // 🔹 Lista temporal para almacenar los detalles de venta antes de guardar en BD
    private List<venta_recetas> lstVentaRecetasTemp = new ArrayList<>();
    @ManagedProperty(value = "#{sessionUser}")
    private SessionUserBean sessionUser;
    private String filtroAsignado;
    private List<usuarios> listaUsuariosEP;
    private List<usuarios> listaUsuariosVenta;

    // Auto-assignment display field
    private String nombreAsignadoAuto;

    // Campos para proteger la edición: valores originales guardados al preparar edición
    private String originalTipo;
    private Date originalFecha;
    private String originalEstado;
    private Integer originalIdAsignado;

    private static final String EMAIL_REGEX = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";
    private static final String PHONE_REGEX = "^\\d{7,15}$";
    private static final String NIT_REGEX = "^[0-9A-Za-z\\-]{3,20}$";
    private static final String NOMBRES_REGEX = "^[\\p{L} .'-]{2,100}$";

    // Getters / Setters
    public int getOriginalIdUsuario() {
        return originalIdUsuario;
    }

    public void setOriginalIdUsuario(int originalIdUsuario) {
        this.originalIdUsuario = originalIdUsuario;
    }

    public int getOriginalIdCliente() {
        return originalIdCliente;
    }

    public void setOriginalIdCliente(int originalIdCliente) {
        this.originalIdCliente = originalIdCliente;
    }

    public String getOriginalTipo() {
        return originalTipo;
    }

    public void setOriginalTipo(String originalTipo) {
        this.originalTipo = originalTipo;
    }

    public Date getOriginalFecha() {
        return originalFecha;
    }

    public void setOriginalFecha(Date originalFecha) {
        this.originalFecha = originalFecha;
    }

    public String getOriginalEstado() {
        return originalEstado;
    }

    public void setOriginalEstado(String originalEstado) {
        this.originalEstado = originalEstado;
    }

    public Integer getOriginalIdAsignado() {
        return originalIdAsignado;
    }

    public void setOriginalIdAsignado(Integer originalIdAsignado) {
        this.originalIdAsignado = originalIdAsignado;
    }

    public Part getExcelVentas() {
        return excelVentas;
    }

    public void setExcelVentas(Part excelVentas) {
        this.excelVentas = excelVentas;
    }

    public String getNombreAsignadoAuto() {
        return nombreAsignadoAuto;
    }

    public void setNombreAsignadoAuto(String nombreAsignadoAuto) {
        this.nombreAsignadoAuto = nombreAsignadoAuto;
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
            itrFila.next();

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
                ventasDao.agregar(v);
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
        
        if (sessionUser == null) {
    sessionUser = FacesContext.getCurrentInstance()
        .getApplication()
        .evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{sessionUser}", SessionUserBean.class);
}

        ventaNueva = new ventas();
        ventaNueva.setFecha(new Date());

        if (sessionUser != null && sessionUser.isLogged()) {
            usuarios u = sessionUser.getUsuario();
            if (u != null) {
                ventaNueva.setIdUsuario(u.getIdUsu());
                ventaNueva.setNombreUsuario(u.getNombres() + " " + u.getApellidos());
            }
        }

        cargarVentas();
        cargarUsuarios();
        cargarClientes();
        cargarRecetas();

        // ✅ Asignar correctamente la lista de usuarios de venta
        listaUsuariosVenta = listarUsuariosVenta();
    }

    private void cargarClientes() {
        listaClientes = clienteDao.listar();
    }

    public void cargarRecetas(ComponentSystemEvent event) {
        cargarRecetas();
    }

    public void cargarRecetas() {
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

    public String getFiltroAsignado() {
        return filtroAsignado;
    }

    public void setFiltroAsignado(String filtroAsignado) {
        this.filtroAsignado = filtroAsignado;
    }

    // prepararEdicion: guardamos los valores originales para protegerlos al actualizar
    public String prepararEdicion(ventas v) {
        this.ventaSeleccionada = v;

        if (v != null) {
            try {
                this.originalIdUsuario = v.getIdUsuario();
            } catch (Exception ex) {
                this.originalIdUsuario = 0;
            }
            try {
                this.originalIdCliente = v.getIdCliente();
            } catch (Exception ex) {
                this.originalIdCliente = 0;
            }

            try {
                this.originalTipo = v.getTipo();
            } catch (Exception ex) {
                this.originalTipo = null;
            }

            try {
                this.originalFecha = v.getFecha();
            } catch (Exception ex) {
                this.originalFecha = null;
            }

            try {
                this.originalEstado = v.getEstado();
            } catch (Exception ex) {
                this.originalEstado = null;
            }

            try {
                // idAsignado puede ser null in some models; use Integer
                Integer idAsig = v.getIdAsignado();
                this.originalIdAsignado = idAsig != null ? idAsig : null;
            } catch (Exception ex) {
                this.originalIdAsignado = null;
            }
        }

        return "/views/Ventas/editarVentas.xhtml?faces-redirect=true";
    }

    public void filtrarVentas() {
        java.sql.Date sqlFechaDesde = filtroFechaDesde != null ? new java.sql.Date(filtroFechaDesde.getTime()) : null;
        java.sql.Date sqlFechaHasta = filtroFechaHasta != null ? new java.sql.Date(filtroFechaHasta.getTime()) : null;

        listaVentas = ventasDao.filtrarAvanzado(
                filtroIdVenta,
                filtroTipo,
                filtroCliente,
                filtroUsuario,
                filtroAsignado, // ✅ nuevo filtro
                filtroTotalMin,
                filtroTotalMax,
                filtroEstado,
                sqlFechaDesde,
                sqlFechaHasta
        );

    }

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

    public double getPrecioRecetaSeleccionada() {
        return precioRecetaSeleccionada;
    }

    public void setPrecioRecetaSeleccionada(double precioRecetaSeleccionada) {
        this.precioRecetaSeleccionada = precioRecetaSeleccionada;
    }

    public double getSubtotalEmpanada() {
        return subtotalEmpanada;
    }

    public void setSubtotalEmpanada(double subtotalEmpanada) {
        this.subtotalEmpanada = subtotalEmpanada;
    }

    // Getter y setter para inyectar SessionUserBean
    public SessionUserBean getSessionUser() {
        return sessionUser;
    }

    public void setSessionUser(SessionUserBean sessionUser) {
        this.sessionUser = sessionUser;
    }

    public List<usuarios> getListaUsuariosVenta() {
        if (listaUsuariosVenta == null) {
            listaUsuariosVenta = new ArrayList<>();
        }
        return listaUsuariosVenta;
    }

    //  AJUSTE ESTEBAN
    public void onClienteChange() {
        try {
            if (ventaNueva == null) {
                System.out.println("onClienteChange: ventaNueva es null");
                return;
            }

            System.out.println("onClienteChange: idCliente = " + ventaNueva.getIdCliente());

            if (ventaNueva.getIdCliente() > 0) {

                nuevoCliente = false;
                clienteNuevo = new clientes();
                System.out.println("onClienteChange: cliente seleccionado -> nuevoCliente forced false");
            } else {

                clienteNuevo = new clientes();
                System.out.println("onClienteChange: sin cliente seleccionado -> checkbox puede habilitarse");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String guardarVenta() {
        try {

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

            // Validate that an EP was assigned
            if (ventaNueva.getIdAsignado() <= 0) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se encontró ningún empleado de producción disponible. Intenta más tarde."));
                return null;
            }

            // 🔹 1️⃣ Guardar venta
            ventaNueva.setTipo("pedido");
            int idGenerado = ventasDao.agregar(ventaNueva);
            if (idGenerado <= 0) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al registrar la venta", null));
                return null;
            }
            ventaNueva.setIdVen(idGenerado);

            // 🔹 2️⃣ Limpiar y refrescar
            ventaNueva = new ventas();
            ventaNueva.setFecha(new Date());
            detallesVenta.clear();
            nombreAsignadoAuto = null;
            cargarVentas();

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Venta registrada correctamente"));

            return "/views/Ventas/index.xhtml?faces-redirect=true";

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al registrar venta: " + e.getMessage(), null));
            return null;
        }
    }

    public String actualizarVenta() {
        if (ventaSeleccionada == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se ha seleccionado ninguna venta"));
            return null;
        }

        try {
            // Restauramos los valores originales (defensivo) por si alguien manipuló la petición.
            // Estos campos no deben cambiar desde la UI: idUsuario, idCliente, tipo, fecha, estado, idAsignado.
            // Si preparaste original values en prepararEdicion, úsalos; si no, intentamos no cambiarlos.
            try {
                ventaSeleccionada.setIdUsuario(this.originalIdUsuario);
            } catch (Exception ignored) {
            }
            try {
                ventaSeleccionada.setIdCliente(this.originalIdCliente);
            } catch (Exception ignored) {
            }
            try {
                if (this.originalTipo != null) {
                    ventaSeleccionada.setTipo(this.originalTipo);
                }
            } catch (Exception ignored) {
            }
            try {
                if (this.originalFecha != null) {
                    ventaSeleccionada.setFecha(this.originalFecha);
                }
            } catch (Exception ignored) {
            }
            try {
                if (this.originalEstado != null) {
                    ventaSeleccionada.setEstado(this.originalEstado);
                }
            } catch (Exception ignored) {
            }
            try {
                if (this.originalIdAsignado != null) {
                    ventaSeleccionada.setIdAsignado(this.originalIdAsignado);
                }
            } catch (Exception ignored) {
            }

            // ÚNICOS campos permitidos a cambiar: observaciones
            String nuevasObs = ventaSeleccionada.getObservaciones();

            // (Opcional) Validación de longitud / contenido de observaciones
            if (nuevasObs != null && nuevasObs.length() > 2000) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Las observaciones son demasiado largas (máx. 2000 caracteres)"));
                return null;
            }

            // Llamamos al DAO que actualiza SOLO observaciones
            boolean actualizado = ventasDao.actualizarObservaciones(ventaSeleccionada.getIdVen(), nuevasObs);

            if (!actualizado) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo actualizar las observaciones"));
                return null;
            }

            // Refrescar lista de ventas
            cargarVentas();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Observaciones actualizadas correctamente"));

            return "/views/Ventas/index.xhtml?faces-redirect=true";

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al actualizar venta: " + e.getMessage()));
            return null;
        }
    }

    public void agregarDetalleEmpanada() {
        recetas receta = recetasDao.obtenerPorId(recetaSeleccionada);

        if (receta != null && cantidadEmpanada > 0) {

            // 🔹 Detalle temporal (para la vista y el cálculo del total)
            DetalleVenta det = new DetalleVenta();
            det.setIdReceta(receta.getId_rec());
            det.setNombreEmpanada(receta.getNombre());
            det.setCantidad(cantidadEmpanada);
            det.setPrecioUnitario(receta.getPrecio());
            det.setSubtotal(receta.getPrecio() * cantidadEmpanada);
            detallesVenta.add(det);

            // 🔹 También crear el objeto venta_recetas (para trazabilidad en BD)
            venta_recetas vr = new venta_recetas();
            vr.setIdReceta(receta.getId_rec());
            vr.setCantidad(cantidadEmpanada);
            vr.setPrecio(receta.getPrecio()); // precio al momento de la venta
            vr.setSubtotal(receta.getPrecio() * cantidadEmpanada); // subtotal histórico
            vr.setNombreReceta(receta.getNombre());
            // idVenta se asignará luego cuando se guarde la venta

            // 🔹 Guardarlo en una lista temporal para registrar después
            if (lstVentaRecetasTemp == null) {
                lstVentaRecetasTemp = new ArrayList<>();
            }
            lstVentaRecetasTemp.add(vr);

            // 🔹 Actualizar el total
            double nuevoTotal = 0.0;
            for (DetalleVenta d : detallesVenta) {
                nuevoTotal += d.getSubtotal();
            }
            ventaNueva.setTotal(nuevoTotal);

            // 🔹 Limpiar campos visuales
            cantidadEmpanada = 0;
            recetaSeleccionada = 0;
            precioRecetaSeleccionada = 0.0;
            subtotalEmpanada = 0.0;

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Empanada agregada al carrito"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Datos inválidos", "Selecciona receta y cantidad"));
        }
    }

    public String registrarVenta() {
        
        if (sessionUser == null) {
    sessionUser = FacesContext.getCurrentInstance()
        .getApplication()
        .evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{sessionUser}", SessionUserBean.class);
}
        try {
            // 🔹 Asegurar idUsuario (fallback)
            if (ventaNueva.getIdUsuario() <= 0) {
                try {
                    if (sessionUser != null && sessionUser.isLogged() && sessionUser.getUsuario() != null) {
                        ventaNueva.setIdUsuario(sessionUser.getUsuario().getIdUsu());
                        ventaNueva.setNombreUsuario(sessionUser.getUsuario().getNombres() + " " + sessionUser.getUsuario().getApellidos());
                    } else {
                        SessionUserBean su = FacesContext.getCurrentInstance()
                                .getApplication()
                                .evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{sessionUser}", SessionUserBean.class);
                        if (su != null && su.isLogged() && su.getUsuario() != null) {
                            usuarios u = su.getUsuario();
                            ventaNueva.setIdUsuario(u.getIdUsu());
                            ventaNueva.setNombreUsuario(u.getNombres() + " " + u.getApellidos());
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            // -------------------------
            //  Cliente nuevo (único bloque)
            // -------------------------
            if (nuevoCliente) {
                // 1) Validación servidor
                if (!validarClienteNuevo()) {
                    return null; // mensajes ya agregados por validarClienteNuevo()
                }

                // 2) Comprobar existencia por teléfono o NIT para evitar unique constraint
                clientes existente = null;
                if (clienteNuevo.getTelefono() != null && !clienteNuevo.getTelefono().trim().isEmpty()) {
                    existente = clienteDao.buscarPorTelefono(clienteNuevo.getTelefono());
                }
                if (existente == null && clienteNuevo.getNit() != null && !clienteNuevo.getNit().trim().isEmpty()) {
                    existente = clienteDao.buscarPorNit(clienteNuevo.getNit());
                }

                if (existente != null) {
                    // Reutilizar cliente existente
                    ventaNueva.setIdCliente(existente.getIdCliente());
                    ventaNueva.setNombreCliente(existente.getNombre());
                } else {
                    // Insertar nuevo cliente (clientesDao.agregar devuelve id o -1 en fallo)
                    int idClienteGenerado = clienteDao.agregar(clienteNuevo);
                    if (idClienteGenerado > 0) {
                        ventaNueva.setIdCliente(idClienteGenerado);
                        ventaNueva.setNombreCliente(clienteNuevo.getNombre());
                        listaClientes = clienteDao.listar(); // refrescar lista
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo registrar el cliente nuevo"));
                        return null;
                    }
                }
            }

            // Validaciones generales
            usuarios usuarioBD = usuariosDao.obtenerPorId(ventaNueva.getIdUsuario());
            if (usuarioBD == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El usuario seleccionado no existe"));
                return null;
            }

            if (ventaNueva.getIdAsignado() <= 0) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se encontró ningún empleado de producción disponible. Intenta más tarde."));
                return null;
            }

            if (detallesVenta == null || detallesVenta.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe agregar al menos una empanada al carrito"));
                return null;
            }

            if (!nuevoCliente && ventaNueva.getIdCliente() <= 0) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe seleccionar un cliente existente o crear uno nuevo"));
                return null;
            }

            // -------------------------
            // Preparar y guardar venta
            // -------------------------
            ventaNueva.setTipo("pedido");
            ventaNueva.setEstado("Pago pendiente");
            if (ventaNueva.getFecha() == null) {
                ventaNueva.setFecha(new Date());
            }
            ventaNueva.setNombreUsuario(usuarioBD.getNombres() + " " + usuarioBD.getApellidos());
            double total = detallesVenta.stream().mapToDouble(DetalleVenta::getSubtotal).sum();
            ventaNueva.setTotal(total);

            int idVenta = ventasDao.agregar(ventaNueva);
            if (idVenta <= 0) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo registrar la venta"));
                return null;
            }
            ventaNueva.setIdVen(idVenta);

            // Guardar detalles
            for (DetalleVenta det : detallesVenta) {
                det.setIdVen(idVenta);
                detalleVentaDao.agregar(det);

                venta_recetas vr = new venta_recetas();
                vr.setIdVenta(idVenta);
                vr.setIdReceta(det.getIdReceta());
                vr.setCantidad(det.getCantidad());
                vr.setPrecio(det.getPrecioUnitario());
                vr.setSubtotal(det.getSubtotal());
                ventaRecetasDao.agregar(vr);
            }

            // Limpieza
            detallesVenta.clear();
            ventaNueva = new ventas();
            ventaNueva.setFecha(new Date());
            clienteNuevo = new clientes();
            nuevoCliente = false;
            nombreAsignadoAuto = null;
            cargarVentas();

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("✅ Venta registrada correctamente (Pago pendiente)"));

            return "/views/Ventas/index.xhtml?faces-redirect=true";

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al registrar venta: " + e.getMessage()));
            return null;
        }
    }

    public void verDetalleVenta(int idVenta) {
        detallesVentaActual = ventasDao.obtenerDetallesPorVenta(idVenta);
    }

    public void eliminarVenta(int id) {
        if (ventasDao.eliminar(id)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Venta eliminada correctamente"));
            // 🔹 Refrescar lista de ventas
            cargarVentas();
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al eliminar la venta", null));
        }
    }

    public String volverALaLista() {
        return "/views/Ventas/index.xhtml?faces-redirect=true";
    }

    // ---  validar cliente nuevo ---
    private boolean validarClienteNuevo() {
        if (clienteNuevo == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No hay datos del cliente."));
            return false;
        }

        boolean ok = true;
        FacesContext ctx = FacesContext.getCurrentInstance();

        // Nombres
        String nombre = clienteNuevo.getNombre();
        if (nombre == null || nombre.trim().isEmpty()) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nombre requerido", "Ingrese el nombre del cliente."));
            ok = false;
        } else if (!nombre.matches(NOMBRES_REGEX)) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nombre inválido", "El nombre contiene caracteres no permitidos."));
            ok = false;
        }

        // Teléfono
        String telefono = clienteNuevo.getTelefono();
        if (telefono != null && !telefono.trim().isEmpty()) {
            if (!telefono.matches(PHONE_REGEX)) {
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Teléfono inválido", "El teléfono debe contener solo dígitos (7-15)."));
                ok = false;
            }
        } else {

        }

        // Correo
        String correo = clienteNuevo.getCorreo();
        if (correo == null || correo.trim().isEmpty()) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Correo requerido", "Ingrese el correo del cliente."));
            ok = false;
        } else if (!correo.matches(EMAIL_REGEX)) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Correo inválido", "El correo no tiene un formato válido."));
            ok = false;
        }

        // NIT
        String nit = clienteNuevo.getNit();
        if (nit == null || nit.trim().isEmpty()) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "NIT requerido", "Ingrese el NIT del cliente."));
            ok = false;
        } else if (!nit.matches(NIT_REGEX)) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "NIT inválido", "El NIT contiene caracteres no permitidos."));
            ok = false;
        }

        return ok;
    }

    // Eliminar detalle
    public void eliminarDetalle(DetalleVenta det) {
        detallesVenta.remove(det);

        // 🔹 Recalcular el total, incluso si la lista quedó vacía
        recalcularTotalVenta();

        // 🔹 Crear nueva referencia para que JSF detecte el cambio
        detallesVenta = new ArrayList<>(detallesVenta);

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Receta eliminada del carrito"));
    }

    public void inicializarNuevaVenta() {
        
        if (sessionUser == null) {
    sessionUser = FacesContext.getCurrentInstance()
        .getApplication()
        .evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{sessionUser}", SessionUserBean.class);
}

        if (!FacesContext.getCurrentInstance().isPostback()) {
            ventaNueva = new ventas();
            detallesVenta = new ArrayList<>();
            recetaSeleccionada = 0;
            cantidadEmpanada = 0;
            precioRecetaSeleccionada = 0.0;
            subtotalEmpanada = 0.0;
            nuevoCliente = false;
            clienteNuevo = new clientes();
            cargarRecetas();

            // Auto-assign EP proposal for the new form (so UI can display nombreAsignadoAuto if desired)
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                SessionUserBean su = FacesContext.getCurrentInstance()
                        .getApplication()
                        .evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{sessionUser}", SessionUserBean.class);

                if (su != null && su.isLogged() && su.getUsuario() != null) {
                    usuarios u = su.getUsuario();
                    ventaNueva.setIdUsuario(u.getIdUsu()); // asigna id del usuario en sesión
                    ventaNueva.setNombreUsuario(u.getNombres() + " " + u.getApellidos());
                    System.out.println("inicializarNuevaVenta: usuario en sesión asignado como registrador: " + u.getIdUsu());
                } else {
                    System.err.println("inicializarNuevaVenta: no se encontró sessionUser o no está logueado");
                }
            } catch (Exception e) {
                System.err.println("inicializarNuevaVenta: error al obtener sessionUser via EL: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void actualizarPrecioReceta() {
        if (recetaSeleccionada > 0) {
            recetas receta = recetasDao.obtenerPorId(recetaSeleccionada);
            if (receta != null) {
                this.precioRecetaSeleccionada = receta.getPrecio();
            } else {
                this.precioRecetaSeleccionada = 0.0;
            }
        } else {
            this.precioRecetaSeleccionada = 0.0;
        }

        // 🔹 Recalcula el subtotal cada vez que se actualiza el precio
        calcularSubtotalEmpanada();
    }

    public void calcularSubtotalEmpanada() {
        this.subtotalEmpanada = this.precioRecetaSeleccionada * this.cantidadEmpanada;
    }

    public void recalcularTotalVenta() {
        double total = 0.0;
        for (DetalleVenta det : detallesVenta) {
            total += det.getSubtotal();
        }
        ventaNueva.setTotal(total); // 🔹 Si la lista está vacía, total será 0
    }

    // Recalcula total de ventaSeleccionada usando detallesVentaActual (usado en editar)
    private void recalcularTotalVentaSeleccionada() {
        double total = 0.0;
        if (detallesVentaActual != null) {
            for (DetalleVenta det : detallesVentaActual) {
                total += det.getSubtotal();
            }
        }
        if (ventaSeleccionada != null) {
            ventaSeleccionada.setTotal(total);
        }
    }

    public List<usuarios> getListaUsuariosEP() {
        if (listaUsuariosEP == null) {
            listaUsuariosEP = listaUsuarios.stream()
                    .filter(u -> "EP".equals(u.getRol()))
                    .collect(Collectors.toList());
        }
        return listaUsuariosEP;
    }

    // Método que se llama desde <f:event type="preRenderView">
    public List<usuarios> listarUsuariosVenta() {
        List<usuarios> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE (rol = 'EV' OR rol = 'A') AND estado = 'A'";

        try (Connection cn = ConDB.conectar(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                usuarios u = new usuarios();
                u.setIdUsu(rs.getInt("id_usu"));
                u.setNombres(rs.getString("nombres"));
                u.setApellidos(rs.getString("apellidos"));
                u.setRol(rs.getString("rol"));
                lista.add(u);
            }

            System.out.println("✅ Usuarios cargados para venta: " + lista.size());

        } catch (SQLException e) {
            System.out.println("❌ Error en listarUsuariosVenta(): " + e.getMessage());
        }

        return lista;
    }

    public int conteo(int id) {
        int conteo = ventasDao.obtenerConteoProduccion(id);
        return conteo;
    }

    public String getNombreAsignado(ventas venta) {
        if (venta.getIdAsignado() > 0) {
            usuarios usuario = usuariosDao.obtenerPorId(venta.getIdAsignado());
            if (usuario != null) {
                return usuario.getNombres() + " " + usuario.getApellidos();
            }
        }
        return "-";
    }

}
