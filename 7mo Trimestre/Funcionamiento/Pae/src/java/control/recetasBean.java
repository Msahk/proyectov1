package control;

import dao.recetasDao;
import dao.receta_insumosDao;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import modelo.recetas;
import net.sf.jasperreports.engine.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.PrimeFaces;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Cell;

@ManagedBean
@SessionScoped
public class recetasBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private final recetasDao dao = new recetasDao();
    private final receta_insumosDao riDao = new receta_insumosDao();

    private recetas receta = new recetas();
    private List<recetas> lstRecetas = new ArrayList<>();
    private List<recetas> lstFiltradas;

    private Part excel; // Archivo para migración Excel

    @PostConstruct
    public void init() {
        listar();
    }

    // 📋 Listar recetas
    public void listar() {
        lstRecetas = dao.listar();
    }

    // ➕ / 🔁 Guardar receta (decide si agregar o actualizar)
    public void guardarReceta() {
        if (receta.getId_rec() == null) { // Nueva receta
            agregar();
        } else { // Receta existente
            actualizar();
        }
    }

    // ➕ Agregar nueva receta con validación de duplicado
    public void agregar() {
        if (dao.existeNombre(receta.getNombre())) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Duplicado", "El nombre de la receta ya existe"));
            return;
        }

        // Validación adicional para precio
        if (receta.getPrecio() <= 0) { // ✅ Nuevo campo
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Precio inválido", "Debe ingresar un precio mayor que cero"));
            return;
        }

        if (dao.agregar(receta)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Receta agregada correctamente"));
            listar();
            limpiar();
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo agregar la receta"));
        }
    }

    // 🔁 Actualizar receta
    public void actualizar() {
        if (receta.getPrecio() <= 0) { // ✅ Validación precio
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Precio inválido", "Debe ingresar un precio mayor que cero"));
            return;
        }

        if (dao.actualizar(receta)) {
            dao.actualizarEstado(receta.getId_rec());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Receta actualizada correctamente"));
            listar();
            limpiar();
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo actualizar la receta"));
        }
    }

    // ✏️ Preparar para editar
    public String irAEditar(recetas r) {
        this.receta = dao.obtenerPorId(r.getId_rec());
        return "/views/Recetas/editarreceta.xhtml?faces-redirect=true";
    }

    // ✅ Guardar cambios y volver a listado
    public String guardarYRedirigir() {
        guardarReceta();
        return "/views/Recetas/recetas.xhtml?faces-redirect=true";
    }

    // ❌ Cancelar edición
    public String cancelarEdicion() {
        limpiar();
        return "/views/Recetas/recetas.xhtml?faces-redirect=true";
    }

    // ❌ Eliminar receta
    public void eliminar(recetas r) {
        dao.eliminar(r);
        listar();
        PrimeFaces.current().ajax().update("formTablaRecetas:tablaRecetas");
    }

    // 🧹 Limpiar formulario
    public void limpiar() {
        receta = new recetas();
    }

    // ✨ Ir a la página de nueva receta
    public String irANuevaReceta() {
        limpiar();
        return "/views/Recetas/nuevareceta.xhtml?faces-redirect=true";
    }

    // ✨ Ir a la página de gestionar insumos
    public String irAGestionarInsumos(recetas r) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("recetaSeleccionada", r);
        return "/views/Recetas/gestionarinsumos.xhtml?faces-redirect=true";
    }

    // 📊 Exportar PDF (JasperReports)
    public void exportarPDF() {
        try {
            String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/recetas.jasper");
            File jasper = new File(path);
            Connection con = control.ConDB.conectar();

            JasperPrint jprint = JasperFillManager.fillReport(jasper.getPath(), null, con);

            HttpServletResponse resp = (HttpServletResponse) FacesContext.getCurrentInstance()
                    .getExternalContext().getResponse();
            resp.addHeader("Content-disposition", "attachment; filename=Recetas.pdf");

            try (ServletOutputStream stream = resp.getOutputStream()) {
                JasperExportManager.exportReportToPdfStream(jprint, stream);
                stream.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error generando reporte PDF"));
        }
    }

    // 📥 Migrar desde Excel
    public void migrar() {
        try (InputStream input = excel.getInputStream(); Workbook libro = new XSSFWorkbook(input)) {

            XSSFSheet hoja = (XSSFSheet) libro.getSheetAt(0);
            Iterator<Row> filas = hoja.iterator();
            filas.next(); // Saltar encabezado

            while (filas.hasNext()) {
                Row fila = filas.next();
                recetas r = new recetas();
                r.setNombre(fila.getCell(0).getStringCellValue());
                r.setDescripcion(fila.getCell(1).getStringCellValue());

                // ✅ Leer el precio si existe      
                if (fila.getCell(2) != null && fila.getCell(2).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    r.setPrecio(fila.getCell(2).getNumericCellValue());
                } else {
                    r.setPrecio(0.0);
                }

                dao.agregar(r);
            }

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Recetas migradas correctamente"));
            listar();

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Error migrando recetas"));
        }
    }

    // 🔍 Buscar receta por nombre
    public void buscarPorNombre(String nombre) {
        lstRecetas = dao.buscarPorNombre(nombre);
        if (lstRecetas.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Sin resultados", "No se encontraron recetas con ese nombre"));
        }
    }

    // ✅ Getters y Setters
    public recetas getReceta() {
        return receta;
    }

    public void setReceta(recetas receta) {
        this.receta = receta;
    }

    public List<recetas> getLstRecetas() {
        return lstRecetas;
    }

    public void setLstRecetas(List<recetas> lstRecetas) {
        this.lstRecetas = lstRecetas;
    }

    public List<recetas> getLstFiltradas() {
        return lstFiltradas;
    }

    public void setLstFiltradas(List<recetas> lstFiltradas) {
        this.lstFiltradas = lstFiltradas;
    }

    public Part getExcel() {
        return excel;
    }

    public void setExcel(Part excel) {
        this.excel = excel;
    }
}
