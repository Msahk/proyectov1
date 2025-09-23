package control;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private List<Integer> recetasSeleccionadasIds; // IDs seleccionadas en checkbox

    // Filtros
    private String filtroEstado;
    private String filtroUsuario;

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
        recetasSeleccionadasIds = new ArrayList<>();
    }

    // =====================
    // GETTERS & SETTERS
    // =====================
    public List<produccion> getListaProducciones() { return listaProducciones; }
    public List<recetas> getListaRecetas() { return listaRecetas; }
    public produccion getProduccion() { return produccion; }
    public void setProduccion(produccion produccion) { this.produccion = produccion; }
    public List<Integer> getRecetasSeleccionadasIds() { return recetasSeleccionadasIds; }
    public void setRecetasSeleccionadasIds(List<Integer> recetasSeleccionadasIds) { this.recetasSeleccionadasIds = recetasSeleccionadasIds; }

    public String getFiltroEstado() { return filtroEstado; }
    public void setFiltroEstado(String filtroEstado) { this.filtroEstado = filtroEstado; }
    public String getFiltroUsuario() { return filtroUsuario; }
    public void setFiltroUsuario(String filtroUsuario) { this.filtroUsuario = filtroUsuario; }
   

    // =====================
    // CRUD PRODUCCIÓN
    // =====================
    public void registrarProduccion() {
        try {
            if (recetasSeleccionadasIds == null || recetasSeleccionadasIds.isEmpty()) {
                addMessage("⚠️ Debes seleccionar al menos una receta.");
                return;
            }

            // Reconstruir lista de produccion_receta a partir de IDs
            List<produccion_receta> seleccionadas = new ArrayList<>();
            for (Integer idRec : recetasSeleccionadasIds) {
                produccion_receta pr = new produccion_receta();
                pr.setId_rec(idRec);
                pr.setCantidad(1); // Ajusta según tu lógica
                seleccionadas.add(pr);
            }
            produccion.setRecetas(seleccionadas);

            produccion.setEstado("PENDIENTE");
            produccion.setUsuario(1); // TODO: obtener desde sesión

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
        recetasSeleccionadasIds = new ArrayList<>();
        if (produccion != null) {
            for (produccion_receta pr : pdao.listarRecetasPorProduccion(id)) {
                recetasSeleccionadasIds.add(pr.getId_rec());
            }
            idProduccionEditar = id;
        } else {
            addMessage("⚠️ Producción no encontrada.");
        }
    }

    public void actualizarProduccion() {
        if (produccion == null || produccion.getId_proc() == 0) {
            addMessage("⚠️ No hay producción seleccionada.");
            return;
        }

        try {
            boolean estadoActualizado = pdao.actualizarEstado(produccion);

            List<produccion_receta> seleccionadas = new ArrayList<>();
            if (recetasSeleccionadasIds != null) {
                for (Integer idRec : recetasSeleccionadasIds) {
                    produccion_receta pr = new produccion_receta();
                    pr.setId_rec(idRec);
                    pr.setCantidad(1);
                    seleccionadas.add(pr);
                }
            }

            boolean recetasActualizadas = pdao.actualizarRecetas(produccion.getId_proc(), seleccionadas);

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
    // UTILIDADES
    // =====================
    private void resetForm() {
        produccion = new produccion();
        recetasSeleccionadasIds = new ArrayList<>();
        idProduccionEditar = 0;
    }

    private void addMessage(String msg) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null));
        System.out.println(msg);
    }

    // Método auxiliar para mostrar nombres de recetas en la tabla
    public String getNombresRecetas(produccion prod) {
        if (prod.getRecetas() == null) return "";
        return prod.getRecetas().stream()
                .map(r -> r.getNombreReceta())
                .collect(Collectors.joining(", "));
    }
    
    // 🔹 Método para aplicar filtros
public void aplicarFiltros() {
    List<produccion> todas = pdao.listar();

    listaProducciones = todas.stream()
        .filter(p -> (filtroEstado == null || filtroEstado.isEmpty() || p.getEstado().toLowerCase().contains(filtroEstado.toLowerCase())))
        .filter(p -> (filtroUsuario == null || filtroUsuario.isEmpty() || String.valueOf(p.getUsuario()).contains(filtroUsuario)))
        .collect(Collectors.toList());
}

}
