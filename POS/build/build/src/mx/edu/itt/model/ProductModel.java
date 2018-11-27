package mx.edu.itt.model;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

import com.sun.xml.internal.ws.util.StringUtils;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mx.edu.itt.util.POSUtil;

/**
 * Clase que encapsula un Producto a través de propiedades de JavaFX.
 * @author hasdai
 *
 */
public class ProductModel {
	private int id;
	private final StringProperty name;
    private final DoubleProperty price;
    private final IntegerProperty type;
    private final StringProperty brand;
    private final ObjectProperty<LocalDate> expiration;

    /**
     * Constructor.
     */
    public ProductModel() {
    	name = new SimpleStringProperty("");
    	price = new SimpleDoubleProperty(20);
		type = new SimpleIntegerProperty(1);
    	brand = new SimpleStringProperty("");
		expiration = new SimpleObjectProperty<>(LocalDate.now());
    }
    
    /**
     * Constructor.
     * @param user
     */
	public ProductModel(Product prod) {
		id = prod.getId();
		name = new SimpleStringProperty(prod.getName());
    	price = new SimpleDoubleProperty(prod.getPrice());
		type = new SimpleIntegerProperty(prod.getType());
    	brand = new SimpleStringProperty(prod.getBrand());
		expiration = new SimpleObjectProperty<>(prod.getExpiration().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public StringProperty getNameProperty() {
		return name;
	}
	
	public String getName() {
		return name.get();
	}
	
	public void setName(String value) {
		name.set(value);
	}

	/**
	 * @return the price
	 */
	public DoubleProperty getPriceProperty() {
		return price;
	}
	
	public Double getPrice() {
		return price.get();
	}
	
	public void setPrice(Double value) {
		price.set(value);
	}

	/**
	 * @return the type
	 */
	public IntegerProperty getTypeProperty() {
		return type;
	}
	
	public Integer getType() {
		return type.get();
	}
	
	public void setType(Integer value) {
		type.set(value);
	}

	/**
	 * @return the brand
	 */
	public StringProperty getBrandProperty() {
		return brand;
	}
	
	public String getBrand() {
		return brand.get();
	}
	
	public void setBrand(String value) {
		brand.set(value);
	}

	/**
	 * @return the expiration
	 */
	public ObjectProperty<LocalDate> getExpirationProperty() {
		return expiration;
	}
	
	public String getExpiration() {
		return POSUtil.format(expiration.get());
	}
	
	public void setExpiration(LocalDate value) {
		expiration.set(value);
	}

	/**
	 * Convierte el objeto a un producto para almacenamiento en servicio de gestión de productos.
	 * @return {@link User}
	 */
	public Product toProduct() {
		Product ret = new Product();
		ret.setId(id);
		ret.setName(name.get());
		ret.setPrice(price.get());
		ret.setType(type.get());
		ret.setBrand(brand.get());
		ret.setExpiration(Date.valueOf(expiration.get()));
		
		return ret;
	}
}
