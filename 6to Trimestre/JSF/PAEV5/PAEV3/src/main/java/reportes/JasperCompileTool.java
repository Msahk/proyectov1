package reportes;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.File;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class JasperCompileTool {
    public static void main(String[] args) {
        try {
            String jrxmlPath = "src/main/webapp/reporte.jrxml";
            String jasperPath = "src/main/webapp/reporte.jasper";

            File jrxmlFile = new File(jrxmlPath);
            System.out.println("Ruta absoluta: " + jrxmlFile.getAbsolutePath());

            if (!jrxmlFile.exists()) {
                System.err.println("❌ El archivo .jrxml no existe.");
                return;
            }

            // Cargar como InputStream
            InputStream input = new FileInputStream(jrxmlFile);
            JasperDesign design = JRXmlLoader.load(input);
            JasperReport report = JasperCompileManager.compileReport(design);

            // Guardar como .jasper
            JasperCompileManager.compileReportToFile(design, jasperPath);
            System.out.println("✅ Compilado correctamente: " + jasperPath);

        } catch (Exception e) {
            System.err.println("❌ Error al compilar el reporte:");
            e.printStackTrace();
        }
    }
}
