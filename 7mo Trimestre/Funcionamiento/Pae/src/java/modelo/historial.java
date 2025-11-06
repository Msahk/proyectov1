package modelo;

import java.io.Serializable;
import java.util.Date;

public class historial implements Serializable {

    private static final long serialVersionUID = 1L;

    public int idHist;
    public Date fecha;
    public String accion;
    public String novedad;
    public int id_ins;
    public Integer id_detalle;
    public String nombre_insumo; // para mostrar el nombre del insumo en listados

    public historial() {

    }

    public historial(int idHist, Date fecha, String accion, String novedad, int id_ins, Integer id_detalle, String nombre_insumo) {
        this.idHist = idHist;
        this.fecha = fecha;
        this.accion = accion;
        this.novedad = novedad;
        this.id_ins = id_ins;
        this.id_detalle = id_detalle;
        this.nombre_insumo = nombre_insumo;
    }

    public int getIdHist() {
        return idHist;
    }

    public void setIdHist(int idHist) {
        this.idHist = idHist;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getNovedad() {
        return novedad;
    }

    public void setNovedad(String novedad) {
        this.novedad = novedad;
    }

    public int getId_ins() {
        return id_ins;
    }

    public void setId_ins(int id_ins) {
        this.id_ins = id_ins;
    }

    public Integer getId_detalle() {
        return id_detalle;
    }

    public void setId_detalle(Integer id_detalle) {
        this.id_detalle = id_detalle;
    }

    public String getNombre_insumo() {
        return nombre_insumo;
    }

    public void setNombre_insumo(String nombre_insumo) {
        this.nombre_insumo = nombre_insumo;
    }

}
