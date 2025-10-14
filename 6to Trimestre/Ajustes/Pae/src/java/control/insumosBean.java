package control;

import dao.insumosDao;
import java.io.File;
import java.io.Serializable;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import modelo.insumos;
import net.sf.jasperreports.engine.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@ManagedBean
@SessionScoped
public class insumosBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private final insumosDao dao = new insumosDao();
    private insumos insumo = new insumos();
    private List<insumos> lstInsumos = new ArrayList<>();
    private List<insumos> lstFiltrados;
    private Part excel;

    @PostConstruct
    public void init() {
        listar();
    }

    // 📋 Listar todos los insumos
    public void listar() {
        try {
            lstInsumos = dao.listar();
            if (lstInsumos == null) {
                lstInsumos = new ArrayList<>();
            }
            verificarAlertasYEstado();
        } catch (Exception e) {
            e.printStackTrace();
            lstInsumos = new ArrayList<>();
        }
    }

    // ⚠️ Verificar stock, vencimiento y actualizar estado
    private void verificarAlertasYEstado() {
        if (lstInsumos == null || lstInsumos.isEmpty()) return;

        Date hoy = new Date();

        for (insumos i : lstInsumos) {
            boolean cambio = false;

            // Verificar vencimiento
            if (i.getFecha_vencimiento() != null && i.getFecha_vencimiento().before(hoy)) {
                if (!"Inactivo".equalsIgnoreCase(i.getEstado())) {
                    i.setEstado("Inactivo");
                    dao.actualizarEstado(i.getId_ins(), "Inactivo");
                    cambio = true;
                }
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Vencido",
                                "El insumo '" + i.getNombre() + "' está vencido."));
            } else if (!"Activo".equalsIgnoreCase(i.getEstado())) {
                i.setEstado("Activo");
                dao.actualizarEstado(i.getId_ins(), "Activo");
                cambio = true;
            }

            // Verificar stock mínimo
            if (i.getStock_actual() < i.getStock_min()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Stock bajo",
                                "El insumo '" + i.getNombre() + "' está por debajo del stock mínimo."));
            }

            if (cambio) {
                // No se vuelve a llamar listar() dentro del for
                System.out.println("Estado actualizado: " + i.getNombre());
            }
        }
    }

    // ➕ Agregar nuevo insumo (con actualización de cantidad si ya existe)
    public void agregar() {
        insumos existente = dao.obtenerPorNombre(insumo.getNombre());

        if (existente != null) {
            // Si ya existe, actualiza el stock actual sumando
            existente.setStock_actual(existente.getStock_actual() + insumo.getStock_actual());
            existente.setFecha_vencimiento(insumo.getFecha_vencimiento()); // actualiza vencimiento si aplica
            dao.actualizar(existente);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizado", "El insumo ya existía, se actualizó su cantidad."));
        } else {
            insumo.setEstado("Activo");
            if (dao.agregar(insumo)) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Insumo agregado correctamente"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo agregar el insumo"));
            }
        }

        listar();
        limpiar();
    }

    // ✏️ Editar
    public void editar(insumos obj) {
        if (obj != null) {
            this.insumo = dao.obtenerPorId(obj.getId_ins());
        }
    }

    // 🔁 Actualizar
    public void actualizar() {
        if (dao.actualizar(insumo)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Insumo actualizado correctamente"));
            listar();
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo actualizar"));
        }
    }

    // ❌ Eliminar
    public void eliminar(insumos i) {
        if (i != null) {
            dao.eliminar(i);
            listar();
        }
    }

    // 🧹 Limpiar
    public void limpiar() {
        insumo = new insumos();
    }

    // 📥 Migrar desde Excel
    public void migrar() {
        try (Workbook libro = WorkbookFactory.create(excel.getInputStream())) {
            XSSFSheet hoja = (XSSFSheet) libro.getSheetAt(0);
            Iterator<Row> filas = hoja.iterator();
            filas.next(); // Saltar encabezado

            while (filas.hasNext()) {
                Row fila = filas.next();
                insumos i = new insumos();

                i.setNombre(fila.getCell(0).getStringCellValue());
                i.setCantidad(fila.getCell(1).getNumericCellValue());
                i.setUnidad_medida(fila.getCell(2).getStringCellValue());
                i.setStock_min(fila.getCell(3).getNumericCellValue());
                i.setStock_actual(fila.getCell(4).getNumericCellValue());
                i.setFecha_vencimiento(fila.getCell(5).getDateCellValue());
                i.setEstado("Activo");

                dao.agregar(i);
            }

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Insumos migrados correctamente"));
            listar();

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Error migrando insumos"));
        }
    }

    // 📊 Exportar PDF
    public void exportarPDF() {
        try {
            String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/insumos.jasper");
            File jasper = new File(path);
            JasperPrint jprint = JasperFillManager.fillReport(jasper.getPath(), null, control.ConDB.conectar());

            HttpServletResponse resp = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            resp.addHeader("Content-disposition", "attachment; filename=Insumos.pdf");

            try (ServletOutputStream stream = resp.getOutputStream()) {
                JasperExportManager.exportReportToPdfStream(jprint, stream);
                stream.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error creando reporte"));
        }
    }

    // 🧩 Getters y Setters
    public insumos getInsumo() { return insumo; }
    public void setInsumo(insumos insumo) { this.insumo = insumo; }
    public List<insumos> getLstInsumos() { return lstInsumos; }
    public void setLstInsumos(List<insumos> lstInsumos) { this.lstInsumos = lstInsumos; }
    public List<insumos> getLstFiltrados() { return lstFiltrados; }
    public void setLstFiltrados(List<insumos> lstFiltrados) { this.lstFiltrados = lstFiltrados; }
    public Part getExcel() { return excel; }
    public void setExcel(Part excel) { this.excel = excel; }
}
