package models;

public class insumos {
    private int id_ins;
    private String nombre;
    private double cantidad;
    private String unidad_medida;
    private double stock_min;

    public insumos() {
    }

    public insumos(int id_ins, String nombre, double cantidad, String unidad_medida, double stock_min) {
        this.id_ins = id_ins;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.unidad_medida = unidad_medida;
        this.stock_min = stock_min;
    }

    public int getId_ins() {
        return id_ins;
    }

    public void setId_ins(int id_ins) {
        this.id_ins = id_ins;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidad_medida() {
        return unidad_medida;
    }

    public void setUnidad_medida(String unidad_medida) {
        this.unidad_medida = unidad_medida;
    }

    public double getStock_min() {
        return stock_min;
    }

    public void setStock_min(double stock_min) {
        this.stock_min = stock_min;
    }

    @Override
    public String toString() {
        return "insumos{" +
                "id_ins=" + id_ins +
                ", nombre='" + nombre + '\'' +
                ", cantidad=" + cantidad +
                ", unidad_medida='" + unidad_medida + '\'' +
                ", stock_min=" + stock_min +
                '}';
    }
}
