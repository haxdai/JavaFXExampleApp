package mx.edu.itt;
	
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import mx.edu.itt.controller.RootController;
import mx.edu.itt.provider.service.UserService;
import mx.edu.itt.provider.service.impl.UserServiceImpl;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

/**
 * Aplicación principal.
 * @author hasdai
 *
 */
public class Main extends Application {
	Logger LOG = Logger.getLogger(Main.class.getName());
	
	@Override
	public void start(Stage primaryStage) {
		try {
			//Carga la vista principal
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("view/RootLayout.fxml"));
			BorderPane root = loader.load();
			Scene scene = new Scene(root, 800, 500);
			
			//Carga el controlador de la vista principal
			RootController controller = loader.getController();
	        controller.setRootPane(root);
	        controller.setMainStage(primaryStage);
			
	        //Muestra la aplicación
			primaryStage.setScene(scene);
			primaryStage.setTitle("Punto de Venta");
			primaryStage.show();
		} catch(Exception e) {
			LOG.log(Level.SEVERE, "Error al iniciar la aplicación", e);
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
