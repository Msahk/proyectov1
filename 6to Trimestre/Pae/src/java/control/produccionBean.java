package control;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import modelo.produccion;
import dao.produccionDao;
import modelo.produccion_receta;
import modelo.recetas;
import dao.recetasDao;

@ManagedBean(name = "produccionBean")
@ViewScoped
public class produccionBean implements Serializable {

    private static final long serialVersionUID = 1L;

    // DAOs
    private produccionDao pdao = new produccionDao();
    private recetasDao rdao = new recetasDao();

    // Datos
    private List<produccion> listaProducciones;
    private List<recetas> listaRecetas;
    private produccion produccion;
    private List<produccion_receta> recetasSeleccionadas;

    private int idProduccionEditar;
    private Integer idProdEliminar;

    // =====================
    // INIT
    // =====================
    @PostConstruct
    public void init() {
        listaProducciones = pdao.listar();
        listaRecetas = rdao.listar();
        produccion = new produccion();
        recetasSeleccionadas = new ArrayList<>();
    }

    // =====================
    // GETTERS & SETTERS
    // =====================
    public List<produccion> getListaProducciones() { return listaProducciones; }
    public List<recetas> getListaRecetas() { return listaRecetas; }
    public produccion getProduccion() { return produccion; }
    public void setProduccion(produccion produccion) { this.produccion = produccion; }
    public List<produccion_receta> getRecetasSeleccionadas() { return recetasSeleccionadas; }
    public void setRecetasSeleccionadas(List<produccion_receta> recetasSeleccionadas) { this.recetasSeleccionadas = recetasSeleccionadas; }

    // =====================
    // CRUD PRODUCCIÓN
    // =====================
    public void registrarProduccion() {
        try {
            if (recetasSeleccionadas == null || recetasSeleccionadas.isEmpty()) {
                addMessage("⚠️ Debes seleccionar al menos una receta.");
                return;
            }

            produccion.setEstado("PENDIENTE");
            produccion.setUsuario(1); // TODO: tomar desde sesión
            produccion.setRecetas(recetasSeleccionadas);

            boolean exito = pdao.agregarProduccion(produccion);
            if (exito) {
                addMessage("✅ Producción registrada correctamente.");
                resetForm();
                listaProducciones = pdao.listar();
            } else {
                addMessage("❌ Error al registrar producción.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            addMessage("❌ Error: " + e.getMessage());
        }
    }

    public void cargarProduccion(int id) {
        produccion = pdao.obtenerPorId(id);
        if (produccion == null) {
            addMessage("⚠️ Producción no encontrada.");
            recetasSeleccionadas = new ArrayList<>();
        } else {
            recetasSeleccionadas = pdao.listarRecetasPorProduccion(id);
            idProduccionEditar = id;
        }
    }

    public void actualizarProduccion() {
        if (produccion == null || produccion.getId_proc() == 0) {
            addMessage("⚠️ No hay producción seleccionada.");
            return;
        }

        try {
            boolean estadoActualizado = pdao.actualizarEstado(produccion);
            boolean recetasActualizadas = true;

            if (recetasSeleccionadas != null && !recetasSeleccionadas.isEmpty()) {
                recetasActualizadas = pdao.actualizarRecetas(produccion.getId_proc(), recetasSeleccionadas);
            }

            if (estadoActualizado && recetasActualizadas) {
                listaProducciones = pdao.listar();
                addMessage("✅ Producción actualizada correctamente.");
                resetForm();
            } else {
                addMessage("❌ Error al actualizar producción.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            addMessage("❌ Error: " + e.getMessage());
        }
    }

    public void eliminarProduccion(int idProd) {
        if (pdao.eliminar(idProd)) {
            listaProducciones = pdao.listar();
            addMessage("✅ Producción eliminada.");
        } else {
            addMessage("❌ Error al eliminar producción.");
        }
    }

    public void finalizarProduccion(int idProd, String usuario) {
        boolean exito = pdao.finalizarProduccion(idProd, usuario);
        if (exito) {
            listaProducciones = pdao.listar();
            addMessage("✅ Producción finalizada y stock actualizado.");
        } else {
            addMessage("❌ Error al finalizar producción. Verifica stock.");
        }
    }

    // =====================
    // GESTIÓN DE RECETAS
    // =====================
    public void agregarReceta() {
        if (recetasSeleccionadas == null) recetasSeleccionadas = new ArrayList<>();
        recetasSeleccionadas.add(new produccion_receta());
    }

    public void eliminarReceta(produccion_receta recetaSel) {
        if (recetasSeleccionadas != null) {
            recetasSeleccionadas.remove(recetaSel);
        }
    }

    // =====================
    // UTILIDADES
    // =====================
    private void resetForm() {
        produccion = new produccion();
        recetasSeleccionadas = new ArrayList<>();
        idProduccionEditar = 0;
    }

    private void addMessage(String msg) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null));
        System.out.println(msg);
    }
}
