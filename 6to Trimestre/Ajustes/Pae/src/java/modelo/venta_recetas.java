package modelo;

import java.io.Serializable;

public class venta_recetas implements Serializable {
    private int idVentaReceta;
    private int idVenta;
    private int idReceta;
    private int cantidad;

    // Campos opcionales para mostrar en tablas (JOINs)
    private String nombreReceta;
    private String nombreVenta; // opcional si se usa en vista

    public venta_recetas() {
    }

    public int getIdVentaReceta() {
        return idVentaReceta;
    }

    public void setIdVentaReceta(int idVentaReceta) {
        this.idVentaReceta = idVentaReceta;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(int idReceta) {
        this.idReceta = idReceta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombreReceta() {
        return nombreReceta;
    }

    public void setNombreReceta(String nombreReceta) {
        this.nombreReceta = nombreReceta;
    }

    public String getNombreVenta() {
        return nombreVenta;
    }

    public void setNombreVenta(String nombreVenta) {
        this.nombreVenta = nombreVenta;
    }
}
