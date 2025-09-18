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

@ManagedBean
@SessionScoped
public class usuariosBean {

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
    
    try {
        Connection con = ConDB.conectar();

        
        String sql = "SELECT * FROM usuarios WHERE correo = ? OR documento = ? AND password = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, usuario.getCorreo());     
        ps.setString(2, usuario.getCorreo());     
        String pw = Utilidades.encriptar(usuario.getPassword());
        ps.setString(3, pw);
        
        System.out.println(pw);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            usuario.setCorreo(rs.getString("correo"));
            usuario.setNombres(rs.getString("nombres"));
            usuario.setApellidos(rs.getString("apellidos"));
            usuario.setRol(rs.getString("rol"));

            FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
                .put("usuario", usuario);

            switch (usuario.getRol()) {
                case "A":
                    FacesContext.getCurrentInstance().getExternalContext().redirect("Dashboard.xhtml");
                    break;
                case "EP":
                    FacesContext.getCurrentInstance().getExternalContext().redirect("Dashboard.xhtml");
                    break;
                case "EV":
                    FacesContext.getCurrentInstance().getExternalContext().redirect("Dashboard.xhtml");
                    break;
                default:
                    System.out.println(">>> Rol desconocido");
                    break;
            }
        } else {
            System.out.println(">>> Credenciales incorrectas");
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_WARN, 
                                 "Correo/Documento o contraseña no válidos", "Aviso"));
        }
    } catch (Exception e) {
        e.printStackTrace();
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                             "Error en la autenticación", "Error"));
    }
}

            public void logout() {
            try {
                FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

                FacesContext.getCurrentInstance().getExternalContext().redirect("../index.xhtml");

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
    

