package control;

import beans.*;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import models.usuarios;
import dao.usuariosDao;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.primefaces.PrimeFaces;

@ManagedBean
@SessionScoped
public class usuariosBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
    

    private final usuariosDao usuDAO = new usuariosDao();
    private usuarios usuario = new usuarios();
    private List<usuarios> lstUsu = new ArrayList<>();
    
    public usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(usuarios usuario) {
        this.usuario = usuario;
    }

    public void autenticar() {
    boolean logged = false;
    String message = null;
    String redirectTo = null;

    String sql = "SELECT * FROM usuarios WHERE (correo = ? OR documento = ?) AND password = ?";

    try (
        Connection con = ConDB.conectar();
        PreparedStatement ps = con.prepareStatement(sql)
    ) {
        ps.setString(1, usuario.getCorreo());
        ps.setString(2, usuario.getCorreo());
        String pw = Utilidades.encriptar(usuario.getPassword());
        ps.setString(3, pw);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                usuarios sesUsuario = new usuarios();
                sesUsuario.setCorreo(rs.getString("correo"));
                sesUsuario.setNombres(rs.getString("nombres"));
                sesUsuario.setApellidos(rs.getString("apellidos"));
                sesUsuario.setRol(rs.getString("rol"));

                FacesContext.getCurrentInstance().getExternalContext()
                            .getSessionMap().put("sessionUser", sesUsuario);


                logged = true;
                message = "Bienvenido " + sesUsuario.getNombres() + " " + sesUsuario.getApellidos();
                redirectTo = "views/Dashboard.xhtml"; 
            } else {
                message = "Correo/Documento o contraseña inválidos";
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
        message = "Error interno. Intenta de nuevo.";
    }

    PrimeFaces.current().ajax().addCallbackParam("loggedIn", logged);
    PrimeFaces.current().ajax().addCallbackParam("msg", message);
    PrimeFaces.current().ajax().addCallbackParam("redirect", redirectTo);
    PrimeFaces.current().ajax().addCallbackParam("correoPreserve", usuario.getCorreo());
}


            public void logout() {
            try {
                FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

                FacesContext.getCurrentInstance().getExternalContext().redirect("/PAEV3");
                

                System.out.println("Sesión cerrada y redirigido a index");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    
    
    public void listar() {
        lstUsu = usuDAO.listar();
    }

    public void agregar() {
        usuDAO.agregar(usuario);
    }
    
    public void editar(usuarios usu) {
        usuario = usuDAO.obtenerPorId(usu);
    }
    
    public void actualizar() {
        usuDAO.actualizar(usuario);
    }
    
    public void cambiar(Integer id) {
    usuarios u = new usuarios();
    u.setIdUsu(id);
    boolean ok = usuDAO.cambiarEstado(u);
    if (ok) {
        PrimeFaces.current().executeScript("window.location = '" +
            FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() +
            "/views/Usuarios/Index.xhtml';");
        listar();
    } else {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo cambiar"));
    }
}

    
    public void eliminar(usuarios usu) {
        usuDAO.eliminar(usu);
    }

    public void limpiar(){
        usuario = new usuarios();
    }
    
    public List<usuarios> getLstUsu() {
        return lstUsu;
    }

    public void setLstUsu(List<usuarios> lstUsu) {
        this.lstUsu = lstUsu;
    }
    
}
    

