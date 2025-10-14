package modelo;

import java.io.Serializable;

public class recetas implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id_rec;

    private String nombre;
    private String descripcion;
    private String estado; // "Activo" o "Inactivo"

    // Constructores
    public recetas() {}

    public recetas(int id_rec, String nombre, String descripcion, String estado) {
        this.id_rec = id_rec;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    // Getters y Setters
// Getter
public Integer getId_rec() {
    return id_rec;
}

// Setter
public void setId_rec(Integer id_rec) {
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
