package modelo;

import java.io.Serializable;
import java.util.Date;

public class historial implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idHist;
    private Date fecha;
    private String accion;
    private String estado;      // nuevo campo
    private int cantidad;       // nuevo campo
    private int stockActual;    // nuevo campo
    private String novedad;
    private int id_ins;
    private Integer id_detalle;
    private String nombre_insumo; // para mostrar el nombre del insumo en listados

    public historial() {
    }

    public historial(int idHist, Date fecha, String accion, String estado, int cantidad, int stockActual, String novedad, int id_ins, Integer id_detalle, String nombre_insumo) {
        this.idHist = idHist;
        this.fecha = fecha;
        this.accion = accion;
        this.estado = estado;
        this.cantidad = cantidad;
        this.stockActual = stockActual;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getStockActual() {
        return stockActual;
    }

    public void setStockActual(int stockActual) {
        this.stockActual = stockActual;
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
