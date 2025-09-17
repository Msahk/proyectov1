// package beans;

// import models.insumos;
// import models.insumosDao;

// import javax.annotation.PostConstruct;
// import javax.faces.bean.ManagedBean;
// import javax.faces.bean.ViewScoped;
// import javax.faces.context.FacesContext;
// import java.io.Serializable;
// import java.util.List;
// import java.util.Map;
// import org.primefaces.event.FileUploadEvent;
// import org.primefaces.model.file.UploadedFile;

// import java.io.IOException;
// import java.io.InputStream;

// @ManagedBean
// @ViewScoped
// public class insumosBean implements Serializable {

//     private insumosDao dao = new insumosDao();
//     private List<insumos> listaInsumos;
//     private insumos insumo;   // para crear/editar
//     private String busqueda;  // campo del buscador
//     private UploadedFile archivoExcel;

//     public insumosBean() {
//         insumo = new insumos(); // inicializamos para que no sea null
//     }

//     @PostConstruct
//     public void init() {
//         // Cargar lista
//         listaInsumos = dao.listar();

//         // Revisar si viene eliminarId en la URL
//         Map<String, String> params = FacesContext.getCurrentInstance()
//                 .getExternalContext()
//                 .getRequestParameterMap();

//         String eliminarId = params.get("eliminarId");
//         if (eliminarId != null) {
//             try {
//                 int id = Integer.parseInt(eliminarId);
//                 dao.eliminar(id);
//                 listaInsumos = dao.listar(); // refrescar lista
//             } catch (NumberFormatException e) {
//                 e.printStackTrace();
//             }
//         }
//     }

//     // ================== GETTERS / SETTERS ==================
//     public List<insumos> getListaInsumos() {
//         return listaInsumos;
//     }

//     public insumos getInsumo() {
//         return insumo;
//     }

//     public void setInsumo(insumos insumo) {
//         this.insumo = insumo;
//     }

//     public String getBusqueda() {
//         return busqueda;
//     }

//     public void setBusqueda(String busqueda) {
//         this.busqueda = busqueda;
//     }

//     // ================== MÉTODOS ==================
//     // Crear
//     public void crear() {
//         dao.agregar(insumo);
//         listaInsumos = dao.listar();
//         insumo = new insumos();
//     }

//     // Actualizar
//     public void actualizar() {
//         dao.actualizar(insumo);
//         listaInsumos = dao.listar();
//         insumo = new insumos();
//     }

//     // Eliminar directo desde acción JSF (sin SweetAlert)
//     public void eliminar(int id) {
//         dao.eliminar(id);
//         listaInsumos = dao.listar();
//     }

//     // Buscar por ID
//     public void cargarPorId(int id) {
//         insumo = dao.buscarPorId(id);
//     }

//     // Contar stock bajo
//     public int contarStockBajo() {
//         int contador = 0;
//         for (insumos i : listaInsumos) {
//             if (i.getCantidad() < i.getStock_min()) {
//                 contador++;
//             }
//         }
//         return contador;
//     }

//     // Filtro búsqueda
//     public List<insumos> getInsumosFiltrados() {
//         if (busqueda == null || busqueda.trim().isEmpty()) {
//             return listaInsumos;
//         }
//         return listaInsumos.stream()
//                 .filter(i -> i.getNombre().toLowerCase().contains(busqueda.toLowerCase()))
//                 .toList();
//     }

//     // ================== EXPORTAR A PDF ==================
//     public void exportarPDF() {
//         try {
//             FacesContext context = FacesContext.getCurrentInstance();
//             context.getExternalContext().setResponseContentType("application/pdf");
//             context.getExternalContext().setResponseHeader("Content-Disposition", "attachment; filename=insumos.pdf");

//             com.lowagie.text.Document document = new com.lowagie.text.Document();
//             com.lowagie.text.pdf.PdfWriter.getInstance(document, context.getExternalContext().getResponseOutputStream());

//             document.open();
//             document.add(new com.lowagie.text.Paragraph("Reporte de Insumos"));
//             document.add(new com.lowagie.text.Paragraph(" ")); // espacio

//             // Crear tabla con 5 columnas
//             com.lowagie.text.pdf.PdfPTable table = new com.lowagie.text.pdf.PdfPTable(5);
//             table.addCell("ID");
//             table.addCell("Nombre");
//             table.addCell("Cantidad");
//             table.addCell("Unidad de Medida");
//             table.addCell("Stock Minimo");

//             // Llenar con datos
//             for (insumos i : listaInsumos) {
//                 table.addCell(String.valueOf(i.getId_ins()));
//                 table.addCell(i.getNombre());
//                 table.addCell(String.valueOf(i.getCantidad()));
//                 table.addCell(i.getUnidad_medida());
//                 table.addCell(String.valueOf(i.getStock_min()));
//             }

//             document.add(table);
//             document.close();
//             context.responseComplete();
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }

// // ================== EXPORTAR A EXCEL ==================
//     public void exportarExcel() {
//         try {
//             FacesContext context = FacesContext.getCurrentInstance();
//             context.getExternalContext().setResponseContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//             context.getExternalContext().setResponseHeader("Content-Disposition", "attachment; filename=insumos.xlsx");

//             org.apache.poi.ss.usermodel.Workbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook();
//             org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("Insumos");

//             // Encabezados
//             String[] columnas = {"ID", "Nombre", "Cantidad", "Unidad de Medida", "Stock Minimo"};
//             org.apache.poi.ss.usermodel.Row headerRow = sheet.createRow(0);
//             for (int i = 0; i < columnas.length; i++) {
//                 headerRow.createCell(i).setCellValue(columnas[i]);
//             }

//             // Llenar filas
//             int rowNum = 1;
//             for (insumos i : listaInsumos) {
//                 org.apache.poi.ss.usermodel.Row row = sheet.createRow(rowNum++);
//                 row.createCell(0).setCellValue(i.getId_ins());
//                 row.createCell(1).setCellValue(i.getNombre());
//                 row.createCell(2).setCellValue(i.getCantidad());
//                 row.createCell(3).setCellValue(i.getUnidad_medida());
//                 row.createCell(4).setCellValue(i.getStock_min());
//             }

//             // Ajustar tamaño automático
//             for (int i = 0; i < columnas.length; i++) {
//                 sheet.autoSizeColumn(i);
//             }

//             workbook.write(context.getExternalContext().getResponseOutputStream());
//             workbook.close();
//             context.responseComplete();
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }

//     public void importarExcel(FileUploadEvent event) {
//         UploadedFile archivoExcel = event.getFile();
//         try (InputStream input = archivoExcel.getInputStream()) {
//             org.apache.poi.ss.usermodel.Workbook workbook = org.apache.poi.ss.usermodel.WorkbookFactory.create(input);
//             org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);

//             for (org.apache.poi.ss.usermodel.Row row : sheet) {
//                 if (row.getRowNum() == 0) {
//                     continue; // saltar cabecera
//                 }
//                 insumos nuevo = new insumos();
//                 nuevo.setNombre(row.getCell(0).getStringCellValue());   // Columna A -> Nombre
//                 nuevo.setCantidad((int) row.getCell(1).getNumericCellValue()); // Columna B -> Cantidad
//                 nuevo.setUnidad_medida(row.getCell(2).getStringCellValue());   // Columna C -> Unidad
//                 nuevo.setStock_min((int) row.getCell(3).getNumericCellValue()); // Columna D -> Stock Minimo

//                 // Guardar en BD
//                 dao.agregar(nuevo);
//             }

//             // Refrescar lista para que se vea en pantalla
//             listaInsumos = dao.listar();
//             workbook.close();

//             FacesContext.getCurrentInstance().addMessage(null,
//                     new javax.faces.application.FacesMessage("Archivo importado correctamente"));

//         } catch (Exception e) {
//             e.printStackTrace();
//             FacesContext.getCurrentInstance().addMessage(null,
//                     new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR,
//                             "Error", "No se pudo procesar el archivo"));
//         }
//     }

// }
