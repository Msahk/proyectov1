package control;

import dao.clientesDao;
import java.io.File;
import java.io.IOException;
import modelo.clientes;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import java.io.InputStream;
import java.util.Iterator;
import javax.servlet.http.Part;

@ManagedBean(name = "clientesBean")
@SessionScoped
public class clientesBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private clientesDao dao = new clientesDao();
    private List<clientes> listaClientes = new ArrayList<>();
    private clientes clienteNuevo = new clientes();
    private clientes clienteSeleccionado = null;
    private String filtroNombre;
    private String filtroTelefono;
    private String filtroCorreo;
    private Part excel;

    @PostConstruct
    public void init() {
        cargarClientes();
    }

    public void cargarClientes() {
        listaClientes = dao.filtrar(filtroNombre, filtroTelefono, filtroCorreo);
    }

    public void listar() {
        listaClientes = dao.listar();
    }
    public String getFiltroNombre() { return filtroNombre; }
    public void setFiltroNombre(String filtroNombre) { this.filtroNombre = filtroNombre; }
    public String getFiltroTelefono() { return filtroTelefono; }
    public void setFiltroTelefono(String filtroTelefono) { this.filtroTelefono = filtroTelefono; }
    public String getFiltroCorreo() { return filtroCorreo; }
    public void setFiltroCorreo(String filtroCorreo) { this.filtroCorreo = filtroCorreo; }

    public String prepararNuevoCliente() {
        clienteNuevo = new clientes();
        return "/views/Clientes/nuevoCliente.xhtml?faces-redirect=true";
    }

    public String guardarCliente() {
        int id = dao.agregar(clienteNuevo);
        if (id > 0) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Cliente registrado correctamente"));
            cargarClientes();
            return "/views/Clientes/Index.xhtml?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al registrar el cliente", null));
            return null;
        }
    }

    public String prepararEdicion(clientes c) {
        clienteSeleccionado = c;
        return "/views/Clientes/editarCliente.xhtml?faces-redirect=true";
    }

    public String actualizarCliente() {
        boolean ok = dao.actualizarEnCascada(clienteSeleccionado);
        if (ok) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Cliente actualizado correctamente"));
            cargarClientes();
            return "/views/Clientes/Index.xhtml?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al actualizar el cliente", null));
            return null;
        }
    }
       public void exportarPDF() throws IOException {
        try {
            String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/clientes.jasper");
            File jasper = new File(path);
            ClientesDataSource uds = new ClientesDataSource();
            
            JasperPrint jprint = JasperFillManager.fillReport(jasper.getPath(), null, uds);
            
            HttpServletResponse resp = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        
            resp.addHeader("Content-disposition", "attachment; filename=Clientes.pdf");
            
            try (ServletOutputStream stream = resp.getOutputStream()){
                JasperExportManager.exportReportToPdfStream(jprint, stream);
                
                stream.flush();
                stream.close();
            }
        } catch (JRException | IOException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error creando reporte"));
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
            clientes cli = new clientes();
            int campo = 1;

            while (itrCelda.hasNext()) {
                Cell celda = itrCelda.next();
                switch (campo) {
                    case 1:
                        cli.setNombre(celda.getRichStringCellValue().toString());
                        break;
                    case 2:
                        cli.setTelefono(celda.getRichStringCellValue().toString());
                        break;
                    case 3:
                        cli.setCorreo(celda.getRichStringCellValue().toString());
                        break;
                }
                campo++;
            }
            dao.agregar(cli);
        }
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Clientes migrados exitosamente"));
    } catch (Exception e) {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Error migrando clientes"));
    }
}

public Part getExcel() { return excel; }
public void setExcel(Part excel) { this.excel = excel; }

    public void eliminarCliente(int id) {
        boolean ok = dao.eliminarEnCascada(id);
        if (ok) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Cliente eliminado correctamente"));
            cargarClientes();
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al eliminar el cliente", null));
        }
    }

    public List<clientes> getListaClientes() { return listaClientes; }
    public void setListaClientes(List<clientes> listaClientes) { this.listaClientes = listaClientes; }
    public clientes getClienteNuevo() { return clienteNuevo; }
    public void setClienteNuevo(clientes clienteNuevo) { this.clienteNuevo = clienteNuevo; }
    public clientes getClienteSeleccionado() { return clienteSeleccionado; }
    public void setClienteSeleccionado(clientes clienteSeleccionado) { this.clienteSeleccionado = clienteSeleccionado; }
}