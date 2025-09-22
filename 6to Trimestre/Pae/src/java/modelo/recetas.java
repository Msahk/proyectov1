package modelo;

public class recetas {
    private int id_rec;
    private String nombre;
    private String descripcion;

    public recetas() {}

    public recetas(int id_rec, String nombre, String descripcion) {
        this.id_rec = id_rec;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getId_rec() {
        return id_rec;
    }

    public void setId_rec(int id_rec) {
        this.id_rec = id_rec;
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
    
    @Override
public String toString() {
    return "recetas{" +
            "id_rec=" + id_rec +
            ", nombre='" + nombre + '\'' +
            ", descripcion='" + descripcion + '\'' +
            '}';
}
}
