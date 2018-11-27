package mx.edu.itt.provider.repository;

import java.util.List;

/**
 * Interface para implementar proveedores de datos.
 * @author hasdai
 *
 */
public interface DataRepository<T, S> {
	
	/**
	 * Almacena un opbjeto en el repositorio.
	 * @param sample Objeto a almacenar.
	 * @return Objeto almacenado o null si el objeto no se almacenó.
	 */
	T save (T sample);
	
	/**
	 * Devuelve todos los objetos en el repositorio.
	 * @return Lista de objetos.
	 */
    List<T> findAll();
    
    /**
     * Elimina un objeto del repositorio.
     * @param sample Objeto a eliminar.
     * @return Objeto eliminado o null si el objeto no se pudo eliminar.
     */
    T remove(T sample);
    
    /**
     * Elimina un objeto por ID.
     * @param id
     * @return
     */
    T removeById(S id);
    
    /**
     * Busca un objeto por ID.
     * @param id
     * @return
     */
    T findById(S id);
    
    /**
     * Elimina todos los elementos del repositorio.
     */
    void clear();
    
}