package modelo;

import java.io.Serializable;

public class venta_produccion implements Serializable {

    private int idVenProd;
    private int idVenta;
    private int idProduccion;
    private int cantidad;

    private String nombreProduccion;
    private String nombreVenta;

    public venta_produccion() {
    }

    public int getIdVenProd() {
        return idVenProd;
    }

    public void setIdVenProd(int idVenProd) {
        this.idVenProd = idVenProd;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdProduccion() {
        return idProduccion;
    }

    public void setIdProduccion(int idProduccion) {
        this.idProduccion = idProduccion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombreProduccion() {
        return nombreProduccion;
    }

    public void setNombreProduccion(String nombreProduccion) {
        this.nombreProduccion = nombreProduccion;
    }

    public String getNombreVenta() {
        return nombreVenta;
    }

    public void setNombreVenta(String nombreVenta) {
        this.nombreVenta = nombreVenta;
    }
}
