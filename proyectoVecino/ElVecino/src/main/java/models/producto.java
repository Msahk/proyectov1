package models;

public class producto {

    private String idProducto;
    private String nombre;
    private String descripcion;
    private double precio_Unitario;
    private String unidad_medida;
    private int stock_actual;
    private Double pago;

    public producto() {
    }

    public producto(String idProducto, String nombre, String descripcion, double precio_Unitario,
                    String unidad_medida, int stock_actual, Double pago) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio_Unitario = precio_Unitario;
        this.unidad_medida = unidad_medida;
        this.stock_actual = stock_actual;
        this.pago = pago;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio_Unitario() {
        return precio_Unitario;
    }

    public void setPrecio_Unitario(double precio_Unitario) {
        this.precio_Unitario = precio_Unitario;
    }

    public String getUnidad_medida() {
        return unidad_medida;
    }

    public void setUnidad_medida(String unidad_medida) {
        this.unidad_medida = unidad_medida;
    }

    public int getStock_actual() {
        return stock_actual;
    }

    public void setStock_actual(int stock_actual) {
        this.stock_actual = stock_actual;
    }

    public Double getPago() {
        return pago;
    }

    public void setPago(Double pago) {
        this.pago = pago;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "idProducto='" + idProducto + '\'' +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio_Unitario=" + precio_Unitario +
                ", unidad_medida='" + unidad_medida + '\'' +
                ", stock_actual=" + stock_actual +
                ", pago=" + pago +
                '}';
    }
}
