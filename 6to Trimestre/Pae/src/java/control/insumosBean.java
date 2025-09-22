package control;

import dao.insumosDao;
import modelo.insumos;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import java.io.InputStream;

@ManagedBean
@ViewScoped
public class insumosBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private final insumosDao dao = new insumosDao();
    private insumos insumo = new insumos();
    private List<insumos> listaInsumos = new ArrayList<>();
    
    // Filtros
    private String filtroNombre;
    private String filtroUnidad;
    private Double filtroCantidadMin;
    private Double filtroCantidadMax;

    // Archivo Excel
    private UploadedFile archivoExcel;

    @PostConstruct
    public void init() {
        listar();

        // Revisar si viene eliminarId en la URL
        Map<String, String> params = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap();

        String eliminarId = params.get("eliminarId");
        if (eliminarId != null) {
            try {
                int id = Integer.parseInt(eliminarId);
                insumos i = new insumos();
                i.setId_ins(id);
                eliminar(i);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    // ================== CRUD ==================
    public void listar() {
        listaInsumos = dao.listar();
    }

    public void crear() {
        if (dao.agregar(insumo)) {
            listar();
            insumo = new insumos();
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Insumo agregado correctamente"));
        }
    }

    public void actualizar() {
        if (dao.actualizar(insumo)) {
            listar();
            insumo = new insumos();
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Insumo actualizado correctamente"));
        }
    }

    public void eliminar(insumos i) {
        dao.eliminar(i);
        listar();
    }

    public void cargarPorId(int id) {
        insumo = dao.obtenerPorId(id);
    }

    public int contarStockBajo() {
        return (int) listaInsumos.stream()
                .filter(i -> i.getCantidad() < i.getStock_min())
                .count();
    }

    // ================== FILTROS ==================
    public List<insumos> getInsumosFiltrados() {
        return listaInsumos.stream()
                .filter(i -> (filtroNombre == null || filtroNombre.trim().isEmpty()
                        || i.getNombre().toLowerCase().contains(filtroNombre.toLowerCase())))
                .filter(i -> (filtroUnidad == null || filtroUnidad.trim().isEmpty()
                        || i.getUnidad_medida().equalsIgnoreCase(filtroUnidad)))
                .filter(i -> (filtroCantidadMin == null || i.getCantidad() >= filtroCantidadMin))
                .filter(i -> (filtroCantidadMax == null || i.getCantidad() <= filtroCantidadMax))
                .collect(Collectors.toList());
    }

    // ================== IMPORTAR / EXPORTAR ==================
    public void importarExcel(FileUploadEvent event) {
        UploadedFile archivoExcel = event.getFile();
        try (InputStream input = archivoExcel.getInputStream()) {
            org.apache.poi.ss.usermodel.Workbook workbook = org.apache.poi.ss.usermodel.WorkbookFactory.create(input);
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);

            for (org.apache.poi.ss.usermodel.Row row : sheet) {
                if (row.getRowNum() == 0) continue; // saltar cabecera
                
                insumos nuevo = new insumos();
                nuevo.setNombre(row.getCell(0).getStringCellValue());
                nuevo.setCantidad(row.getCell(1).getNumericCellValue());
                nuevo.setUnidad_medida(row.getCell(2).getStringCellValue());
                nuevo.setStock_min(row.getCell(3).getNumericCellValue());

                dao.agregar(nuevo);
            }

            listar();
            workbook.close();

            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Archivo importado correctamente"));

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo procesar el archivo"));
        }
    }

    // ================== GETTERS / SETTERS ==================
    public List<insumos> getListaInsumos() { return listaInsumos; }
    public insumos getInsumo() { return insumo; }
    public void setInsumo(insumos insumo) { this.insumo = insumo; }
    public String getFiltroNombre() { return filtroNombre; }
    public void setFiltroNombre(String filtroNombre) { this.filtroNombre = filtroNombre; }
    public String getFiltroUnidad() { return filtroUnidad; }
    public void setFiltroUnidad(String filtroUnidad) { this.filtroUnidad = filtroUnidad; }
    public Double getFiltroCantidadMin() { return filtroCantidadMin; }
    public void setFiltroCantidadMin(Double filtroCantidadMin) { this.filtroCantidadMin = filtroCantidadMin; }
    public Double getFiltroCantidadMax() { return filtroCantidadMax; }
    public void setFiltroCantidadMax(Double filtroCantidadMax) { this.filtroCantidadMax = filtroCantidadMax; }
    public UploadedFile getArchivoExcel() { return archivoExcel; }
    public void setArchivoExcel(UploadedFile archivoExcel) { this.archivoExcel = archivoExcel; }
}
