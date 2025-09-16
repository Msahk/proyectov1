package models;

import java.sql.Date;
import java.util.List;

public class produccion {
    private int id_proc;
    private Date fecha_produccion;
    private String tipo;
    private int cantidad;
    private int id_prot;
    private int id_res;
    private String estado;

    private List<detalleProduccion> detalles;

    public produccion() {}

    public produccion(int id_proc, Date fecha_produccion, String tipo, int cantidad, int id_prot, int id_res, String estado) {
        this.id_proc = id_proc;
        this.fecha_produccion = fecha_produccion;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.id_prot = id_prot;
        this.id_res = id_res;
        this.estado = estado;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getId_prot() {
        return id_prot;
    }

    public void setId_prot(int id_prot) {
        this.id_prot = id_prot;
    }

    public int getId_res() {
        return id_res;
    }

    public void setId_res(int id_res) {
        this.id_res = id_res;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<detalleProduccion> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<detalleProduccion> detalles) {
        this.detalles = detalles;
    }

    @Override
    public String toString() {
        return "produccion{" +
                "id_proc=" + id_proc +
                ", fecha_produccion=" + fecha_produccion +
                ", tipo='" + tipo + '\'' +
                ", cantidad=" + cantidad +
                ", id_prot=" + id_prot +
                ", id_res=" + id_res +
                ", estado='" + estado + '\'' +
                ", detalles=" + (detalles != null ? detalles.size() + " insumos" : "sin detalles") +
                '}';
    }
}
