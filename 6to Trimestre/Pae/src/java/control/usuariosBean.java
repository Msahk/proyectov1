package control;

import dao.usuariosDao;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import modelo.usuarios;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.PrimeFaces;

@ManagedBean
@SessionScoped
public class usuariosBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private final usuariosDao usuDAO = new usuariosDao();
    private usuarios usuario = new usuarios();
    private List<usuarios> lstUsu = new ArrayList<>();
    private List<usuarios> lstUsuFiltrados;

    Part excel;
    
    public void exportarPDF() throws IOException {
        try {
            String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/usuarios.jasper");
            File jasper = new File(path);
            usuariosDataSource uds = new usuariosDataSource();
            
            JasperPrint jprint = JasperFillManager.fillReport(jasper.getPath(), null, uds);
            
            HttpServletResponse resp = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        
            resp.addHeader("Content-disposition", "attachment; filename=Usuarios.pdf");
            
            try (ServletOutputStream stream = resp.getOutputStream()){
                JasperExportManager.exportReportToPdfStream(jprint, stream);
                
                stream.flush();
                stream.close();
            }
        } catch (JRException | IOException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error creando reporte"));
        }
    }
    
    public void migrar(){
        try {
            Workbook libro = WorkbookFactory.create(excel.getInputStream());
            XSSFSheet hoja = (XSSFSheet)libro.getSheetAt(0);
                        
            Iterator<Row> itrFila = hoja.rowIterator();
            itrFila.next();
            
            while(itrFila.hasNext()){
                Row fila = itrFila.next();
                Iterator<Cell> itrCelda = fila.cellIterator();
                usuarios usu = new usuarios();
                int campo = 1;
                
                while(itrCelda.hasNext()){
                    Cell celda = itrCelda.next();
                    
                    switch(campo){
                        case 1:  
                            usu.setDocumento((int) celda.getNumericCellValue());
                            break;
                        case 2: 
                            usu.setNombres(celda.getRichStringCellValue().toString());
                            break;
                        case 3: 
                            usu.setApellidos(celda.getRichStringCellValue().toString());
                            break;
                        case 4: 
                            usu.setTelefono((long) celda.getNumericCellValue());
                            break;
                        case 5: 
                            usu.setDireccion(celda.getRichStringCellValue().toString());
                            break;
                        case 6: 
                            usu.setCorreo(celda.getRichStringCellValue().toString());
                            break;
                        case 7: 
                            usu.setRol(celda.getRichStringCellValue().toString());
                            break;
                        case 8: 
                            usu.setPassword(celda.getRichStringCellValue().toString());
                            break;
                            
                    }
                    campo++;
                }
                
                usuDAO.agregar(usu);
            }
            
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Usuarios migrados exitosamente"));
        } catch (IOException | EncryptedDocumentException | InvalidFormatException e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Error migrando Usuarios"));
        }
    }
    
    public void exportarUsuariosExcel() {
    Workbook wb = new XSSFWorkbook();
    Sheet sheet = wb.createSheet("Usuarios");
    int rownum = 0;

    // cabecera
    Row header = sheet.createRow(rownum++);
    header.createCell(0).setCellValue("idUsu");
    header.createCell(1).setCellValue("Documento");
    header.createCell(2).setCellValue("Nombres");
    header.createCell(3).setCellValue("Apellidos");
    header.createCell(4).setCellValue("Telefono");
    header.createCell(5).setCellValue("Direccion");
    header.createCell(6).setCellValue("Correo");
    header.createCell(7).setCellValue("Rol");
    header.createCell(8).setCellValue("Estado");

    // obtiene lista de usuarios desde tu DAO (ajusta el método)
    java.util.List<usuarios> list = usuDAO.listar();

    for (usuarios u : list) {
        Row r = sheet.createRow(rownum++);
        r.createCell(0).setCellValue(u.getIdUsu());
        r.createCell(1).setCellValue(u.getDocumento());
        r.createCell(2).setCellValue(u.getNombres() == null ? "" : u.getNombres());
        r.createCell(3).setCellValue(u.getApellidos() == null ? "" : u.getApellidos());
        r.createCell(4).setCellValue(u.getTelefono());
        r.createCell(5).setCellValue(u.getDireccion() == null ? "" : u.getDireccion());
        r.createCell(6).setCellValue(u.getCorreo() == null ? "" : u.getCorreo());
        r.createCell(7).setCellValue(u.getRol() == null ? "" : u.getRol());
        r.createCell(8).setCellValue(u.getEstado() == null ? "" : u.getEstado());
    }

    // Auto-ajustar columnas (opcional)
    for (int i = 0; i <= 8; i++) sheet.autoSizeColumn(i);

    // Enviar al navegador
    FacesContext fc = FacesContext.getCurrentInstance();
    ExternalContext ec = fc.getExternalContext();
    try {
        ec.responseReset();
        ec.setResponseContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        ec.setResponseHeader("Content-Disposition", "attachment; filename=\"usuarios.xlsx\"");
        try (java.io.OutputStream os = ec.getResponseOutputStream()) {
            wb.write(os);
            os.flush();
        }
        fc.responseComplete();
    } catch (IOException ex) {
        ex.printStackTrace();
    } finally {
        try { wb.close(); } catch (IOException ignored) {}
    }
}
    
    public usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(usuarios usuario) {
        this.usuario = usuario;
    }

    public List<usuarios> getLstUsuFiltrados() {
        return lstUsuFiltrados;
    }

    public void setLstUsuFiltrados(List<usuarios> lstUsuFiltrados) {
        this.lstUsuFiltrados = lstUsuFiltrados;
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

                SessionUserBean bean = FacesContext.getCurrentInstance()
    .getApplication()
    .evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{sessionUser}", SessionUserBean.class);
bean.setUsuario(sesUsuario);


System.out.println("DEBUG: usuario guardado en bean: " + bean.getUsuario() + " nombres=" + bean.getNombres());


                logged = true;
                message = sesUsuario.getNombres() + " " + sesUsuario.getApellidos();
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

                FacesContext.getCurrentInstance().getExternalContext().redirect("/Pae/index.xhtml");
                

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

    public Part getExcel() {
        return excel;
    }

    public void setExcel(Part excel) {
        this.excel = excel;
    }
    
    
}
    

