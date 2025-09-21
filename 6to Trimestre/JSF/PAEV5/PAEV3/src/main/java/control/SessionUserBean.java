import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import models.usuarios;

@ManagedBean(name = "sessionUser")
@SessionScoped
public class SessionUserBean implements Serializable {
    private usuarios usuario;

    public usuarios getUsuario() { return usuario; }
    public void setUsuario(usuarios usuario) { this.usuario = usuario; }

    public boolean isLogged() { return usuario != null; }

    public void logout() {
        usuario = null;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }
}
