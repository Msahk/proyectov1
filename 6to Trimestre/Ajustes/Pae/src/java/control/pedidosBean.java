package control;

import dao.clientesDao;
import dao.pedidosDao;
import dao.ventasDao;

import modelo.pedidos;
import modelo.ventas;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import modelo.clientes;

import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.File;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import java.io.InputStream;
import java.util.Iterator;
import javax.servlet.http.Part;

@ManagedBean(name = "pedidosBean")
@SessionScoped
public class pedidosBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<pedidos> listaPedidos;
    private pedidos pedidoNuevo;
    private pedidos pedidoSeleccionado;
    private List<ventas> listaVentas;
    private clientesDao clienteDao = new clientesDao();
    private List<clientes> listaClientes = new ArrayList<>();
    private Integer filtroIdPedido;
private Integer filtroIdVenta;
private String filtroEstado;
private String filtroCliente;
private Date filtroFechaDesde;
private Date filtroFechaHasta;
private Part excel;
private List<pedidos> listaPedidosFiltrados;


    private pedidosDao pedidosDao = new pedidosDao();
    private ventasDao ventasDao = new ventasDao();

    @PostConstruct
    public void init() {
        pedidoNuevo = new pedidos();
        pedidoNuevo.setFechaEntrega(new Date());
        cargarPedidos();
        cargarVentas();
    }
 public Integer getFiltroIdPedido() { return filtroIdPedido; }
    public void setFiltroIdPedido(Integer filtroIdPedido) { this.filtroIdPedido = filtroIdPedido; }

    public Integer getFiltroIdVenta() { return filtroIdVenta; }
    public void setFiltroIdVenta(Integer filtroIdVenta) { this.filtroIdVenta = filtroIdVenta; }

    public String getFiltroEstado() { return filtroEstado; }
    public void setFiltroEstado(String filtroEstado) { this.filtroEstado = filtroEstado; }

    public String getFiltroCliente() { return filtroCliente; }
    public void setFiltroCliente(String filtroCliente) { this.filtroCliente = filtroCliente; }

    public Date getFiltroFechaDesde() { return filtroFechaDesde; }
    public void setFiltroFechaDesde(Date filtroFechaDesde) { this.filtroFechaDesde = filtroFechaDesde; }

    public Date getFiltroFechaHasta() { return filtroFechaHasta; }
    public void setFiltroFechaHasta(Date filtroFechaHasta) { this.filtroFechaHasta = filtroFechaHasta; }
   
    public void setListaPedidos(List<pedidos> listaPedidos) { this.listaPedidos = listaPedidos; }
public List<pedidos> getListaPedidosFiltrados() { return listaPedidosFiltrados; }
public void setListaPedidosFiltrados(List<pedidos> listaPedidosFiltrados) { this.listaPedidosFiltrados = listaPedidosFiltrados; }
    
    public void filtrarPedidos() {
        java.sql.Date sqlFechaDesde = filtroFechaDesde != null ? new java.sql.Date(filtroFechaDesde.getTime()) : null;
        java.sql.Date sqlFechaHasta = filtroFechaHasta != null ? new java.sql.Date(filtroFechaHasta.getTime()) : null;

        listaPedidos = pedidosDao.filtrarAvanzado(
            filtroIdPedido,
            filtroIdVenta,
            filtroEstado,
            filtroCliente,
            sqlFechaDesde,
            sqlFechaHasta
        );
    }

    public void cargarPedidos() {
        listaPedidos = pedidosDao.listar();
    }

    public void cargarVentas() {
        listaVentas = ventasDao.listarProcesando(); 
    }

    public String guardarPedido() {
        if (pedidosDao.agregar(pedidoNuevo)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Pedido registrado correctamente"));
            pedidoNuevo = new pedidos();
            pedidoNuevo.setFechaEntrega(new Date());
            cargarPedidos();
            cargarVentas();
            return "/views/Pedidos/Index.xhtml?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al registrar el pedido: " + pedidosDao.ultimoError, null));
            return null;
        }
    }

public String actualizarPedido() {
    if (pedidosDao.actualizar(pedidoSeleccionado)) {
        
        ventas venta = ventasDao.obtenerPorId(pedidoSeleccionado.getIdVen());
        if (venta != null) {
            listaClientes = clienteDao.listar(); 
            pedidoSeleccionado.setIdCliente(venta.getIdCliente());
            pedidoSeleccionado.setNombreCliente(venta.getNombreCliente());
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al obtener la venta asociada al pedido", null));
            return null;
        }

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Pedido actualizado correctamente"));
        cargarPedidos(); 
        cargarVentas();  
        return "/views/Pedidos/Index.xhtml?faces-redirect=true";
    } else {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al actualizar el pedido", null));
        return null;
    }
}

    public void eliminarPedido(int idPed) {
        if (pedidosDao.eliminar(idPed)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Pedido eliminado correctamente"));
            cargarPedidos();
            cargarVentas();
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al eliminar el pedido: " + pedidosDao.ultimoError, null));
        }
    }
    public void migrar() {
    try {
        Workbook libro = WorkbookFactory.create(excel.getInputStream());
        Sheet hoja = libro.getSheetAt(0);
        Iterator<Row> itrFila = hoja.rowIterator();
        itrFila.next(); // Saltar cabecera

        while (itrFila.hasNext()) {
            Row fila = itrFila.next();
            Iterator<Cell> itrCelda = fila.cellIterator();
            pedidos ped = new pedidos();
            int campo = 1;

            while (itrCelda.hasNext()) {
                Cell celda = itrCelda.next();
                switch (campo) {
                    case 1:
                        ped.setIdVen((int) celda.getNumericCellValue());
                        break;
                    case 2:
                        ped.setFechaEntrega(celda.getDateCellValue());
                        break;
                    case 3:
                        ped.setEstado(celda.getRichStringCellValue().toString());
                        break;
                    case 4:
                        ped.setObservacionesPedido(celda.getRichStringCellValue().toString());
                        break;
                }
                campo++;
            }
            pedidosDao.agregar(ped); 
        }
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Pedidos migrados exitosamente"));
    } catch (Exception e) {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Error migrando pedidos"));
    }
}

public Part getExcel() { return excel; }
public void setExcel(Part excel) { this.excel = excel; }
    public void exportarPDF() {
    try {
        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/pedidos.jasper");
        File jasper = new File(path);
        pedidosDataSource pds = new pedidosDataSource();
        
        System.out.println("Total pedidos para el reporte: " + pds.getSize());

        JasperPrint jprint = JasperFillManager.fillReport(jasper.getPath(), null, pds);

        HttpServletResponse resp = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        resp.addHeader("Content-disposition", "attachment; filename=Pedidos.pdf");

        try (ServletOutputStream stream = resp.getOutputStream()) {
            JasperExportManager.exportReportToPdfStream(jprint, stream);
            stream.flush();
        }

        FacesContext.getCurrentInstance().responseComplete();
    } catch (JRException | IOException e) {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error creando reporte de pedidos"));
        e.printStackTrace();
    }
}

    public String prepararEdicion(pedidos p) {
        this.pedidoSeleccionado = p;
        return "/views/Pedidos/editarPedido.xhtml?faces-redirect=true";
    }

    public String volverALaLista() {
        return "/views/Pedidos/Index.xhtml?faces-redirect=true";
    }

    public List<pedidos> getListaPedidos() { return listaPedidos; }
    public pedidos getPedidoNuevo() { return pedidoNuevo; }
    public void setPedidoNuevo(pedidos pedidoNuevo) { this.pedidoNuevo = pedidoNuevo; }
    public pedidos getPedidoSeleccionado() { return pedidoSeleccionado; }
    public void setPedidoSeleccionado(pedidos pedidoSeleccionado) { this.pedidoSeleccionado = pedidoSeleccionado; }
    public List<ventas> getListaVentas() { return listaVentas; }
    public void setListaVentas(List<ventas> listaVentas) { this.listaVentas = listaVentas; }
}