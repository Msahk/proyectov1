// package beans;

// import javax.annotation.PostConstruct;
// import javax.faces.application.FacesMessage;
// import javax.faces.bean.ManagedBean;
// import javax.faces.bean.ViewScoped;
// import javax.faces.context.FacesContext;
// import java.io.Serializable;
// import java.sql.Date;
// import java.util.ArrayList;
// import java.util.List;

// import models.produccion;
// import models.produccionDao;
// import models.produccion_receta;
// import models.recetas;
// import models.recetasDao;

// @ManagedBean(name = "produccionBean")
// @ViewScoped
// public class produccionBean implements Serializable {

//     private static final long serialVersionUID = 1L;

//     // DAOs
//     private produccionDao pdao = new produccionDao();
//     private recetasDao rdao = new recetasDao();

//     // Datos
//     private List<produccion> listaProducciones;
//     private List<recetas> listaRecetas;
//     private produccion produccion;
//     private List<produccion_receta> recetasSeleccionadas;

//     private int idProduccionEditar; // para cargar al editar
//     private Integer idProdEliminar; // getter y setter

//     // =====================
//     // INIT
//     // =====================
//     @PostConstruct
//     public void init() {
//         listaProducciones = pdao.listar();
//         listaRecetas = rdao.listar();
//         produccion = new produccion();
//         recetasSeleccionadas = new ArrayList<>();
//     }

//     // =====================
//     // GETTERS & SETTERS
//     // =====================
//     public List<produccion> getListaProducciones() {
//         return listaProducciones;
//     }

//     public List<recetas> getListaRecetas() {
//         return listaRecetas;
//     }

//     public produccion getProduccion() {
//         return produccion;
//     }

//     public void setProduccion(produccion produccion) {
//         this.produccion = produccion;
//     }

//     public List<produccion_receta> getRecetasSeleccionadas() {
//         return recetasSeleccionadas;
//     }

//     public void setRecetasSeleccionadas(List<produccion_receta> recetasSeleccionadas) {
//         this.recetasSeleccionadas = recetasSeleccionadas;
//     }

//     // =====================
//     // CRUD PRODUCCIÓN
//     // =====================
//     public void registrarProduccion() {
//         try {
//             if (recetasSeleccionadas == null || recetasSeleccionadas.isEmpty()) {
//                 addMessage("⚠️ Debes seleccionar al menos una receta.");
//                 return;
//             }

//             produccion.setEstado("PENDIENTE");
//             produccion.setUsuario(1); // TODO: tomar desde sesión si lo manejas

//             produccion.setRecetas(recetasSeleccionadas);

//             boolean exito = pdao.guardarProduccion(produccion);
//             if (exito) {
//                 addMessage("✅ Producción registrada correctamente.");
//                 resetForm();
//                 listaProducciones = pdao.listar();
//             } else {
//                 addMessage("❌ Error al registrar producción.");
//             }

//         } catch (Exception e) {
//             e.printStackTrace();
//             addMessage("❌ Error: " + e.getMessage());
//         }
//     }

//     public void editarProduccion(int idProd) {
//         produccion = pdao.buscarPorId(idProd);
//         recetasSeleccionadas = pdao.listarRecetasPorProduccion(idProd);
//         idProduccionEditar = idProd;
//     }

//     public String actualizarProduccion() {
//         if (produccion == null || produccion.getId_proc() == 0) {
//             addMessage("⚠️ No hay producción seleccionada.");
//             return null;
//         }

//         try {
//             boolean exito = pdao.actualizarEstado(produccion);

//             if (recetasSeleccionadas != null && !recetasSeleccionadas.isEmpty()) {
//                 pdao.actualizarRecetasProduccion(produccion.getId_proc(), recetasSeleccionadas);
//             }

//             if (exito) {
//                 listaProducciones = pdao.listar();
//                 addMessage("✅ Producción actualizada.");
//                 resetForm();
//             } else {
//                 addMessage("❌ Error: no se pudo actualizar la producción.");
//             }
//         } catch (Exception e) {
//             e.printStackTrace();
//             addMessage("❌ Error: " + e.getMessage());
//         }

//         return null; // 🔹 muy importante
//     }

//     public void eliminarProduccion(int idProd) {
//         if (pdao.eliminarProduccion(idProd)) {
//             listaProducciones = pdao.listar();
//             addMessage("✅ Producción eliminada.");
//         } else {
//             addMessage("❌ Error al eliminar producción.");
//         }
//     }

//     public void finalizarProduccion(int idProd) {
//         boolean exito = pdao.finalizarProduccion(idProd);
//         if (exito) {
//             listaProducciones = pdao.listar();
//             addMessage("✅ Producción finalizada y stock actualizado.");
//         } else {
//             addMessage("❌ Error al finalizar producción. Verifica stock.");
//         }
//     }

//     // =====================
//     // UTILIDADES
//     // =====================
//     private void resetForm() {
//         produccion = new produccion();
//         recetasSeleccionadas = new ArrayList<>();
//         idProduccionEditar = 0;
//     }

//     private void addMessage(String msg) {
//         FacesContext.getCurrentInstance().addMessage(null,
//                 new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null));
//         System.out.println(msg);
//     }

//     // =====================
// // AGREGAR RECETA AL FORM NUEVA PRODUCCIÓN
// // =====================
//     public void agregarReceta() {
//         if (recetasSeleccionadas == null) {
//             recetasSeleccionadas = new ArrayList<>();
//         }
//         recetasSeleccionadas.add(new produccion_receta()); // agregamos un objeto vacío
//     }

//     public void eliminarReceta(produccion_receta recetaSel) {
//         if (recetasSeleccionadas != null) {
//             recetasSeleccionadas.remove(recetaSel);
//         }
//     }

//     public void cargarProduccion(int id) {
//         produccion = pdao.buscarPorId(id); // ✔ usar el método que sí existe en produccionDao

//         if (produccion == null || produccion.getId_proc() == 0) {
//             addMessage("⚠️ No se encontró la producción.");
//         } else {
//             // cargar recetas asociadas para edición
//             recetasSeleccionadas = pdao.listarRecetasPorProduccion(produccion.getId_proc());
//         }
//     }

// }
