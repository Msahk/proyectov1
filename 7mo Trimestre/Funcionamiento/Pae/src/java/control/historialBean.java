package control;

import dao.historialDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import modelo.historial;

@ManagedBean
@SessionScoped
public class historialBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private final historialDao dao = new historialDao();
    private historial h = new historial();
    private List<historial> lstHist = new ArrayList<>();
    private List<historial> lstHistFiltrados;

    @PostConstruct
    public void init() {
        listar();
    }

    public void listar() {
        lstHist = dao.listar();
    }

    public void agregar() {
        boolean ok = dao.agregar(h);
        if (ok) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Registro agregado correctamente"));
            listar();
            limpiar();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo agregar el registro"));
        }
    }

    public void eliminar(historial h) {
        boolean ok = dao.eliminar(h);
        if (ok) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Eliminado", "Registro eliminado"));
            listar();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar"));
        }
    }
    
    public void agregarEntrada() {
    h.setAccion("Entrada"); // valor válido para la columna ENUM
    boolean ok = dao.agregar(h);
    if (ok) {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Registro agregado correctamente"));
        listar();
        limpiar();
    } else {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo agregar el registro"));
    }
}

public void agregarSalida() {
    h.setAccion("Salida"); // valor válido para la columna ENUM
    boolean ok = dao.agregar(h);
    if (ok) {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Registro agregado correctamente"));
        listar();
        limpiar();
    } else {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo agregar el registro"));
    }
}


    public void limpiar() {
        h = new historial();
    }

    // Getters y Setters
    public historial getH() {
        return h;
    }

    public void setH(historial h) {
        this.h = h;
    }

    public List<historial> getLstHist() {
        return lstHist;
    }

    public void setLstHist(List<historial> lstHist) {
        this.lstHist = lstHist;
    }

    public List<historial> getLstHistFiltrados() {
        return lstHistFiltrados;
    }

    public void setLstHistFiltrados(List<historial> lstHistFiltrados) {
        this.lstHistFiltrados = lstHistFiltrados;
    }
}
