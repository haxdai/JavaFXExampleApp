package mx.edu.itt.model;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * POJO para manipular Usuarios.
 * @author hasdai
 *
 */
public class User {
	private Integer id;
	private String firstName;
	private String lastName;
	private String email;
	private String address;
	private String phone;
	private String city;
	private Date birthDay;
	private Integer postalcode;
	
	public User() {
		this.postalcode = 0;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}
	public Integer getPostalcode() {
		return postalcode;
	}
	public void setPostalcode(Integer postalcode) {
		this.postalcode = postalcode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	/**
	 * Verifica si la instancia del usuario tiene los campos requeridos.
	 * @return true si la instancia tiene los campos requeridos.
	 */
	public boolean isValid() {
		return StringUtils.isNotBlank(firstName) && StringUtils.isNotBlank(lastName)
				&& StringUtils.isNotBlank(address) && StringUtils.isNotBlank(email);
	}
	
	@Override
	public String toString() {
		StringBuilder ret = new StringBuilder();
		ret.append("{\n");
		ret.append("name:").append(this.firstName).append(" ").append(this.lastName).append(",\n");
		ret.append("address:").append(this.getAddress()).append(",\n");
		ret.append("email:").append(this.getEmail()).append(",\n");
		ret.append("birthday:").append(this.birthDay.getYear())
			.append("/").append(this.birthDay.getMonth())
			.append("/").append(this.birthDay.getDay()).append("\n");
		ret.append("}");
		
		return ret.toString();
	}
}
