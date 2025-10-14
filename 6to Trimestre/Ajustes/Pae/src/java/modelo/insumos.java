package modelo;

import java.io.Serializable;
import java.util.Date;

public class insumos implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id_ins;
    private String nombre;
    private double cantidad;
    private String unidad_medida;
    private double stock_min;
    private double stock_actual;
    private Date fecha_vencimiento;
    private String estado;

    public insumos() {
    }

    public insumos(int id_ins, String nombre, double cantidad, String unidad_medida, 
                   double stock_min, double stock_actual, Date fecha_vencimiento, String estado) {
        this.id_ins = id_ins;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.unidad_medida = unidad_medida;
        this.stock_min = stock_min;
        this.stock_actual = stock_actual;
        this.fecha_vencimiento = fecha_vencimiento;
        this.estado = estado;
    }

    // 🔹 Getters y Setters
    public int getId_ins() {
        return id_ins;
    }

    public void setId_ins(int id_ins) {
        this.id_ins = id_ins;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidad_medida() {
        return unidad_medida;
    }

    public void setUnidad_medida(String unidad_medida) {
        this.unidad_medida = unidad_medida;
    }

    public double getStock_min() {
        return stock_min;
    }

    public void setStock_min(double stock_min) {
        this.stock_min = stock_min;
    }

    public double getStock_actual() {
        return stock_actual;
    }

    public void setStock_actual(double stock_actual) {
        this.stock_actual = stock_actual;
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
}
