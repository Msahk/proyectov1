package modelo;

import java.io.Serializable;

public class DetalleVenta implements Serializable {
    private int idDetalle;
    private int idVen;
    private int idReceta;
    private String nombreEmpanada;
    private int cantidad;

    
    public int getIdDetalle() { return idDetalle; }
    public void setIdDetalle(int idDetalle) { this.idDetalle = idDetalle; }

    public int getIdVen() { return idVen; }
    public void setIdVen(int idVen) { this.idVen = idVen; }

    public int getIdReceta() { return idReceta; }
    public void setIdReceta(int idReceta) { this.idReceta = idReceta; }

    public String getNombreEmpanada() { return nombreEmpanada; }
    public void setNombreEmpanada(String nombreEmpanada) { this.nombreEmpanada = nombreEmpanada; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
}