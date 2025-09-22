package control;

import dao.clientesDao;
import modelo.clientes;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "clientesBean")
@SessionScoped
public class clientesBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private clientesDao dao = new clientesDao();
    private List<clientes> listaClientes = new ArrayList<>();
    private clientes clienteNuevo = new clientes();
    private clientes clienteSeleccionado = null;
    private String filtroNombre = "";

    @PostConstruct
    public void init() {
        cargarClientes();
    }

    public void cargarClientes() {
        if (filtroNombre == null || filtroNombre.trim().isEmpty()) {
            listaClientes = dao.listar();
        } else {
            listaClientes = dao.filtrarPorNombre(filtroNombre);
        }
    }

    public void filtrarClientes() {
        cargarClientes();
    }

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
        boolean ok = dao.actualizar(clienteSeleccionado);
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

    public void eliminarCliente(int id) {
        boolean ok = dao.eliminar(id);
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

    public String getFiltroNombre() { return filtroNombre; }
    public void setFiltroNombre(String filtroNombre) { this.filtroNombre = filtroNombre; }
}