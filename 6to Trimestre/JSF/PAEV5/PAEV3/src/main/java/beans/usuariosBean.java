package beans;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import models.usuarios;
import dao.usuariosDao;

@ManagedBean
@SessionScoped
public class usuariosBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private usuariosDao u_dao;
    private usuarios usu;

    private String correo;
    private String password;
    private String documento;
    private String nuevaPassword;

    private List<usuarios> listaUsuarios;

    @PostConstruct
    public void init() {
        u_dao = new usuariosDao();
        usu = new usuarios();
        try {
            listaUsuarios = u_dao.listar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ====== LOGIN ======
    public String login() {
    System.out.println("=== MÉTODO LOGIN EJECUTADO ===");
    System.out.println("Correo recibido: " + correo);
    System.out.println("Password recibido: " + password);
    
    // USUARIO DE PRUEBA TEMPORAL
    if ("esteban@gmail.com".equals(correo) && "123".equals(password)) {
        System.out.println("Usuario de prueba - redirigiendo");
        // Crear usuario temporal
        usu = new usuarios();
        usu.setCorreo(correo);
        usu.setEstado("A");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuarios", usu);
        return "views/dashboard.xhtml?faces-redirect=true";
    }
    
    // Lógica normal con base de datos
    try {
        usu = u_dao.Validar(correo, password);
        System.out.println("Usuario encontrado: " + (usu != null ? usu.getCorreo() : "null"));
    } catch (Exception e) {
        System.out.println("Error en validación: " + e.getMessage());
        e.printStackTrace();
    }

    if (usu != null && usu.getCorreo() != null && usu.getPassword() != null) {
        System.out.println("Estado del usuario: " + usu.getEstado());
        
        if ("I".equalsIgnoreCase(usu.getEstado())) {
            System.out.println("Usuario inactivo - mostrando mensaje");
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Usuario inactivo"));
            return null;
        } else if ("A".equalsIgnoreCase(usu.getEstado())) {
            System.out.println("Usuario activo - guardando en sesión y redirigiendo");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuarios", usu);
            return "views/dashboard.xhtml?faces-redirect=true";
        }
    } else {
        System.out.println("Credenciales incorrectas");
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Usuario o contraseña incorrectos"));
        return null;
    }
    
    System.out.println("Fin del método - retornando null");
    return null;
}


    // ====== REGISTRAR USUARIO ======
    public void agregar() {
        usuarios nuevo = new usuarios();
        nuevo.setDocumento(Integer.parseInt(documento));
        nuevo.setCorreo(correo);
        nuevo.setPassword(password);
        
        u_dao.agregar(nuevo);
        listaUsuarios = u_dao.listar();
    }
public String logout() {
    try {
        // Limpiar la sesión
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        // Limpiar variables locales
        correo = null;
        password = null;
        usu = null;
        // Redirigir al login
        return "/index.xhtml?faces-redirect=true";
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}
    // ====== RECUPERAR / CAMBIAR CONTRASEÑA ======
    public void resetPassword() {
        try {
            int doc = Integer.parseInt(documento);
            usu = u_dao.olvidar(correo, doc);
            if (usu != null) {
                u_dao.actualizarContra(usu.getId_usu(), nuevaPassword);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Contraseña actualizada correctamente"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Correo o documento no encontrados"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ====== LISTAR USUARIOS ======
    public List<usuarios> getListaUsuarios() {
        return listaUsuarios;
    }

    // ====== GETTERS Y SETTERS ======
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

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNuevaPassword() {
        return nuevaPassword;
    }

    public void setNuevaPassword(String nuevaPassword) {
        this.nuevaPassword = nuevaPassword;
    }

    public usuarios getUsu() {
        return usu;
    }

    public void setUsu(usuarios usu) {
        this.usu = usu;
    }
}
