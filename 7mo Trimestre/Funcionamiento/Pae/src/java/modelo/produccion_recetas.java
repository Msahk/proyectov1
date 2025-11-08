package modelo;

import java.io.Serializable;

public class produccion_recetas implements Serializable {

    private static final long serialVersionUID = 1L;

    public int id_detalle;
    public int id_produccion;
    public int id_rec;
    public int cantidad;

    public produccion_recetas() {
    }

    public produccion_recetas(int id_detalle, int id_produccion, int id_rec, int cantidad) {
        this.id_detalle = id_detalle;
        this.id_produccion = id_produccion;
        this.id_rec = id_rec;
        this.cantidad = cantidad;
    }

    public int getId_detalle() {
        return id_detalle;
    }

    public void setId_detalle(int id_detalle) {
        this.id_detalle = id_detalle;
    }

    public int getId_produccion() {
        return id_produccion;
    }

    public void setId_produccion(int id_produccion) {
        this.id_produccion = id_produccion;
    }

    public int getId_rec() {
        return id_rec;
    }

    public void setId_rec(int id_rec) {
        this.id_rec = id_rec;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
