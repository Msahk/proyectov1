package models;

public class usuario {
    public String docUsuario;       
    public String nombreUsuario;    
    public String rol;              
    public String password;        
    public String email;           

   
    public usuario() {
    }

    public usuario(String docUsuario, String nombreUsuario, String rol, String password, String email) {
        this.docUsuario = docUsuario;
        this.nombreUsuario = nombreUsuario;
        this.rol = rol;
        this.password = password;
        this.email = email;
    }


    public String getDocUsuario() {
        return docUsuario;
    }

    public void setDocUsuario(String docUsuario) {
        this.docUsuario = docUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "docUsuario='" + docUsuario + '\'' +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", rol='" + rol + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
