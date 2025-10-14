package modelo;
import java.io.Serializable;
import java.util.Date;

public class inv_entradas implements Serializable {
    private static final long serialVersionUID = 1L;

    public int id_entrada;
    public int id_ins;
    public double cantidad;
    public Date fecha_hora;
    public String usuario;
    public String observacion;

    public inv_entradas() {}

    public inv_entradas(int id_entrada, int id_ins, double cantidad, Date fecha_hora, String usuario, String observacion) {
        this.id_entrada = id_entrada;
        this.id_ins = id_ins;
        this.cantidad = cantidad;
        this.fecha_hora = fecha_hora;
        this.usuario = usuario;
        this.observacion = observacion;
    }

    public int getId_entrada() {
        return id_entrada;
    }

    public void setId_entrada(int id_entrada) {
        this.id_entrada = id_entrada;
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

    public Date getFecha_hora() {
        return fecha_hora;
    }

    public void setFecha_hora(Date fecha_hora) {
        this.fecha_hora = fecha_hora;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
