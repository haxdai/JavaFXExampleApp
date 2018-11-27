package mx.edu.itt.controller;

import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.edu.itt.model.UserModel;
import mx.edu.itt.util.POSUtil;

public class UserEditController {
	private UserModel user;
	private Stage stage;
	
	@FXML	
	private TextField firstNameField;
	@FXML	
	private TextField lastNameField;
	@FXML	
	private TextField emailField;
	@FXML	
	private TextField addressField;
	@FXML	
	private TextField cityField;
	@FXML	
	private TextField postalCodeField;
	@FXML	
	private DatePicker birthdayField;
	
	public UserEditController() {}
	
	@FXML
	private void initialize() { }
	
	@FXML
	private void handleCancel() {
		if (null != stage) {
			this.user = null;
			stage.close();
		}
	}
	
	@FXML
	private void handleOk() {
		if (null == user) {
			user = new UserModel();
		}
		
		user.setFirstName(firstNameField.getText());
		user.setLastName(lastNameField.getText());
		user.setEmail(emailField.getText());
		user.setStreet(addressField.getText());
		user.setCity(cityField.getText());
		try {
			int pc = Integer.parseInt(postalCodeField.getText());
			user.setPostalCode(pc);
		} catch (NumberFormatException nfe) {
			user.setPostalCode(0);
		}
		user.setBirthDay(birthdayField.getValue());
		
		if (user.toUser().isValid() && null != stage) {
			stage.close();
		} else {
			POSUtil.showAlert("Algunos de los datos no son válidos.");
		}
	}
	
	public void setUser(UserModel user) {
		this.user = user;
		fillForm(user);
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	/**
	 * Llena el formulario con la información de un usuario.
	 * @param user UserModel.
	 */
	private void fillForm(UserModel user) {
		if (null == user) {
			firstNameField.setText("");
			lastNameField.setText("");
			emailField.setText("");
			addressField.setText("");
			cityField.setText("");
			postalCodeField.setText("");
			birthdayField.setValue(LocalDate.now());
		} else {
			firstNameField.setText(user.getFirstName());
			lastNameField.setText(user.getLastName());
			emailField.setText(user.getEmail());
			addressField.setText(user.getStreet());
			cityField.setText(user.getCity());
			postalCodeField.setText(String.valueOf(user.getPostalCode()));
			birthdayField.setValue(POSUtil.parse(user.getBirthDay()));
		}
	}
	
	/**
	 * Obtiene el usuario relacionado con el formulario.
	 * @return {@link UserModel}
	 */
	public UserModel getUser() {		
		return user;
	}
}
