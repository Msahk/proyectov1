package modelo;

import java.io.Serializable;

public class clientes implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id_Cliente;
    private String nombre;
    private String telefono;
    private String correo;

    public clientes() {}

    public clientes(int id_Cliente, String nombre, String telefono, String correo) {
        this.id_Cliente = id_Cliente;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
    }

    public int getId_Cliente() {
        return id_Cliente;
    }

    public void setId_Cliente(int id_Cliente) {
        this.id_Cliente = id_Cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}