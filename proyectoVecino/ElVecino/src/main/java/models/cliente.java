package models;

import java.time.LocalDateTime;

public class cliente {
    private int idCliente;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String direccion;
    private String categoriaCrediticia;
    private LocalDateTime fechaRegistro;
    private int limiteCreditos;
    private int creditosActuales;
    private int idUsuario;

    public cliente() {
    }

    public cliente(int idCliente, String nombre, String apellidos, String telefono, String direccion,
                   String categoriaCrediticia, LocalDateTime fechaRegistro,
                   int limiteCreditos, int creditosActuales, int idUsuario) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.direccion = direccion;
        this.categoriaCrediticia = categoriaCrediticia;
        this.fechaRegistro = fechaRegistro;
        this.limiteCreditos = limiteCreditos;
        this.creditosActuales = creditosActuales;
        this.idUsuario = idUsuario;
    }

    // Getters y Setters

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCategoriaCrediticia() {
        return categoriaCrediticia;
    }

    public void setCategoriaCrediticia(String categoriaCrediticia) {
        this.categoriaCrediticia = categoriaCrediticia;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public int getLimiteCreditos() {
        return limiteCreditos;
    }

    public void setLimiteCreditos(int limiteCreditos) {
        this.limiteCreditos = limiteCreditos;
    }

    public int getCreditosActuales() {
        return creditosActuales;
    }

    public void setCreditosActuales(int creditosActuales) {
        this.creditosActuales = creditosActuales;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public String toString() {
        return "cliente{" +
                "idCliente=" + idCliente +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                ", categoriaCrediticia='" + categoriaCrediticia + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                ", limiteCreditos=" + limiteCreditos +
                ", creditosActuales=" + creditosActuales +
                ", idUsuario=" + idUsuario +
                '}';
    }
}
