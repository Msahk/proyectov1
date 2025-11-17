package Control;

import dao.usuariosDao;
import modelo.usuarios;
import java.io.Serializable;
import java.security.MessageDigest;
import java.util.Random;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "recuperarBean")
@SessionScoped
public class RecuperarBean implements Serializable {

    private String correo;
    private String codigo;
    private String codigoGenerado;
    private String pass1;
    private String pass2;

    private usuarios user;
    private usuariosDao uDao = new usuariosDao();

    // ================================
    // 1. Enviar código
    // ================================
    public String enviarCodigo() {

        user = uDao.buscarPorCorreo(correo);

        if (user == null) {
            msg("No existe una cuenta con este correo.");
            return null;
        }

        // Generar código de 6 dígitos
        codigoGenerado = String.format("%06d", new Random().nextInt(999999));

        // Simulación de envío real
        System.out.println("Código enviado a " + correo + ": " + codigoGenerado);

        msg("Se ha enviado un código a tu correo.");

        return "codigo?faces-redirect=true";
    }

    // ================================
    // 2. Validar código
    // ================================
    public String validarCodigo() {

        if (!codigo.equals(codigoGenerado)) {
            msg("El código es incorrecto.");
            return null;
        }

        msg("Código validado correctamente.");

        return "nuevaClave?faces-redirect=true";
    }

    // ================================
    // 3. Guardar nueva contraseña
    // ================================
    public String actualizarPassword() {

        if (!pass1.equals(pass2)) {
            msg("Las contraseñas no coinciden.");
            return null;
        }

        try {
            String hash = sha256(pass1);

            user.setPassword(hash);   // ✔ campo correcto del modelo
            uDao.actualizarPassword(user); // ✔ DAO corregido

            msg("Contraseña actualizada correctamente.");

            return "login?faces-redirect=true";

        } catch (Exception e) {
            msg("Error al actualizar: " + e.getMessage());
        }

        return null;
    }

    // ================================
    // SHA-256
    // ================================
    public String sha256(String base) throws Exception {

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(base.getBytes("UTF-8"));

        StringBuilder hex = new StringBuilder();

        for (byte b : hash) {
            String hexChar = Integer.toHexString(0xff & b);
            if (hexChar.length() == 1) hex.append('0');
            hex.append(hexChar);
        }

        return hex.toString();
    }

    // ================================
    // Mensajes
    // ================================
    private void msg(String texto) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, texto, null));
    }

    // ================================
    // Getters & Setters
    // ================================
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getPass1() { return pass1; }
    public void setPass1(String pass1) { this.pass1 = pass1; }

    public String getPass2() { return pass2; }
    public void setPass2(String pass2) { this.pass2 = pass2; }
}
