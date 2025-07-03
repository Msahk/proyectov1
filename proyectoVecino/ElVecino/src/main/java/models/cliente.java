package models;

import java.util.Date;

public class cliente {

    private String docCliente;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String direccion;
    private String categoriaCrediticia; 
    private Date fechaRegistro;
    private int limite_creditos;
    private int creditos_actuales;
    private String docUsuario;

    public cliente() {
    }

    public cliente(String docCliente, String nombre, String apellidos, String telefono, String direccion,
                   String categoriaCrediticia, Date fechaRegistro, int limite_creditos,
                   int creditos_actuales, String docUsuario) {
        this.docCliente = docCliente;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.direccion = direccion;
        this.categoriaCrediticia = categoriaCrediticia;
        this.fechaRegistro = fechaRegistro;
        this.limite_creditos = limite_creditos;
        this.creditos_actuales = creditos_actuales;
        this.docUsuario = docUsuario;
    }


    public String getDocCliente() {
        return docCliente;
    }

    public void setDocCliente(String docCliente) {
        this.docCliente = docCliente;
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

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public int getLimite_creditos() {
        return limite_creditos;
    }

    public void setLimite_creditos(int limite_creditos) {
        this.limite_creditos = limite_creditos;
    }

    public int getCreditos_actuales() {
        return creditos_actuales;
    }

    public void setCreditos_actuales(int creditos_actuales) {
        this.creditos_actuales = creditos_actuales;
    }

    public String getDocUsuario() {
        return docUsuario;
    }

    public void setDocUsuario(String docUsuario) {
        this.docUsuario = docUsuario;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "docCliente='" + docCliente + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                ", categoriaCrediticia='" + categoriaCrediticia + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                ", limite_creditos=" + limite_creditos +
                ", creditos_actuales=" + creditos_actuales +
                ", docUsuario='" + docUsuario + '\'' +
                '}';
    }
}
