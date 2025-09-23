package modelo;

import java.util.Date; // en lugar de java.sql.Date
import java.sql.Timestamp;
import java.util.List;

public class produccion {
    private int id_proc;
    private Date fecha_produccion;
    private String estado;
    private int usuario; // ID del usuario
    private String usuarioNombre; // ← Nuevo campo para mostrar en la vista
    private Timestamp fecha_hora;

    // Lista de recetas asociadas a esta producción
    private List<produccion_receta> recetas;

    public produccion() {}

    public produccion(int id_proc, Date fecha_produccion, String estado, int usuario, String usuarioNombre, Timestamp fecha_hora, List<produccion_receta> recetas) {
        this.id_proc = id_proc;
        this.fecha_produccion = fecha_produccion;
        this.estado = estado;
        this.usuario = usuario;
        this.usuarioNombre = usuarioNombre;
        this.fecha_hora = fecha_hora;
        this.recetas = recetas;
    }

    public int getId_proc() { return id_proc; }
    public void setId_proc(int id_proc) { this.id_proc = id_proc; }

    public Date getFecha_produccion() { return fecha_produccion; }
    public void setFecha_produccion(Date fecha_produccion) { this.fecha_produccion = fecha_produccion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public int getUsuario() { return usuario; }
    public void setUsuario(int usuario) { this.usuario = usuario; }

    public String getUsuarioNombre() { return usuarioNombre; }
    public void setUsuarioNombre(String usuarioNombre) { this.usuarioNombre = usuarioNombre; }

    public Timestamp getFecha_hora() { return fecha_hora; }
    public void setFecha_hora(Timestamp fecha_hora) { this.fecha_hora = fecha_hora; }

    public List<produccion_receta> getRecetas() { return recetas; }
    public void setRecetas(List<produccion_receta> recetas) { this.recetas = recetas; }

    @Override
    public String toString() {
        return "produccion{" +
                "id_proc=" + id_proc +
                ", fecha_produccion=" + fecha_produccion +
                ", estado='" + estado + '\'' +
                ", usuario=" + usuario +
                ", usuarioNombre='" + usuarioNombre + '\'' +
                ", fecha_hora=" + fecha_hora +
                ", recetas=" + recetas +
                '}';
    }
}
