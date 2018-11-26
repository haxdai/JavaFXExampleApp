package mx.edu.itt.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import mx.edu.itt.Main;

/**
 * Controlador para la vista principal.
 * @author hasdai
 *
 */
public class RootController {
	private static final Logger LOG = Logger.getLogger(RootController.class.getName());
	private BorderPane rootPane;
	private Stage stage;
	
	/**
	 * Constructor.
	 */
	public RootController() {}
	
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
	
	public void setRootPane(Pane pane) {
		this.rootPane = (BorderPane) pane;
	}
	
	public void setMainStage(Stage stage) {
		this.stage = stage;
	}
	
}
