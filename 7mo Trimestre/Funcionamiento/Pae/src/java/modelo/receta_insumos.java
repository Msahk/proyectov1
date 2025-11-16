package modelo;

import java.io.Serializable;

public class receta_insumos implements Serializable {

    private static final long serialVersionUID = 1L;

    // 🔹 Atributos principales
    private int id_rec_ins;
    private int id_rec;
    private int id_ins;
    private double cantidad;
    private String unidad;
    private String estado; // ENUM('Activo', 'Agotado')

    // 🔹 Relaciones (opcional para mostrar nombres o datos relacionados)
    private recetas receta;
    private insumos insumo;

    // 🔹 Constructores
    public receta_insumos() {
    }

    public receta_insumos(int id_rec_ins, int id_rec, int id_ins, double cantidad, String unidad, String estado) {
        this.id_rec_ins = id_rec_ins;
        this.id_rec = id_rec;
        this.id_ins = id_ins;
        this.cantidad = cantidad;
        this.unidad = unidad;
        this.estado = estado;
    }

    // 🔹 Getters y Setters
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

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
