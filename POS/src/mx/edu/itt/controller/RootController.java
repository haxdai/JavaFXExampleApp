package mx.edu.itt.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mx.edu.itt.Main;
import mx.edu.itt.model.Product;
import mx.edu.itt.model.User;
import mx.edu.itt.provider.service.ProductService;
import mx.edu.itt.provider.service.UserService;
import mx.edu.itt.provider.service.impl.ProductServiceImpl;
import mx.edu.itt.provider.service.impl.UserServiceImpl;
import mx.edu.itt.report.ReportBuilder;
import mx.edu.itt.util.POSUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;

/**
 * Controlador para la vista principal.
 * @author hasdai
 *
 */
public class RootController {
	private static final Logger LOG = Logger.getLogger(RootController.class.getName());
	private BorderPane rootPane;
	private Stage stage;
	private static JasperReport productReport;
	private static JasperReport userReport;
	
	/**
	 * Constructor.
	 */
	public RootController() {
		compileReports();
	}
	
	@FXML
	private void handleShowUserOverView() {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/UserOverview.fxml"));
            AnchorPane userOverview = loader.load();
            
            rootPane.setCenter(userOverview);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "No se pudo cargar la vista de usuarios", e);
        }
	}
	
	@FXML
	private void handleShowProductOverView() {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/ProductOverview.fxml"));
            AnchorPane userOverview = loader.load();
            
            rootPane.setCenter(userOverview);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "No se pudo cargar la vista de productos", e);
        }
	}
	
	@FXML
	private void handleClose() {
		if (null != stage) {
			stage.close();
		}
	}
	
	@FXML
	private void handleProductReport() {
        File file = showSaveReportDialog();
        if (file != null) {
			ProductService service = new ProductServiceImpl();
        	List<Product> products = service.findAll();
        	
			try {
				ReportBuilder.generatePDFReport(productReport, products, "PRODUCTLIST", file.getAbsolutePath());
				POSUtil.showAlert("Reporte de productos", "Se ha guardado el reporte en la ruta " + file.getAbsolutePath());
			} catch (FileNotFoundException e) {
				LOG.log(Level.SEVERE, "No se ha seleccionado un archivo de destno", e);
			} catch (JRException e) {
				LOG.log(Level.SEVERE, "No se pudo generar el reporte de productos", e);
			}
		}
	}
	
	@FXML
	private void handleUserReport() {
        File file = showSaveReportDialog();
        if (file != null) {
			UserService service = new UserServiceImpl();
        	List<User> users = service.findAll();
        	
			try {
				ReportBuilder.generatePDFReport(userReport, users, "USERLIST", file.getAbsolutePath());
				POSUtil.showAlert("Reporte de usuarios", "Se ha guardado el reporte en la ruta " + file.getAbsolutePath());
			} catch (FileNotFoundException e) {
				LOG.log(Level.SEVERE, "No se ha seleccionado un archivo de destno", e);
			} catch (JRException e) {
				LOG.log(Level.SEVERE, "No se pudo generar el reporte de usuarios", e);
			}
		}
	}
	
	/**
	 * Muestra el diálogo de seleccón de archivo para guardar reporte.
	 * @return Archivo seleccionado.
	 */
	private File showSaveReportDialog() {
		FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar reporte");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("PDF", "*.pdf")
        );
        
        return fileChooser.showSaveDialog(stage);
	}
	
	public void setRootPane(Pane pane) {
		this.rootPane = (BorderPane) pane;
	}
	
	public void setMainStage(Stage stage) {
		this.stage = stage;
	}
	
	/**
	 * Compila los archivos fuentes para los reportes de usuarios y productos.
	 */
	private void compileReports() {
		if (null == productReport) {
			URL urlprods = RootController.class.getResource("../view/ProductReport.jrxml");
			if (null != urlprods) {
				productReport = ReportBuilder.compileReport(new File(urlprods.getPath()));
			}
		}
		
		if (null == userReport) {
			URL urlusers = RootController.class.getResource("../view/UserReport.jrxml");
			if (null != urlusers) {
				userReport = ReportBuilder.compileReport(new File(urlusers.getPath()));
			}
		}
	}
}
