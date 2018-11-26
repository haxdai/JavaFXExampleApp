package mx.edu.itt.provider.service;

import java.util.List;

import mx.edu.itt.model.User;

/**
 * Servicio de almacenamiento de usuarios.
 * @author hasdai
 *
 */
public interface UserService {
	/**
	 * Almacena un opbjeto en el repositorio.
	 * @param sample Objeto a almacenar.
	 * @return Objeto almacenado o null si el objeto no se almacenó.
	 */
	User save (User sample);
	
	/**
	 * Devuelve todos los objetos en el repositorio.
	 * @return Lista de objetos.
	 */
    List<User> findAll();
    
    /**
     * Elimina un objeto del repositorio.
     * @param sample Objeto a eliminar.
     * @return Objeto eliminado o null si el objeto no se pudo eliminar.
     */
    User remove(User sample);
    
    /**
     * Elimina un objeto por ID.
     * @param id
     * @return
     */
    User removeById(int id);
    
    /**
     * Busca un objeto por ID.
     * @param id
     * @return
     */
    User findById(int id);
    
    /**
     * Elimina todos los elementos del repositorio.
     */
    void clear();
}
