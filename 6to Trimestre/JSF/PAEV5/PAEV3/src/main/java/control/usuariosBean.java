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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@ManagedBean
@SessionScoped
public class usuariosBean {

    private final usuariosDao usuDAO = new usuariosDao();
    private usuarios usuario = new usuarios();
    private List<usuarios> lstUsu = new ArrayList<>();

    public void autenticar() {
        try {
            Connection con = ConDB.conectar();
            
            String sql = "SELECT * FROM usuarios WHERE correo = ? AND password = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, usuario.getCorreo());
            String pw = Utilidades.encriptar(usuario.getPassword());
            ps.setString(2, pw);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", rs.getString("correo"));
                switch (rs.getString("rol")) {
                    case "A":
                        FacesContext.getCurrentInstance().getExternalContext().redirect("dashboard.xhtml");
                        break;
                    default:
                        throw new AssertionError();
                }
            } 
        } catch (Exception e) {
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

    public usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(usuarios usuario) {
        this.usuario = usuario;
    }
    
    public List<usuarios> getLstUsu() {
        return lstUsu;
    }

    public void setLstUsu(List<usuarios> lstUsu) {
        this.lstUsu = lstUsu;
    }
    
}
    

