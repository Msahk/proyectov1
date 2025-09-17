// package beans;

// import javax.annotation.PostConstruct;
// import javax.faces.application.FacesMessage;
// import javax.faces.bean.ManagedBean;
// import javax.faces.bean.ViewScoped;
// import javax.faces.context.FacesContext;
// import java.io.Serializable;
// import java.util.List;
// import models.recetas;
// import models.recetasDao;
// import models.insumos;
// import models.insumosDao;
// import models.receta_insumos;
// import models.receta_insumosDao;

// @ManagedBean(name = "recetasBean")
// @ViewScoped
// public class recetasBean implements Serializable {

//     private recetas receta = new recetas();
//     private List<recetas> listaRecetas;

//     private int idReceta;   // receta seleccionada
//     private int insumoSeleccionado;
//     private double cantidad;
//     private String unidad;

//     private recetasDao recetaDao = new recetasDao();
//     private insumosDao insumoDao = new insumosDao();
//     private receta_insumosDao recetaInsumosDao = new receta_insumosDao();

//     private List<receta_insumos> listaInsumosReceta;
//     private List<insumos> listaInsumos;

//     // =====================
//     // INIT
//     // =====================
//     @PostConstruct
//     public void init() {
//         listaRecetas = recetaDao.listar();
//         listaInsumos = insumoDao.listar();
//     }

//     // =====================
//     // GETTERS Y SETTERS
//     // =====================
//     public recetas getReceta() { return receta; }
//     public void setReceta(recetas receta) { this.receta = receta; }

//     public int getIdReceta() { return idReceta; }
//     public void setIdReceta(int idReceta) {
//         this.idReceta = idReceta; 
//         if (idReceta > 0) {
//             listaInsumosReceta = recetaInsumosDao.listarPorReceta(idReceta); // actualiza los insumos para la receta seleccionada
//         }
//     }

//     public int getInsumoSeleccionado() { return insumoSeleccionado; }
//     public void setInsumoSeleccionado(int insumoSeleccionado) { this.insumoSeleccionado = insumoSeleccionado; }

//     public double getCantidad() { return cantidad; }
//     public void setCantidad(double cantidad) { this.cantidad = cantidad; }

//     public String getUnidad() { return unidad; }
//     public void setUnidad(String unidad) { this.unidad = unidad; }

//     public List<recetas> getListaRecetas() { return listaRecetas; }
//     public List<insumos> getInsumos() { return listaInsumos; }
//     public List<receta_insumos> getListaInsumosReceta() { return listaInsumosReceta; }

//     // =====================
//     // CRUD RECETA
//     // =====================
//     public void agregarReceta() {
//         if (recetaDao.agregar(receta)) {
//             receta = new recetas();
//             listaRecetas = recetaDao.listar(); // refrescar lista de recetas
//             addMessage("✅ Receta agregada correctamente.");
//         } else {
//             addMessage("❌ Error al agregar receta.");
//         }
//     }

//     public void editarReceta() {
//         if (recetaDao.actualizar(receta)) {
//             receta = new recetas();
//             listaRecetas = recetaDao.listar(); // refrescar lista de recetas
//             addMessage("✅ Receta actualizada correctamente.");
//         } else {
//             addMessage("❌ Error al actualizar receta.");
//         }
//     }

//    public void eliminarReceta() {
//     if (idReceta > 0) {
//         if (recetaDao.eliminar(idReceta)) {
//             listaRecetas = recetaDao.listar();
//             listaInsumosReceta = null;
//             addMessage("✅ Receta eliminada correctamente.");
//         } else {
//             addMessage("❌ Error al eliminar receta.");
//         }
//         idReceta = 0; // limpiar id temporal
//     } else {
//         addMessage("⚠️ No se seleccionó ninguna receta.");
//     }
// }
//     // =====================
//     // CRUD INSUMOS EN RECETA
//     // =====================
//     public void agregarInsumoAReceta() {
//         if (idReceta <= 0) {
//             addMessage("⚠️ Selecciona una receta primero.");
//             return;
//         }
//         if (insumoSeleccionado <= 0 || cantidad <= 0 || unidad == null || unidad.trim().isEmpty()) {
//             addMessage("⚠️ Datos inválidos para insumo.");
//             return;
//         }

//         receta_insumos ri = new receta_insumos();
//         ri.setId_rec(idReceta);
//         ri.setId_ins(insumoSeleccionado);
//         ri.setCantidad(cantidad);
//         ri.setUnidad(unidad);

//         if (recetaInsumosDao.agregar(ri)) {
//             listaInsumosReceta = recetaInsumosDao.listarPorReceta(idReceta); // refrescar los insumos asociados

//             // reset campos
//             insumoSeleccionado = 0;
//             cantidad = 0;
//             unidad = "";

//             addMessage("✅ Insumo agregado a la receta.");
//         } else {
//             addMessage("❌ Error al agregar insumo.");
//         }
//     }

//     public void eliminarInsumoDeReceta(int idRecIns) {
//         if (recetaInsumosDao.eliminarPorId(idRecIns)) {
//             listaInsumosReceta = recetaInsumosDao.listarPorReceta(idReceta); // refrescar los insumos
//             addMessage("✅ Insumo eliminado.");
//         } else {
//             addMessage("❌ Error al eliminar insumo.");
//         }
//     }

//     // =====================
//     // UTILS
//     // =====================
//     private void addMessage(String mensaje) {
//         FacesContext.getCurrentInstance().addMessage(null,
//                 new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, null));
//         System.out.println(mensaje);
//     }
// }
