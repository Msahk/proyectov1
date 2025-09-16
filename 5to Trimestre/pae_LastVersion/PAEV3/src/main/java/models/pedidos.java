package models;

/**
 * Clase modelo para representar un pedido
 * @author Esteban
 */
public class pedidos {
    private int id_ped;
    private int id_ven;
    private String fecha_entrega;
    private String estado;
    private String observaciones_pedido;
    
    // Constructor vacío
    public pedidos() {
    }
    
    // Constructor con parámetros
    public pedidos(int id_ped, int id_ven, String fecha_entrega, String estado, String observaciones_pedido) {
        this.id_ped = id_ped;
        this.id_ven = id_ven;
        this.fecha_entrega = fecha_entrega;
        this.estado = estado;
        this.observaciones_pedido = observaciones_pedido;
    }
    
    // Getters y Setters
    public int getId_ped() {
        return id_ped;
    }
    
    public void setId_ped(int id_ped) {
        this.id_ped = id_ped;
    }
    
    public int getId_ven() {
        return id_ven;
    }
    
    public void setId_ven(int id_ven) {
        this.id_ven = id_ven;
    }
    
    public String getFecha_entrega() {
        return fecha_entrega;
    }
    
    public void setFecha_entrega(String fecha_entrega) {
        this.fecha_entrega = fecha_entrega;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getObservaciones_pedido() {
        return observaciones_pedido;
    }
    
    public void setObservaciones_pedido(String observaciones_pedido) {
        this.observaciones_pedido = observaciones_pedido;
    }
    
    @Override
    public String toString() {
        return "pedidos{" +
                "id_ped=" + id_ped +
                ", id_ven=" + id_ven +
                ", fecha_entrega='" + fecha_entrega + '\'' +
                ", estado='" + estado + '\'' +
                ", observaciones_pedido='" + observaciones_pedido + '\'' +
                '}';
    }
}