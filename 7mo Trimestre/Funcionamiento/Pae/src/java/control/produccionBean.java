package control;

import dao.produccionDao;
import dao.produccion_recetasDao;
import dao.receta_insumosDao;
import dao.insumosDao;
import dao.venta_produccionDao;
import dao.ventasDao;

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

@ManagedBean
@ViewScoped
public class produccionBean implements Serializable {

    private static final long serialVersionUID = 1L;

    // 🔹 Objetos principales
    private produccion produccion;
    private produccion produccionSeleccionada;

    // 🔹 Listas
    private List<produccion> lstProduccion;
    private List<produccion> lstProduccionFiltrados;

    // 🔹 DAOs
    private produccionDao dao;
    private produccion_recetasDao daoProdRec;
    private receta_insumosDao daoRecIns;
    private insumosDao daoInsumos;

    // 🔹 Filtros
    private String filtroEstado;
    private Date filtroFecha;

    @ManagedProperty(value = "#{sessionUser}")
    private SessionUserBean sessionUser;

    // 🔹 Inicialización
    @PostConstruct
    public void init() {
        dao = new produccionDao();
        daoProdRec = new produccion_recetasDao();
        daoRecIns = new receta_insumosDao();
        daoInsumos = new insumosDao();
        produccion = new produccion();
        produccionSeleccionada = new produccion(); // importante para evitar null
        listar();
    }

    // 🟢 Listar producciones
    public void listar() {
        lstProduccion = dao.listar();
    }

    // 🟢 Guardar nueva producción
    public void guardar() {
        try {
            if (sessionUser == null || sessionUser.getUsuario() == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                                "No hay un usuario en sesión."));
                return;
            }

            if (produccion.getId_proc() == 0) {
                produccion.setEstado("Pendiente");
                produccion.setFecha_hora(new Timestamp(new Date().getTime()));
                produccion.setUsuario(sessionUser.getUsuario().getNombres() + " " +
                        sessionUser.getUsuario().getApellidos());

                int idGenerado = dao.agregar(produccion);

                if (idGenerado > 0) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito",
                                    "Producción creada con estado Pendiente."));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                                    "No se pudo registrar la producción."));
                }

                listar();
                limpiar();
            }

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                            "Ocurrió un problema al guardar la producción."));
        }
    }

    // 🟠 Cambiar estado dinámicamente
public void cambiarEstadoCiclo(produccion p) {
    try {
        String nuevoEstado = "";
        Date fechaActual = new Date();

        switch (p.getEstado()) {
            case "Pendiente":
                nuevoEstado = "Aceptada";
                dao.actualizarFechaAceptacion(p.getId_proc(), new Timestamp(fechaActual.getTime()));
                p.setFecha_aceptacion(fechaActual); // ✅ nombre correcto
                break;

            case "Aceptada":
                if (!validarStockProduccion(p)) return;

                nuevoEstado = "Finalizada";
                dao.actualizarFechaFinalizacion(p.getId_proc(), new Timestamp(fechaActual.getTime()));
                p.setFecha_finalizacion(fechaActual); // ✅ nombre correcto
                descontarStockProduccion(p);
                break;

            default:
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Información",
                                "La producción ya está finalizada."));
                return;
        }

        if (dao.cambiarEstado(p.getId_proc(), nuevoEstado)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Estado actualizado", "Nuevo estado: " + nuevoEstado));

            // ✅ Si finaliza, marcar venta como completada
            if (nuevoEstado.equals("Finalizada")) {
                venta_produccionDao vpDao = new venta_produccionDao();
                int idVenta = vpDao.obtenerIdVentaPorProduccion(p.getId_proc());
                if (idVenta > 0) {
                    ventasDao vDao = new ventasDao();
                    vDao.actualizarEstado(idVenta, "Completada");
                }
            }

            listar();
        }

    } catch (Exception e) {
        e.printStackTrace();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                        "No se pudo cambiar el estado de la producción."));
    }
}

    // 🧩 Validar stock antes de finalizar
    private boolean validarStockProduccion(produccion p) {
        try {
            List<produccion_recetas> recetas = daoProdRec.buscarPorProduccion(p.getId_proc());
            for (produccion_recetas pr : recetas) {
                List<receta_insumos> insumosReceta = daoRecIns.listarPorReceta(pr.getId_rec());
                for (receta_insumos ri : insumosReceta) {
                    insumos ins = daoInsumos.obtenerPorId(ri.getId_ins());
                    double requerido = ri.getCantidad() * pr.getCantidad();
                    if (ins.getStock_actual() < requerido) {
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Stock insuficiente",
                                        "No hay stock para: " + ins.getNombre() +
                                                " (actual: " + ins.getStock_actual() + ", requerido: " + requerido + ")"));
                        return false;
                    }
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 🧮 Descontar stock al finalizar
    private void descontarStockProduccion(produccion p) {
    try {
        dao.detalle_insumoDao daoDetalle = new dao.detalle_insumoDao(); // ✅ Nuevo

        List<produccion_recetas> recetas = daoProdRec.buscarPorProduccion(p.getId_proc());
        for (produccion_recetas pr : recetas) {
            List<receta_insumos> insumosReceta = daoRecIns.listarPorReceta(pr.getId_rec());
            for (receta_insumos ri : insumosReceta) {
                double cantidadUsada = ri.getCantidad() * pr.getCantidad();

                // 1️⃣ Descontar del stock general
                daoInsumos.descontarStock(ri.getId_ins(), cantidadUsada);

                // 2️⃣ Descontar del lote más próximo a vencer
                daoDetalle.descontarDeLotes(ri.getId_ins(), cantidadUsada);
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}


    // 🔹 Mostrar trazabilidad (3 fechas)
    public void verFechas(produccion p) {
        this.produccionSeleccionada = p;
    }

    // 🧹 Limpiar formulario
    public void limpiar() {
        produccion = new produccion();
    }
    
    // 🟢 Redirigir a la vista de recetas de la producción seleccionada
public String verRecetas(produccion p) {
    try {
        // Guarda la producción seleccionada en sesión
        FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .put("produccionSeleccionada", p);

        // Redirige a la vista de recetas asociadas
        return "/views/Producciones/produccionrecetas.xhtml?faces-redirect=true";

    } catch (Exception e) {
        e.printStackTrace();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                        "No se pudo abrir la vista de recetas de la producción."));
        return null;
    }
}


    // ✅ Getters & Setters
    public produccion getProduccion() { return produccion; }
    public void setProduccion(produccion produccion) { this.produccion = produccion; }

    public produccion getProduccionSeleccionada() { return produccionSeleccionada; }
    public void setProduccionSeleccionada(produccion produccionSeleccionada) { this.produccionSeleccionada = produccionSeleccionada; }

    public List<produccion> getLstProduccion() { return lstProduccion; }
    public void setLstProduccion(List<produccion> lstProduccion) { this.lstProduccion = lstProduccion; }

    public List<produccion> getLstProduccionFiltrados() { return lstProduccionFiltrados; }
    public void setLstProduccionFiltrados(List<produccion> lstProduccionFiltrados) { this.lstProduccionFiltrados = lstProduccionFiltrados; }

    public String getFiltroEstado() { return filtroEstado; }
    public void setFiltroEstado(String filtroEstado) { this.filtroEstado = filtroEstado; }

    public Date getFiltroFecha() { return filtroFecha; }
    public void setFiltroFecha(Date filtroFecha) { this.filtroFecha = filtroFecha; }

    public SessionUserBean getSessionUser() { return sessionUser; }
    public void setSessionUser(SessionUserBean sessionUser) { this.sessionUser = sessionUser; }
}
