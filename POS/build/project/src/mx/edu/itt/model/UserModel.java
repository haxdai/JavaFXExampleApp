package mx.edu.itt.model;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

import com.sun.xml.internal.ws.util.StringUtils;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mx.edu.itt.util.POSUtil;

/**
 * Clase que encapsula un Usuario a través de propiedades de JavaFX.
 * @author hasdai
 *
 */
public class UserModel {
	private int id;
	private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty street;
    private final IntegerProperty postalCode;
    private final StringProperty city;
    private final StringProperty email;
    private final ObjectProperty<LocalDate> birthday;

    /**
     * Constructor.
     */
    public UserModel() {
    	firstName = new SimpleStringProperty("");
		lastName = new SimpleStringProperty("");
		street = new SimpleStringProperty("");
		city = new SimpleStringProperty("");
		email = new SimpleStringProperty("");
		postalCode = new SimpleIntegerProperty(233);
		birthday = new SimpleObjectProperty<>(LocalDate.now());
    }
    
    /**
     * Constructor.
     * @param user
     */
	public UserModel(User user) {
		id = user.getId();
		firstName = new SimpleStringProperty(user.getFirstName());
		lastName = new SimpleStringProperty(user.getLastName());
		street = new SimpleStringProperty(user.getAddress());
		city = new SimpleStringProperty(user.getCity());
		email = new SimpleStringProperty(user.getEmail());
		postalCode = new SimpleIntegerProperty(user.getPostalcode());
		birthday = new SimpleObjectProperty<>(user.getBirthDay().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
	}
	
	/**
	 * @return the email
	 */
	public StringProperty getEmailProperty() {
		return email;
	}
	
	public String getEmail() {
		return email.get();
	}
	
	public void setEmail(String value) {
		email.set(value);
	}

	/**
	 * @return the firstName
	 */
	public StringProperty getFirstNameProperty() {
		return firstName;
	}
	
	public String getFirstName() {
		return firstName.get();
	}
	
	public void setFirstName(String value) {
		firstName.set(value);
	}

	/**
	 * @return the lastName
	 */
	public StringProperty getLastNameProperty() {
		return lastName;
	}
	
	public String getLastName() {
		return lastName.get();
	}
	
	public void setLastName(String value) {
		lastName.set(value);
	}

	/**
	 * @return the street
	 */
	public StringProperty getStreetProperty() {
		return street;
	}
	
	public String getStreet() {
		return street.get();
	}
	
	public void setStreet(String value) {
		street.set(value);
	}

	/**
	 * @return the postalCode
	 */
	public IntegerProperty getPostalCodeProperty() {
		return postalCode;
	}
	
	public int getPostalCode() {
		return postalCode.get();
	}
	
	public void setPostalCode(int value) {
		postalCode.set(value);
	}

	/**
	 * @return the city
	 */
	public StringProperty getCityProperty() {
		return city;
	}
	
	public String getCity() {
		return city.get();
	}
	
	public void setCity(String value) {
		city.set(value);
	}

	/**
	 * @return the birthday
	 */
	public ObjectProperty<LocalDate> getBirthdayProperty() {
		return birthday;
	}
	
	public String getBirthDay() {
		return POSUtil.format(birthday.get());
	}
	
	public void setBirthDay(LocalDate value) {
		birthday.set(value);
	}
	
	/**
	 * Convierte el objeto a un usuario para almacenamiento en servicio de gestión de usuarios.
	 * @return {@link User}
	 */
	public User toUser() {
		User ret = new User();
		ret.setId(id);
		ret.setFirstName(firstName.get());
		ret.setLastName(lastName.get());
		ret.setAddress(street.get());
		ret.setPostalcode(postalCode.get());
		ret.setEmail(email.get());
		ret.setCity(city.get());
		ret.setBirthDay(Date.valueOf(birthday.get()));
		
		return ret;
	}
}
