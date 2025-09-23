package modelo;

public class produccion_receta {
    private int id_proc;
    private int id_rec;
    private int cantidad;
    private String nombreReceta; // ← Nuevo campo para mostrar nombre en la vista

    public produccion_receta() {}

    public produccion_receta(int id_proc, int id_rec, int cantidad, String nombreReceta) {
        this.id_proc = id_proc;
        this.id_rec = id_rec;
        this.cantidad = cantidad;
        this.nombreReceta = nombreReceta;
    }

    public int getId_proc() { return id_proc; }
    public void setId_proc(int id_proc) { this.id_proc = id_proc; }

    public int getId_rec() { return id_rec; }
    public void setId_rec(int id_rec) { this.id_rec = id_rec; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public String getNombreReceta() { return nombreReceta; }
    public void setNombreReceta(String nombreReceta) { this.nombreReceta = nombreReceta; }

    @Override
    public String toString() {
        return "produccion_receta{" +
                "id_proc=" + id_proc +
                ", id_rec=" + id_rec +
                ", cantidad=" + cantidad +
                ", nombreReceta='" + nombreReceta + '\'' +
                '}';
    }
}
