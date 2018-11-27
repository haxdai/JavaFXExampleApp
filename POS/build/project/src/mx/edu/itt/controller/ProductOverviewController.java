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
import mx.edu.itt.model.Product;
import mx.edu.itt.model.ProductModel;
import mx.edu.itt.model.User;
import mx.edu.itt.model.UserModel;
import mx.edu.itt.provider.service.ProductService;
import mx.edu.itt.provider.service.UserService;
import mx.edu.itt.provider.service.impl.ProductServiceImpl;
import mx.edu.itt.provider.service.impl.UserServiceImpl;
import mx.edu.itt.util.POSUtil;

/**
 * Constrolador para la vista de productos.
 * @author hasdai
 *
 */
public class ProductOverviewController {
	private static final Logger LOG = Logger.getLogger(ProductOverviewController.class.getName());
	private static ProductService service = new ProductServiceImpl();
	private Stage stage;
	
	@FXML
    private TableView<ProductModel> productTable;
    @FXML
    private TableColumn<ProductModel, String> nameColumn;
    @FXML
    private TableColumn<ProductModel, Double> priceColumn;

    @FXML
    private Label nameameLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label brandLabel;
    @FXML
    private Label expirationLabel;
    
    
    public ProductOverviewController() { }
    
    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty().asObject());
        
        loadProducts();
        clearLabels();
        
        productTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showProductDetails(newValue));
    }
    
    @FXML
    private void handleDeleteProduct() {
    	if (productTable.getSelectionModel().isEmpty()) {
    		POSUtil.showAlert("No existen productos para eliminar");
    	} else {
	    	ProductModel selected = productTable.getSelectionModel().getSelectedItem();
	    	if (null != selected) {
		    	Alert alert = POSUtil.showConfirmAlert("Desea eliminar el producto?");
		    	if (alert.getResult() == ButtonType.YES) {
			    	service.remove(selected.toProduct());
			    	productTable.getItems().remove(selected);
		    	}
	    	} else {
	    		POSUtil.showAlert("No se ha seleccionado un producto");
	    	}
    	}
    }
    
    @FXML
    private void handleEditProduct() {
    	if (productTable.getSelectionModel().isEmpty()) {
    		POSUtil.showAlert("No existen productos para editar");
    	} else {
	    	ProductModel selected = productTable.getSelectionModel().getSelectedItem();
	    	if (null != selected) {
		    	showProductEdit(selected);
	    	} else {
	    		POSUtil.showAlert("No se ha seleccionado un producto");
	    	}
    	}
    }
    
    @FXML
    private void handleNewProduct() {
    	showProductEdit(null);
    }
    
    /**
     * Muestra la ventana de edición de productos.
     */
    private void showProductEdit(ProductModel prod) {
    	try {
    		//Carga la vista de edición de usuarios
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/ProductEditView.fxml"));
            AnchorPane page = loader.load();

            //Se establece inicio modal
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);
            
            //Se despliega en una escena independiente
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            //Se carga el controlador
            ProductEditController controller = loader.getController();
            controller.setStage(dialogStage);
            controller.setProduct(prod);
            
            if (null != prod) {
            	dialogStage.setTitle("Editar Producto");
            } else {
            	dialogStage.setTitle("Nuevo Producto");
            }

            dialogStage.showAndWait();

            if (null != controller.getProduct()) {
            	service.save(controller.getProduct().toProduct());
            	loadProducts();
            }
        } catch (IOException e) {
        	LOG.log(Level.SEVERE, "No se pudo cargar la vista de productos", e);
        }
    }
    
    private void showProductDetails(ProductModel prod) {
    	if (null == prod) {
    		clearLabels();
    	} else {
    		fillLabels(prod);
    	}
    }
    
    /**
     * Llena las etiquetas del formulario con la información del producto.
     */
    private void fillLabels(ProductModel prod) {
    	nameameLabel.setText(prod.getName());
    	priceLabel.setText(String.valueOf(prod.getPrice()));
    	typeLabel.setText(String.valueOf(prod.getType()));
    	brandLabel.setText(prod.getBrand());
    	expirationLabel.setText(prod.getExpiration());
    }
    
    /**
     * Limpia las etiquetas.
     */
    private void clearLabels() {
    	nameameLabel.setText("");
    	priceLabel.setText("");
    	typeLabel.setText("");
    	brandLabel.setText("");
    	expirationLabel.setText("");
    }
    
    public void setStage(Stage stage) {
    	this.stage = stage;
    }
    
    /**
     * Carga los usuarios del servicio de gestión de productos.
     */
    private void loadProducts() {
    	ObservableList<ProductModel> prods = FXCollections.observableArrayList();

    	//Carga los usuarios de la base de datos
    	List<Product> res = service.findAll();
    	for (Product p : res) {
    		prods.add(new ProductModel(p));
    	}
    	productTable.setItems(prods);
    }
}
