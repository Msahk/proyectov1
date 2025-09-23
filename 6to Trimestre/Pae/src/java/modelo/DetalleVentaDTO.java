package modelo;

public class DetalleVentaDTO {
    private String nombreEmpanada;
    private int cantidad;

    public String getNombreEmpanada() {
        return nombreEmpanada;
    }
    public void setNombreEmpanada(String nombreEmpanada) {
        this.nombreEmpanada = nombreEmpanada;
    }
    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}