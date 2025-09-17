package models;

import java.sql.Timestamp;

public class inv_salidas {
    private int id_salida;
    private int id_ins;
    private double cantidad;
    private Timestamp fecha_hora;
    private String usuario;
    private int id_proc;        // opcional, nullable
    private String observacion;

    public inv_salidas() {}

    public inv_salidas(int id_salida, int id_ins, double cantidad, Timestamp fecha_hora, String usuario, int id_proc, String observacion) {
        this.id_salida = id_salida;
        this.id_ins = id_ins;
        this.cantidad = cantidad;
        this.fecha_hora = fecha_hora;
        this.usuario = usuario;
        this.id_proc = id_proc;
        this.observacion = observacion;
    }

    public int getId_salida() { return id_salida; }
    public void setId_salida(int id_salida) { this.id_salida = id_salida; }

    public int getId_ins() { return id_ins; }
    public void setId_ins(int id_ins) { this.id_ins = id_ins; }

    public double getCantidad() { return cantidad; }
    public void setCantidad(double cantidad) { this.cantidad = cantidad; }

    public Timestamp getFecha_hora() { return fecha_hora; }
    public void setFecha_hora(Timestamp fecha_hora) { this.fecha_hora = fecha_hora; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public int getId_proc() { return id_proc; }
    public void setId_proc(int id_proc) { this.id_proc = id_proc; }

    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }

    @Override
    public String toString() {
        return "inv_salidas{" +
                "id_salida=" + id_salida +
                ", id_ins=" + id_ins +
                ", cantidad=" + cantidad +
                ", fecha_hora=" + fecha_hora +
                ", usuario='" + usuario + '\'' +
                ", id_proc=" + id_proc +
                ", observacion='" + observacion + '\'' +
                '}';
    }
}
