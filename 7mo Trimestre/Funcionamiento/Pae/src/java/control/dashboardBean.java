
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
    
    //TODO LO QUE SEA VENTAS
    
    public int conteoVentasPagoPendiente() {
        int conteo = dDao.conteoVentasPagoPendiente();
        return conteo;
    }
    
    public int conteoVentaPagoCompleto() {
        int conteo = dDao.conteoVentasPagoCompleto();
        return conteo;
    }
    
    public int conteoVentaProcesando() {
        int conteo = dDao.conteoVentasProcesando();
        return conteo;
    }
    
    public int conteoVentaCompletada() {
        int conteo = dDao.conteoVentasCompletado();
        return conteo;
    }
    
    
    //TODO LO DE RECETAS
    
    public int conteoRecetasTotales() {
        int conteo = dDao.conteoRecetasTotales();
        return conteo;
    }
    
    public int conteoRecetasInactivas() {
        int conteo = dDao.conteoRecetasInactivas();
        return conteo;
    }
    
    public double porcentajeInactivas() {
        double porcentaje = (dDao.conteoRecetasInactivas()*100)/dDao.conteoRecetasTotales();
        return porcentaje;
    }
}
