package modelo;

import java.io.Serializable;
import java.util.Date;

public class produccion implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id_proc;
    private String estado;
    private Date fecha_hora;
    private Date fecha_aceptacion;
    private Date fecha_finalizacion;
    private int idReceta;
    private double cantidad;

    // 🔗 Relación con usuarios
    private int id_usu;          // Usuario que crea la producción
    private int id_asignado;     // Usuario asignado para ejecutar la producción

    // 🧍 Datos auxiliares (para mostrar nombres en la tabla)
    private String nombreUsuario;         // Nombre del usuario que la creó
    private String apellidoUsuario;
    private String nombreAsignado;        // Nombre del empleado asignado
    private String apellidoAsignado;

    public produccion() {
    }

    public produccion(int id_proc, String estado, Date fecha_hora,
                      Date fecha_aceptacion, Date fecha_finalizacion,
                      int idReceta, double cantidad, int id_usu, int id_asignado) {
        this.id_proc = id_proc;
        this.estado = estado;
        this.fecha_hora = fecha_hora;
        this.fecha_aceptacion = fecha_aceptacion;
        this.fecha_finalizacion = fecha_finalizacion;
        this.idReceta = idReceta;
        this.cantidad = cantidad;
        this.id_usu = id_usu;
        this.id_asignado = id_asignado;
    }

    // 🔹 Getters y Setters

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

    public int getId_usu() {
        return id_usu;
    }

    public void setId_usu(int id_usu) {
        this.id_usu = id_usu;
    }

    public int getId_asignado() {
        return id_asignado;
    }

    public void setId_asignado(int id_asignado) {
        this.id_asignado = id_asignado;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getApellidoUsuario() {
        return apellidoUsuario;
    }

    public void setApellidoUsuario(String apellidoUsuario) {
        this.apellidoUsuario = apellidoUsuario;
    }

    public String getNombreAsignado() {
        return nombreAsignado;
    }

    public void setNombreAsignado(String nombreAsignado) {
        this.nombreAsignado = nombreAsignado;
    }

    public String getApellidoAsignado() {
        return apellidoAsignado;
    }

    public void setApellidoAsignado(String apellidoAsignado) {
        this.apellidoAsignado = apellidoAsignado;
    }
}
