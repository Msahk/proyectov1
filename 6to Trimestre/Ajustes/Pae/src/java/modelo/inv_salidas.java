package modelo;
import java.io.Serializable;
import java.util.Date;

public class inv_salidas implements Serializable {
    private static final long serialVersionUID = 1L;

    public int id_salida;
    public int id_ins;
    public double cantidad;
    public Date fecha_hora;
    public String usuario;
    public int id_proc;
    public String observacion;

    public inv_salidas() {}

    public inv_salidas(int id_salida, int id_ins, double cantidad, Date fecha_hora, String usuario, int id_proc, String observacion) {
        this.id_salida = id_salida;
        this.id_ins = id_ins;
        this.cantidad = cantidad;
        this.fecha_hora = fecha_hora;
        this.usuario = usuario;
        this.id_proc = id_proc;
        this.observacion = observacion;
    }

    public int getId_salida() {
        return id_salida;
    }

    public void setId_salida(int id_salida) {
        this.id_salida = id_salida;
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

    public int getId_proc() {
        return id_proc;
    }

    public void setId_proc(int id_proc) {
        this.id_proc = id_proc;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
