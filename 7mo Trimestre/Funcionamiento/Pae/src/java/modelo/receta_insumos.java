package modelo;

import java.io.Serializable;

public class receta_insumos implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id_rec_ins;
    private int id_rec;
    private Integer id_ins;
    private double cantidad;
    private String unidad;

    private recetas receta;
    private insumos insumo;

    public receta_insumos() {
    }

    public receta_insumos(int id_rec_ins, int id_rec, Integer id_ins, double cantidad, String unidad) {
        this.id_rec_ins = id_rec_ins;
        this.id_rec = id_rec;
        this.id_ins = id_ins;
        this.cantidad = cantidad;
        this.unidad = unidad;
    }

    // Getters y Setters existentes
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

    public Integer getId_ins() {
        return id_ins;
    }

    public void setId_ins(Integer id_ins) {
        this.id_ins = id_ins;
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

    // 🔹 Getters y Setters nuevos
    public recetas getReceta() {
        return receta;
    }

    public void setReceta(recetas receta) {
        this.receta = receta;
    }

    public insumos getInsumo() {
        return insumo;
    }

    public void setInsumo(insumos insumo) {
        this.insumo = insumo;
    }
}
