package control;

import dao.usuariosDao;
import modelo.usuarios;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "loginBean")
@RequestScoped
public class loginBean implements Serializable {

    private String correo;
    private String password;

    private usuarios usuarioLogueado;

    // ✅ Inyectamos el bean de sesión
    @ManagedProperty(value = "#{sessionUser}")
    private SessionUserBean sessionUser;

    public void setSessionUser(SessionUserBean sessionUser) {
        this.sessionUser = sessionUser;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public usuarios getUsuarioLogueado() {
        return usuarioLogueado;
    }

    // 🔹 Método de login
    public String login() {
        try {
            usuariosDao dao = new usuariosDao();
            usuarioLogueado = dao.Validar(correo, password);


            if (usuarioLogueado != null) {
                // ✅ Guardar el usuario en sesión
                sessionUser.setUsuario(usuarioLogueado);

                // Redirigir a la vista principal
                return "/views/Principal/index.xhtml?faces-redirect=true";
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Credenciales incorrectas", "Correo o contraseña inválidos"));
                return null;
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Error en el inicio de sesión", e.getMessage()));
            return null;
        }
    }
}
