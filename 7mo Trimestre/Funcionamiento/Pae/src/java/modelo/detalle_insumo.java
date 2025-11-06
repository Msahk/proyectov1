package modelo;

import java.io.Serializable;
import java.util.Date;

public class detalle_insumo implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id_detalle;
    private int id_ins;
    private double cantidad;
    private Date fecha_ingreso;
    private Date fecha_vencimiento;
    private String estado;
    private String nombre_insumo; // Campo auxiliar para mostrar el nombre del insumo
    // 🔹 Eliminado: unidad_medida (ya pertenece al insumo principal)

    public detalle_insumo() {
        // Constructor vacío (usado por beans y DAO)
    }

    public detalle_insumo(int id_detalle, int id_ins, double cantidad,
                          Date fecha_ingreso, Date fecha_vencimiento,
                          String estado, String nombre_insumo) {
        this.id_detalle = id_detalle;
        this.id_ins = id_ins;
        this.cantidad = cantidad;
        this.fecha_ingreso = fecha_ingreso;
        this.fecha_vencimiento = fecha_vencimiento;
        this.estado = estado;
        this.nombre_insumo = nombre_insumo;
    }

    // ---------- Getters y Setters ----------

    public int getId_detalle() {
        return id_detalle;
    }

    public void setId_detalle(int id_detalle) {
        this.id_detalle = id_detalle;
    }

    public int getId_ins() {
        return id_ins;
    }

    public void setId_ins(int id_ins) {
        this.id_ins = id_ins;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(Date fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public Date getFecha_vencimiento() {
        return fecha_vencimiento;
    }

    public void setFecha_vencimiento(Date fecha_vencimiento) {
        this.fecha_vencimiento = fecha_vencimiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombre_insumo() {
        return nombre_insumo;
    }

    public void setNombre_insumo(String nombre_insumo) {
        this.nombre_insumo = nombre_insumo;
    }
}
