package modelo;

import java.io.Serializable;
import java.util.Date;

public class produccion implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id_proc;
    private String estado;
    private String usuario;
    private Date fecha_hora;
    private Date fecha_aceptacion;
    private Date fecha_finalizacion;
    private int idReceta;
    private double cantidad;

    public produccion() {
    }

    public produccion(int id_proc, String estado, String usuario, Date fecha_hora,
                      Date fecha_aceptacion, Date fecha_finalizacion,
                      int idReceta, double cantidad) {
        this.id_proc = id_proc;
        this.estado = estado;
        this.usuario = usuario;
        this.fecha_hora = fecha_hora;
        this.fecha_aceptacion = fecha_aceptacion;
        this.fecha_finalizacion = fecha_finalizacion;
        this.idReceta = idReceta;
        this.cantidad = cantidad;
    }

    public int getId_proc() {
        return id_proc;
    }

    public void setId_proc(int id_proc) {
        this.id_proc = id_proc;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Date getFecha_hora() {
        return fecha_hora;
    }

    public void setFecha_hora(Date fecha_hora) {
        this.fecha_hora = fecha_hora;
    }

    public Date getFecha_aceptacion() {
        return fecha_aceptacion;
    }

    public void setFecha_aceptacion(Date fecha_aceptacion) {
        this.fecha_aceptacion = fecha_aceptacion;
    }

    public Date getFecha_finalizacion() {
        return fecha_finalizacion;
    }

    public void setFecha_finalizacion(Date fecha_finalizacion) {
        this.fecha_finalizacion = fecha_finalizacion;
    }

    public int getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(int idReceta) {
        this.idReceta = idReceta;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }
}
