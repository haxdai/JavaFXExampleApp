package mx.edu.itt.report;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class ReportBuilder {
	private static final Logger LOG = Logger.getLogger(ReportBuilder.class.getName());
	
	/**
	 * Compila un archivo fuente de iReport Designer desde los paquetes del compilado.
	 * @param template Ruta de la plantilla en los paquetes.
	 * @return {@link JasperReport}
	 * 
	 */
	public static JasperReport compileReport(String template) {
		URL url = ReportBuilder.class.getResource("template/"+template+".jrxml");
		if (null != url) {
			File tpf = new File(url.getPath()); 
			return compileReport(tpf);
		}
		return null;
	}
	
	/**
	 * Compila un archivo fuente de iReport Designer 
	 * @param template Ruta del archivo fuente.
	 * @return {@link JasperReport}
	 */
	public static JasperReport compileReport(File template) {
		if (null != template) { 
			try {
				JasperDesign ds = JRXmlLoader.load(template);
				return JasperCompileManager.compileReport(ds);
			} catch (JRException e) {
				LOG.log(Level.SEVERE, "No se ha podido compilar la plantilla del reporte", e);
			}
		}
		return null;
	}
	
	/**
	 * Genera un reporte PDF utilizando un reporte compilado y datos en una lista.
	 * @param report Reporte compilado {@link JasperReport}
	 * @param data Lista con objetos de datos.
	 * @param paramName Nombre del parámetro en el reporte que recibe la lista de objetos.
	 * @param outputPath Ruta para guardar el reporte.
	 * @throws FileNotFoundException
	 * @throws JRException
	 */
	public static void generatePDFReport(JasperReport report, List data, String paramName, String outputPath) throws FileNotFoundException, JRException {
		JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(data);
    	Map<String, Object> parameters = new HashMap<>();
        parameters.put(paramName, itemsJRBean);
        
		JasperPrint print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
		OutputStream outputStream = new FileOutputStream(new File(outputPath));
		JasperExportManager.exportReportToPdfStream(print, outputStream);
	}
}
