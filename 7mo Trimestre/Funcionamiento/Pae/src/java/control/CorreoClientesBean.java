package control;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import modelo.clientes;

@ManagedBean
@ViewScoped
public class CorreoClientesBean {
    private String asunto, contenido;
    private List<String> dest;
    private List<clientes> listaClientes;

    public void listarClientes() {
        listaClientes = new ArrayList<>();
        try {
            String sql = "SELECT * FROM clientes";
            PreparedStatement ps = ConDB.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                clientes c = new clientes();
                c.setId_Cliente(rs.getInt("id_Cliente"));
                c.setNombre(rs.getString("nombre"));
                c.setCorreo(rs.getString("correo"));
                listaClientes.add(c);
            }
        } catch (SQLException e) {}
    }

    public void enviarCorreo() {
        final String user = "pcorreosjava@gmail.com"; 
        final String pass = "iibc kfrf xfsn lppw";   

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session sesion = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });

        try {
            Message mensaje = new MimeMessage(sesion);
            mensaje.setFrom(new InternetAddress(user));
            InternetAddress[] dests = new InternetAddress[dest.size()];
            int i = 0;
            Iterator itr = dest.iterator();
            while (itr.hasNext()) {
                InternetAddress ndir = new InternetAddress(itr.next().toString());
                dests[i] = ndir;
                i++;
            }
            mensaje.setRecipients(Message.RecipientType.TO, dests);
            mensaje.setSubject(asunto);
            mensaje.setText(contenido);

            Transport.send(mensaje);

            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Mensaje enviado exitosamente"));

        } catch (MessagingException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Error enviando mensaje"));
        }
    }

    
    public String getAsunto() { return asunto; }
    public void setAsunto(String asunto) { this.asunto = asunto; }
    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }
    public List<String> getDest() { return dest; }
    public void setDest(List<String> dest) { this.dest = dest; }
    public List<clientes> getListaClientes() { return listaClientes; }
    public void setListaClientes(List<clientes> listaClientes) { this.listaClientes = listaClientes; }
}