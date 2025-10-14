package modelo;
import java.io.Serializable;
import java.util.Date;

public class produccion implements Serializable {
    private static final long serialVersionUID = 1L;

    public int id_proc;
    public Date fecha_produccion;
    public String estado;
    public int usuario;
    public Date fecha_hora;

    public produccion() {}

    public produccion(int id_proc, Date fecha_produccion, String estado, int usuario, Date fecha_hora) {
        this.id_proc = id_proc;
        this.fecha_produccion = fecha_produccion;
        this.estado = estado;
        this.usuario = usuario;
        this.fecha_hora = fecha_hora;
    }

    public int getId_proc() {
        return id_proc;
    }

    public void setId_proc(int id_proc) {
        this.id_proc = id_proc;
    }

    public Date getFecha_produccion() {
        return fecha_produccion;
    }

    public void setFecha_produccion(Date fecha_produccion) {
        this.fecha_produccion = fecha_produccion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public Date getFecha_hora() {
        return fecha_hora;
    }

    public void setFecha_hora(Date fecha_hora) {
        this.fecha_hora = fecha_hora;
    }
}
