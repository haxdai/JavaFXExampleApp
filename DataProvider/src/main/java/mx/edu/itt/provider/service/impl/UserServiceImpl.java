package mx.edu.itt.provider.service.impl;

import java.util.List;

import mx.edu.itt.model.User;
import mx.edu.itt.provider.repository.UserRepository;
import mx.edu.itt.provider.repository.impl.SQLUserRepository;
import mx.edu.itt.provider.service.UserService;

/**
 * Implementación de servicio de gestión de usuarios con SQL por defecto.
 * @author hasdai
 *
 */
public class UserServiceImpl implements UserService {
	private UserRepository repository;

	public UserServiceImpl() {
		repository = SQLUserRepository.getInstance();
        ((SQLUserRepository)repository).connect("jdbc:mysql://localhost/inventariodb?useSSL=false", "root", "");
	}
	
	public UserServiceImpl(UserRepository repo) {
		this.repository = repo;
	}
	
	@Override
	public User save(User sample) {
		return repository.save(sample);
	}

	@Override
	public List<User> findAll() {
		return repository.findAll();
	}

	@Override
	public User remove(User sample) {
		return repository.remove(sample);
	}

	@Override
	public User removeById(int id) {
		return repository.removeById(id);
	}

	@Override
	public User findById(int id) {
		return repository.findById(id);
	}

	@Override
	public void clear() {
		repository.clear();
	}
}
