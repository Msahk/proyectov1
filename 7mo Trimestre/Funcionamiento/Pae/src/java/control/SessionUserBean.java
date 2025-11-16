package control;

import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import modelo.usuarios;

@ManagedBean(name = "sessionUser")
@SessionScoped
public class SessionUserBean implements Serializable {

    private usuarios usuario;

    public usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(usuarios usuario) {
    this.usuario = usuario;
    if (usuario != null) {
        System.out.println("DEBUG >>> Usuario guardado en sesión: " 
            + usuario.getNombres() + " (ID=" + usuario.getIdUsu() + ")");
    }
}

    public boolean isLogged() {
        return usuario != null;
    }
    
    public int getIdUsu() {
        return usuario.getIdUsu();  
    }
    
    
    public String getNombres() {
        return usuario != null ? usuario.getNombres() : "";
    }

    public String getApellidos() {
        return usuario != null ? usuario.getApellidos() : "";
    }

    public String getRol() {
        return usuario != null ? usuario.getRol() : "";
    }
    
    public String getCorreo() {
        return usuario != null ? usuario.getCorreo(): "";
    }
    
    public long getTelefono() {
        return usuario != null ? usuario.getTelefono(): 0;
    }
    
    

    
    
    
    
    
    
    
    public void logout() {
        usuario = null;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }

public void checkLogin(ComponentSystemEvent event) throws IOException {
        if (!isLogged()) {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        ec.redirect(ec.getRequestContextPath() + "/index.xhtml"); // ajusta ruta
        fc.responseComplete();
    }
}

    

}
