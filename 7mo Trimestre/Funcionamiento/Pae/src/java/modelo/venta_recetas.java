package modelo;

import java.io.Serializable;

public class venta_recetas implements Serializable {

    private int idVentaReceta;
    private int idVenta;
    private int idReceta;
    private int cantidad;

    // 🔹 Nuevos campos para trazabilidad
    private double precio;
    private double subtotal;

    // 🔹 Campos adicionales (para mostrar nombres)
    private String nombreReceta;
    private String nombreVenta;

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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
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
