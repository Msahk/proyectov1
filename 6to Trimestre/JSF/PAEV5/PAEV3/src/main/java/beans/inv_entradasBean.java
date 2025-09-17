// package beans;

// import java.io.Serializable;
// import java.util.List;
// import javax.annotation.PostConstruct;
// import javax.faces.bean.ManagedBean;
// import javax.faces.bean.RequestScoped;

// import models.inv_entradas;
// import models.insumosDao;

// @ManagedBean(name = "invEntradasBean")  // Se usará como #{invEntradasBean}
// @RequestScoped
// public class inv_entradasBean implements Serializable {

//     private List<inv_entradas> listaEntradas;
//     private insumosDao dao;

//     public inv_entradasBean() {
//         dao = new insumosDao();
//     }

//     @PostConstruct
//     public void init() {
//         listar();
//     }

//     // Listar todas las entradas
//     public void listar() {
//         listaEntradas = dao.listarEntradas(); // ya existe en insumosDao
//     }

//     // Eliminar una entrada
//     public void eliminar(int idEntrada) {
//         dao.eliminarEntrada(idEntrada); // ya existe en insumosDao
//         listar(); // refresca la lista
//     }

//     // Getters y setters
//     public List<inv_entradas> getListaEntradas() {
//         return listaEntradas;
//     }

//     public void setListaEntradas(List<inv_entradas> listaEntradas) {
//         this.listaEntradas = listaEntradas;
//     }
// }
