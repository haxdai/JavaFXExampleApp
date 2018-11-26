package mx.edu.itt.provider.service;

import java.util.List;

import mx.edu.itt.model.Product;

/**
 * Servicio de almacenamiento de productos.
 * @author hasdai
 *
 */
public interface ProductService {
	/**
	 * Almacena un opbjeto en el repositorio.
	 * @param sample Objeto a almacenar.
	 * @return Objeto almacenado o null si el objeto no se almacenó.
	 */
	Product save (Product sample);
	
	/**
	 * Devuelve todos los objetos en el repositorio.
	 * @return Lista de objetos.
	 */
    List<Product> findAll();
    
    /**
     * Elimina un objeto del repositorio.
     * @param sample Objeto a eliminar.
     * @return Objeto eliminado o null si el objeto no se pudo eliminar.
     */
    Product remove(Product sample);
    
    /**
     * Elimina un objeto por ID.
     * @param id
     * @return
     */
    Product removeById(int id);
    
    /**
     * Busca un objeto por ID.
     * @param id
     * @return
     */
    Product findById(int id);
    
    /**
     * Elimina todos los elementos del repositorio.
     */
    void clear();
}
