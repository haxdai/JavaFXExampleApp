package mx.edu.itt.controller;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import mx.edu.itt.Main;
import mx.edu.itt.model.User;
import mx.edu.itt.model.UserModel;
import mx.edu.itt.provider.service.UserService;
import mx.edu.itt.provider.service.impl.UserServiceImpl;
import mx.edu.itt.util.POSUtil;

/**
 * Constrolador para la vista de usuarios.
 * @author hasdai
 *
 */
public class UserOverviewController {
	Logger LOG = Logger.getLogger(UserOverviewController.class.getName());
	private static UserService service = new UserServiceImpl();
	private Stage stage;
	
	@FXML
    private TableView<UserModel> userTable;
    @FXML
    private TableColumn<UserModel, String> firstNameColumn;
    @FXML
    private TableColumn<UserModel, String> lastNameColumn;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;
    @FXML
    private Label emailLabel;
    
    
    public UserOverviewController() { }
    
    @FXML
    private void initialize() {
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());
        
        loadUsers();
        clearLabels();
        
        userTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showUserDetails(newValue));
    }
    
    @FXML
    private void handleDeleteUser() {
    	if (userTable.getSelectionModel().isEmpty()) {
    		POSUtil.showAlert("No existen usuarios para eliminar");
    	} else {
	    	UserModel selected = userTable.getSelectionModel().getSelectedItem();
	    	if (null != selected) {
		    	Alert alert = POSUtil.showConfirmAlert("Desea eliminar el usuario?");
		    	if (alert.getResult() == ButtonType.YES) {
			    	service.remove(selected.toUser());
			        userTable.getItems().remove(selected);
		    	}
	    	} else {
	    		POSUtil.showAlert("No se ha seleccionado un usuario");
	    	}
    	}
    }
    
    @FXML
    private void handleEditUser() {
    	if (userTable.getSelectionModel().isEmpty()) {
    		POSUtil.showAlert("No existen usuarios para editar");
    	} else {
	    	UserModel selected = userTable.getSelectionModel().getSelectedItem();
	    	if (null != selected) {
		    	showUserEdit(selected);
	    	} else {
	    		POSUtil.showAlert("No se ha seleccionado un usuario");
	    	}
    	}
    }
    
    @FXML
    private void handleNewUser() {
    	showUserEdit(null);
    }
    
    /**
     * Muestra la ventana de edición de usuarios.
     * @param user {@link UserModel}
     */
    private void showUserEdit(UserModel user) {
    	try {
    		//Carga la vista de edición de usuarios
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/UserEditView.fxml"));
            AnchorPane page = loader.load();

            //Se establece inicio modal
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);
            
            //Se despliega en una escena independiente
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            //Se carga el controlador
            UserEditController controller = loader.getController();
            controller.setStage(dialogStage);
            controller.setUser(user);
            
            if (null != user) {
            	dialogStage.setTitle("Editar Usuario");
            } else {
            	dialogStage.setTitle("Nuevo Usuario");
            }

            dialogStage.showAndWait();

            if (null != controller.getUser()) {
            	service.save(controller.getUser().toUser());
            	loadUsers();
            }
        } catch (IOException e) {
        	LOG.log(Level.SEVERE, "No se pudo cargar la vista de usuarios", e);
        }
    }
    
    private void showUserDetails(UserModel user) {
    	if (null == user) {
    		clearLabels();
    	} else {
    		fillLabels(user);
    	}
    }
    
    /**
     * Llena las etiquetas del formulario con la información del usuario.
     * @param user {@link UserModel}
     */
    private void fillLabels(UserModel user) {
    	firstNameLabel.setText(user.getFirstName());
    	lastNameLabel.setText(user.getLastName());
    	emailLabel.setText(user.getEmail());
    	cityLabel.setText(user.getCity());
    	birthdayLabel.setText(user.getBirthDay());
    }
    
    /**
     * Limpia las etiquetas.
     */
    private void clearLabels() {
    	firstNameLabel.setText("");
    	lastNameLabel.setText("");
    	emailLabel.setText("");
    	cityLabel.setText("");
    	birthdayLabel.setText("");
    }
    
    public void setStage(Stage stage) {
    	this.stage = stage;
    }
    
    /**
     * Carga los usuarios del servicio de gestión de usuarios.
     */
    private void loadUsers() {
    	ObservableList<UserModel> users = FXCollections.observableArrayList();

    	//Carga los usuarios de la base de datos
    	List<User> res = service.findAll();
    	for (User u : res) {
    		users.add(new UserModel(u));
    	}
    	userTable.setItems(users);
    }
}
