package control;

import dao.produccionDao;
import dao.produccion_recetasDao;
import dao.receta_insumosDao;
import dao.insumosDao;

import modelo.produccion;
import modelo.produccion_recetas;
import modelo.receta_insumos;
import modelo.insumos;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.bean.ManagedProperty;

import control.SessionUserBean;

@ManagedBean
@ViewScoped
public class produccionBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private produccion produccion;
    private List<produccion> lstProduccion;
    private List<produccion> lstProduccionFiltrados;
    private produccionDao dao;

    // 🔹 Nuevos atributos
    private List<produccion_recetas> lstProduccionRecetas;
    private produccion_recetasDao daoProdRec;
    private receta_insumosDao daoRecIns;
    private insumosDao daoInsumos;

    private String filtroEstado;
    private Date filtroFecha;

    // 🔹 Inyección del usuario en sesión
    @ManagedProperty(value = "#{sessionUser}")
    private SessionUserBean sessionUser;

    @PostConstruct
    public void init() {
        dao = new produccionDao();
        daoProdRec = new produccion_recetasDao();
        daoRecIns = new receta_insumosDao();
        daoInsumos = new insumosDao();
        produccion = new produccion();
        listar();
    }

    // 🟢 Listar producciones
    public void listar() {
        lstProduccion = dao.listar();
    }

    // 🟢 Guardar o actualizar
    public void guardar() {
    try {
        // 🧩 Validar sesión de usuario
        if (sessionUser == null || sessionUser.getUsuario() == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No hay un usuario en sesión. Inicia sesión nuevamente."));
            return;
        }

        if (produccion.getId_proc() == 0) {
            // 🧩 Validar que no exista una producción con la misma fecha
            if (dao.existeFecha(produccion.getFecha_produccion())) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Ya existe una producción para esta fecha."));
                return;
            }

            produccion.setFecha_hora(new Timestamp(new Date().getTime()));
            produccion.setEstado("Pendiente");

            // 🧩 Asignar usuario logueado automáticamente
            produccion.setUsuario(sessionUser.getUsuario().getIdUsu());

            // 🔹 El método agregar ahora retorna el ID generado
            int idGenerado = dao.agregar(produccion);

            if (idGenerado > 0) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", 
                        "Producción registrada correctamente (ID: " + idGenerado + ")"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", 
                        "No se pudo registrar la producción."));
            }

        } else {
            if (dao.actualizar(produccion)) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizado", "Producción modificada correctamente"));
            }
        }

        listar();
        limpiar();

    } catch (Exception e) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo guardar la producción"));
        e.printStackTrace();
    }
}

    // 🟢 Editar
    public void editar(produccion p) {
        produccion = dao.obtenerPorId(p.getId_proc());
        lstProduccionRecetas = daoProdRec.buscarPorProduccion(p.getId_proc());

    }

    // 🟢 Eliminar
    public void eliminar(produccion p) {
        dao.eliminar(p);
        listar();
    }

    // 🟢 Cambiar estado (con descuento de insumos)
    public void cambiarEstado(produccion p, String nuevoEstado) {
        try {
            if (nuevoEstado.equalsIgnoreCase("Completada")) {
                List<produccion_recetas> recetasAsociadas = daoProdRec.buscarPorProduccion(p.getId_proc());

                for (produccion_recetas pr : recetasAsociadas) {
                    List<receta_insumos> insumosReceta = daoRecIns.listarPorReceta(pr.getId_rec());

                    for (receta_insumos ri : insumosReceta) {
                        double cantidadDescontar = ri.getCantidad() * pr.getCantidad();
                        daoInsumos.descontarStock(ri.getId_ins(), cantidadDescontar);
                    }
                }
            }

            if (dao.cambiarEstado(p.getId_proc(), nuevoEstado)) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Estado actualizado", "Nuevo estado: " + nuevoEstado));
                listar();
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo cambiar el estado"));
            e.printStackTrace();
        }
    }

    // 🧹 Limpiar formulario
    public void limpiar() {
        produccion = new produccion();
        lstProduccionRecetas = new ArrayList<>();
    }

    // ✅ Getters & Setters
    public produccion getProduccion() { return produccion; }
    public void setProduccion(produccion produccion) { this.produccion = produccion; }

    public List<produccion> getLstProduccion() { return lstProduccion; }
    public void setLstProduccion(List<produccion> lstProduccion) { this.lstProduccion = lstProduccion; }

    public List<produccion> getLstProduccionFiltrados() { return lstProduccionFiltrados; }
    public void setLstProduccionFiltrados(List<produccion> lstProduccionFiltrados) { this.lstProduccionFiltrados = lstProduccionFiltrados; }

    public String getFiltroEstado() { return filtroEstado; }
    public void setFiltroEstado(String filtroEstado) { this.filtroEstado = filtroEstado; }

    public Date getFiltroFecha() { return filtroFecha; }
    public void setFiltroFecha(Date filtroFecha) { this.filtroFecha = filtroFecha; }

    public List<produccion_recetas> getLstProduccionRecetas() { return lstProduccionRecetas; }
    public void setLstProduccionRecetas(List<produccion_recetas> lstProduccionRecetas) { this.lstProduccionRecetas = lstProduccionRecetas; }

    public SessionUserBean getSessionUser() { return sessionUser; }
    public void setSessionUser(SessionUserBean sessionUser) { this.sessionUser = sessionUser; }
}
