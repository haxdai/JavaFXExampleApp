package mx.edu.itt.controller;

import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.edu.itt.model.ProductModel;
import mx.edu.itt.util.POSUtil;

public class ProductEditController {
	private ProductModel prod;
	private Stage stage;
	
	@FXML	
	private TextField nameField;
	@FXML	
	private TextField priceField;
	@FXML	
	private TextField typeField;
	@FXML	
	private TextField brandField;
	@FXML	
	private DatePicker expirationField;
	
	public ProductEditController() {}
	
	@FXML
	private void initialize() { }
	
	@FXML
	private void handleCancel() {
		if (null != stage) {
			this.prod = null;
			stage.close();
		}
	}
	
	@FXML
	private void handleOk() {
		if (null == prod) {
			prod = new ProductModel();
		}
		
		prod.setName(nameField.getText());
		try {
			Double pc = Double.parseDouble(priceField.getText());
			prod.setPrice(pc);
		} catch (NumberFormatException nfe) {
			prod.setPrice(20.0);
		}
		
		try {
			Integer pc = Integer.parseInt(typeField.getText());
			prod.setType(pc);
		} catch (NumberFormatException nfe) {
			prod.setType(1);
		}
		prod.setBrand(brandField.getText());
		prod.setExpiration(expirationField.getValue());
		
		
		if (prod.toProduct().isValid() && null != stage) {
			stage.close();
		} else {
			POSUtil.showAlert("Algunos de los datos no son válidos.");
		}
	}
	
	public void setProduct(ProductModel user) {
		this.prod = user;
		fillForm(prod);
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	/**
	 * Llena el formulario con la información de un usuario.
	 */
	private void fillForm(ProductModel prod) {
		if (null == prod) {
			nameField.setText("");
			priceField.setText("");
			typeField.setText("");
			brandField.setText("");
			expirationField.setValue(LocalDate.now());
		} else {
			nameField.setText(prod.getName());
			priceField.setText(String.valueOf(prod.getPrice()));
			typeField.setText(String.valueOf(prod.getType()));
			brandField.setText(prod.getBrand());
			expirationField.setValue(POSUtil.parse(prod.getExpiration()));
		}
	}
	
	/**
	 * Obtiene el usuario relacionado con el formulario.
	 */
	public ProductModel getProduct() {		
		return prod;
	}
}
