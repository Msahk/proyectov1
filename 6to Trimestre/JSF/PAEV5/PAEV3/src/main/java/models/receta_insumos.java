package models;

public class receta_insumos {
    
    private int id_rec_ins;       // ID único de la relación (PK)
    private int id_rec;           // ID de la receta
    private int id_ins;           // ID del insumo
    private String nombre_insumo; // Nombre del insumo (JOIN con insumos)
    private double cantidad;      // Cantidad requerida
    private String unidad;        // Unidad de medida (g, ml, etc.)

    // 🔹 Constructor vacío
    public receta_insumos() {
    }

    // 🔹 Constructor con parámetros
    public receta_insumos(int id_rec_ins, int id_rec, int id_ins, String nombre_insumo, double cantidad, String unidad) {
        this.id_rec_ins = id_rec_ins;
        this.id_rec = id_rec;
        this.id_ins = id_ins;
        this.nombre_insumo = nombre_insumo;
        this.cantidad = cantidad;
        this.unidad = unidad;
    }

    // 🔹 Getters & Setters
    public int getId_rec_ins() {
        return id_rec_ins;
    }

    public void setId_rec_ins(int id_rec_ins) {
        this.id_rec_ins = id_rec_ins;
    }

    public int getId_rec() {
        return id_rec;
    }

    public void setId_rec(int id_rec) {
        this.id_rec = id_rec;
    }

    public int getId_ins() {
        return id_ins;
    }

    public void setId_ins(int id_ins) {
        this.id_ins = id_ins;
    }

    public String getNombre_insumo() {
        return nombre_insumo;
    }

    public void setNombre_insumo(String nombre_insumo) {
        this.nombre_insumo = nombre_insumo;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }
}
