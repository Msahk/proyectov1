package models;


public class detalle_venta{
    public int id_detalle;
    public int id_ven;
    public int id_proc;
    public int cantidad;
    public String nombreProducto;

    public detalle_venta() {
    }

    public detalle_venta(int id_detalle, int id_ven, int id_proc, int cantidad, String nombteProducto) {
        this.id_detalle = id_detalle;
        this.id_ven = id_ven;
        this.id_proc = id_proc;
        this.cantidad = cantidad;
        this.nombreProducto = nombreProducto;
    }

    
    public int getId_detalle() {
        return id_detalle;
    }

    public void setId_detalle(int id_detalle) {
        this.id_detalle = id_detalle;
    }

    public int getId_ven() {
        return id_ven;
    }

    public void setId_ven(int id_ven) {
        this.id_ven = id_ven;
    }

    public int getId_proc() {
        return id_proc;
    }

    public void setId_proc(int id_proc) {
        this.id_proc = id_proc;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }
    
    
}

