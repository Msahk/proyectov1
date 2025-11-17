package control;

import dao.historialDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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

    private historialDao dao;
    private historial h;
    private List<historial> lstHist;
    private List<historial> lstHistFiltrados;

    public historialBean() {
        dao = new historialDao();
        h = new historial();
        lstHist = new ArrayList<>();
        lstHistFiltrados = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        listar();
    }

    // 🔹 Listar todos los registros
    public void listar() {
        lstHist = dao.listar();
    }

    // 🔹 Agregar registro genérico
    public void agregar() {
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

    // 🔹 Eliminar registro
   public void eliminar(historial h) {
    boolean ok = dao.eliminar(h.getIdHist()); // ✅ Pasamos solo el ID
    if (ok) {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_WARN, "Eliminado", "Registro eliminado correctamente"));
        listar();
    } else {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar el registro"));
    }
}



    // 🔹 Agregar entrada con cantidad y estado
    public void agregarEntrada(String estado, int cantidad, int id_ins, int id_detalle) {
    h = new historial();      // asignas el historial al atributo del bean
    h.setAccion("Entrada");
    h.setEstado(estado);
    h.setCantidad(cantidad);
    h.setFecha(new Date());
    h.setId_ins(id_ins);
    h.setId_detalle(id_detalle);

    int stockActualInsumo = dao.obtenerStockActualInsumo(id_ins);
    h.setStockActual(stockActualInsumo);

    agregar();   // ✔ sin parámetros
}



    // 🔹 Agregar salida con cantidad y estado
  // 🔹 Agregar salida con cantidad y estado
public void agregarSalida(String estado, int cantidad, int id_ins, int id_detalle) {

    h = new historial();
    h.setAccion("Salida");
    h.setEstado(estado);
    h.setCantidad(cantidad);
    h.setFecha(new Date());
    h.setId_ins(id_ins);
    h.setId_detalle(id_detalle);

    // 🔥 Obtener el stock actual del insumo ANTES de registrar la salida
    int stockActualInsumo = dao.obtenerStockActualInsumo(id_ins);
    h.setStockActual(stockActualInsumo);

    // Guardar en la BD
    agregar();   // ✔ sin parámetros
}


    // 🔹 Limpiar objeto
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
