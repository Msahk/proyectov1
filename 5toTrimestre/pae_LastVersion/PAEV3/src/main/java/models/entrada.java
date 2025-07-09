package models;

import java.sql.Date;

public class entrada {
    private int id_ent;
    private Date fecha;
    private int cantidad;
    private int id_ins;

    public entrada() {}

    public entrada(int id_ent, Date fecha, int cantidad, int id_ins) {
        this.id_ent = id_ent;
        this.fecha = fecha;
        this.cantidad = cantidad;
        this.id_ins = id_ins;
    }

    public int getId_ent() {
        return id_ent;
    }

    public void setId_ent(int id_ent) {
        this.id_ent = id_ent;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getId_ins() {
        return id_ins;
    }

    public void setId_ins(int id_ins) {
        this.id_ins = id_ins;
    }
}
