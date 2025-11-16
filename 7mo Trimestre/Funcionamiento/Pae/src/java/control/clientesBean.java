package control;

import dao.clientesDao;
import modelo.clientes;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.poi.ss.usermodel.*;

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
    private String filtroId; // 🔹 agregado porque el dao.filtrar usa 4 parámetros
    private Part excel;
    private List<clientes> listaClientesFiltrados;
    private clientes clienteSeleccionadoParaEliminar;


    @PostConstruct
    public void init() {
        listar();
    }

    /** 🔹 Cargar lista filtrada o completa */
    public void cargarClientes() {
        listaClientes = dao.filtrar(filtroId, filtroNombre, filtroTelefono, filtroCorreo);
    }

    /** 🔹 Listar todos los clientes */
    public void listar() {
        listaClientes = dao.listar();
    }

    /** 🔹 CRUD: Crear */
    public String prepararNuevoCliente() {
        clienteNuevo = new clientes();
        return "/views/Clientes/nuevoCliente.xhtml?faces-redirect=true";
    }

    public String guardarCliente() {
        int id = dao.agregar(clienteNuevo);
        if (id > 0) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("✅ Cliente registrado correctamente"));
            listar();
            return "/views/Clientes/Index.xhtml?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "❌ Error al registrar el cliente", null));
            return null;
        }
    }

    /** 🔹 CRUD: Editar */
    public String prepararEdicion(clientes c) {
        clienteSeleccionado = c;
        return "/views/Clientes/editarCliente.xhtml?faces-redirect=true";
    }

    public String actualizarCliente() {
        boolean ok = dao.actualizarEnCascada(clienteSeleccionado);
        if (ok) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("✅ Cliente actualizado correctamente"));
            listar();
            return "/views/Clientes/Index.xhtml?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "❌ Error al actualizar el cliente", null));
            return null;
        }
    }

    /** 🔹 CRUD: Eliminar */
    public void eliminarCliente(int id) {
        boolean ok = dao.eliminarEnCascada(id);
        if (ok) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("✅ Cliente eliminado correctamente"));
            listar();
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "❌ Error al eliminar el cliente", null));
        }
    }

    /** 🔹 Exportar a PDF con JasperReports */
    public void exportarPDF() {
        try {
            File jasper = new File(FacesContext.getCurrentInstance().getExternalContext()
                    .getRealPath("/reportes/Clientes.jasper"));

            ClientesDataSource ds = new ClientesDataSource(); // ✅ DataSource correcto

            JasperPrint jprint = JasperFillManager.fillReport(jasper.getPath(), null, ds);

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance()
                    .getExternalContext().getResponse();
            response.addHeader("Content-disposition", "attachment; filename=Clientes.pdf");

            ServletOutputStream stream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jprint, stream);

            stream.flush();
            stream.close();

            FacesContext.getCurrentInstance().responseComplete();
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, "❌ Error al generar PDF", e.getMessage()));
        }
    }

    /** 🔹 Migrar desde archivo Excel */
    public void migrar() {
        try {
            Workbook libro = WorkbookFactory.create(excel.getInputStream());
            Sheet hoja = libro.getSheetAt(0);
            Iterator<Row> itrFila = hoja.rowIterator();
            itrFila.next(); // Saltar cabecera

            while (itrFila.hasNext()) {
                Row fila = itrFila.next();
                clientes cli = new clientes();
                if (fila.getCell(0) != null)
                    cli.setNombre(fila.getCell(0).getStringCellValue());
                if (fila.getCell(1) != null)
                    cli.setTelefono(fila.getCell(1).getStringCellValue());
                if (fila.getCell(2) != null)
                    cli.setCorreo(fila.getCell(2).getStringCellValue());

                if (cli.getNombre() != null && !cli.getNombre().trim().isEmpty()) {
                    dao.agregar(cli);
                }
            }

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "✅ Éxito", "Clientes migrados exitosamente"));
            listar();

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, "❌ Error migrando clientes", e.getMessage()));
        }
    }
    
   public void eliminarClienteSeleccionado() {
    System.out.println("🔹 eliminarClienteSeleccionado llamado");

    if (clienteSeleccionadoParaEliminar != null) {
        System.out.println("Cliente seleccionado para eliminar: " + clienteSeleccionadoParaEliminar.getId_Cliente()
                + " - " + clienteSeleccionadoParaEliminar.getNombre());

        boolean ok = dao.eliminarEnCascada(clienteSeleccionadoParaEliminar.getId_Cliente());

        System.out.println("Resultado de eliminarEnCascada: " + ok);

        if (ok) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("✅ Cliente eliminado correctamente"));
            listar();
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "❌ Error al eliminar el cliente", null));
        }

        clienteSeleccionadoParaEliminar = null;
    } else {
        System.out.println("⚠️ No se seleccionó ningún cliente para eliminar");
    }
}


    /** 🔹 Getters y Setters */
    public List<clientes> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<clientes> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public clientes getClienteNuevo() {
        return clienteNuevo;
    }

    public void setClienteNuevo(clientes clienteNuevo) {
        this.clienteNuevo = clienteNuevo;
    }

    public clientes getClienteSeleccionado() {
        return clienteSeleccionado;
    }

    public void setClienteSeleccionado(clientes clienteSeleccionado) {
        this.clienteSeleccionado = clienteSeleccionado;
    }

    public String getFiltroNombre() {
        return filtroNombre;
    }

    public void setFiltroNombre(String filtroNombre) {
        this.filtroNombre = filtroNombre;
    }

    public String getFiltroTelefono() {
        return filtroTelefono;
    }

    public void setFiltroTelefono(String filtroTelefono) {
        this.filtroTelefono = filtroTelefono;
    }

    public String getFiltroCorreo() {
        return filtroCorreo;
    }

    public void setFiltroCorreo(String filtroCorreo) {
        this.filtroCorreo = filtroCorreo;
    }

    public String getFiltroId() {
        return filtroId;
    }

    public void setFiltroId(String filtroId) {
        this.filtroId = filtroId;
    }

    public Part getExcel() {
        return excel;
    }

    public void setExcel(Part excel) {
        this.excel = excel;
    }
    public List<clientes> getListaClientesFiltrados() {
    return listaClientesFiltrados;
}

public void setListaClientesFiltrados(List<clientes> listaClientesFiltrados) {
    this.listaClientesFiltrados = listaClientesFiltrados;
}

public clientes getClienteSeleccionadoParaEliminar() {
    return clienteSeleccionadoParaEliminar;
}

public void setClienteSeleccionadoParaEliminar(clientes clienteSeleccionadoParaEliminar) {
    this.clienteSeleccionadoParaEliminar = clienteSeleccionadoParaEliminar;
}

}
