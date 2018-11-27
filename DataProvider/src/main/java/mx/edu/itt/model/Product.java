package mx.edu.itt.model;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * POJO para manipular productos.
 * @author hasdai
 *
 */
public class Product {
	private Integer id;
	private String name;
	private String brand;
	private int type;
	private double price;
	private Date expiration;
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}
	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	/**
	 * @return the expiration
	 */
	public Date getExpiration() {
		return expiration;
	}
	/**
	 * @param expiration the expiration to set
	 */
	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}
	
	/**
	 * @return the brand
	 */
	public String getBrand() {
		return brand;
	}
	/**
	 * @param brand the brand to set
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	/**
	 * Verifica si la instancia del producto tiene los campos requeridos.
	 * @return true si la instancia tiene los campos requeridos.
	 */
	public boolean isValid() {
		return StringUtils.isNotBlank(name) && price > 0
				&& type > 0 && StringUtils.isNotBlank(brand);
	}
	
	@Override
	public String toString() {
		StringBuilder ret = new StringBuilder();
		ret.append("{\n");
		ret.append("name: ").append(name).append(",\n");
		ret.append("price: $").append(price).append(",\n");
		ret.append("type: ").append(type).append(",\n");
		ret.append("brand: ").append(brand).append(",\n");
		ret.append("expiration: ").append(expiration).append(",\n");
		ret.append("}");
		
		return ret.toString();
	}

}
