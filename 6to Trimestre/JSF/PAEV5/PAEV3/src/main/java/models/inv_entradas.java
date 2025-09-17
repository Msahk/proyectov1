package models;

import java.sql.Timestamp;

public class inv_entradas {
    private int id_entrada;       // coincide con id_entrada en la tabla
    private int id_ins;           // id del insumo
    private double cantidad;      // decimal(10,2)
    private Timestamp fecha_hora; // datetime
    private String usuario;       // varchar(50)
    private String observacion;   // text

    public inv_entradas() {}

    public inv_entradas(int id_entrada, int id_ins, double cantidad, Timestamp fecha_hora, String usuario, String observacion) {
        this.id_entrada = id_entrada;
        this.id_ins = id_ins;
        this.cantidad = cantidad;
        this.fecha_hora = fecha_hora;
        this.usuario = usuario;
        this.observacion = observacion;
    }

    public int getId_entrada() { return id_entrada; }
    public void setId_entrada(int id_entrada) { this.id_entrada = id_entrada; }

    public int getId_ins() { return id_ins; }
    public void setId_ins(int id_ins) { this.id_ins = id_ins; }

    public double getCantidad() { return cantidad; }
    public void setCantidad(double cantidad) { this.cantidad = cantidad; }

    public Timestamp getFecha_hora() { return fecha_hora; }
    public void setFecha_hora(Timestamp fecha_hora) { this.fecha_hora = fecha_hora; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }

    @Override
    public String toString() {
        return "inv_entradas{" +
                "id_entrada=" + id_entrada +
                ", id_ins=" + id_ins +
                ", cantidad=" + cantidad +
                ", fecha_hora=" + fecha_hora +
                ", usuario='" + usuario + '\'' +
                ", observacion='" + observacion + '\'' +
                '}';
    }
}
