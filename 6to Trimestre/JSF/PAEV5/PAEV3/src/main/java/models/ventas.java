package models;

import java.util.Date;

public class ventas {
    private int idVen;
    private String tipo; // ENUM
    private Date fecha;
    private int idUsuario;
    private int idCliente;
    private double total;
    private String estado; // ENUM
    private String observaciones;

    // Getters y Setters
    public int getIdVen() {
        return idVen;
    }
    public void setIdVen(int idVen) {
        this.idVen = idVen;
    }

    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}