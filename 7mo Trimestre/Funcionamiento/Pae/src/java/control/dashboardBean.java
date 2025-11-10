
package control;

import dao.dashboardDao;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "dashboardBean")
@RequestScoped

public class dashboardBean {
    private final dashboardDao dDao = new dashboardDao();
    
    public int conteoUsuarios() {
        int conteo = dDao.obtenerConteoUsuarios();

        return conteo;
    }
    
    public int conteoProduccion() {
        int conteo = dDao.obtenerConteoProduccion();
        return conteo;
    }
    
    public int conteoVentas() {
        int conteo  = dDao.obtenerConteoVentas();
        return conteo;
    }
    
    
    //TODO LO DE PRODDUCCIONES
    
    public int conteoProduccionPendiente() {
        int conteo = dDao.conteoProduccionesPendientes();
        return conteo;
    }
    
    public int conteoProduccionAceptada() {
        int conteo = dDao.conteoProduccionesAceptada();
        return conteo;
    }
    
    public int conteoProduccionFinalizada() {
        int conteo = dDao.conteoProduccionesFinalizada();
        return conteo;
    }
}
