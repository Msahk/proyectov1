package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.io.IOException;
import javax.faces.context.FacesContext;

@ManagedBean(name = "logoutBean")
@SessionScoped
public class LogoutBean implements Serializable {

    private static final long serialVersionUID = 1L;

    // Método para cerrar sesión
    public void logout() {
        try {
            // Invalida la sesión actual
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

            // Redirige a la página principal
            FacesContext.getCurrentInstance().getExternalContext()
                        .redirect("index.xhtml");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

