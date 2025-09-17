package beans;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import models.inv_salidas;
import models.produccionDao;   // 🔹 usamos produccionDao

@ManagedBean(name = "invSalidasBean")
@RequestScoped
public class inv_salidasBean implements Serializable {

    private List<inv_salidas> listaSalidas;
    private produccionDao dao;   // 🔹 apuntamos a produccionDao

    public inv_salidasBean() {
        dao = new produccionDao();
    }

    @PostConstruct
    public void init() {
        listar();
    }

    // 🔹 Listar salidas desde produccionDao
    public void listar() {
        listaSalidas = dao.listarSalidas();  
    }

    // 🔹 Eliminar salida desde produccionDao
    public void eliminar(int id) {
        dao.eliminarSalida(id);
        listar(); // refrescar lista
    }

    // Getters y Setters
    public List<inv_salidas> getListaSalidas() {
        return listaSalidas;
    }

    public void setListaSalidas(List<inv_salidas> listaSalidas) {
        this.listaSalidas = listaSalidas;
    }
}
