package mx.edu.itt.provider.repository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mx.edu.itt.model.User;
import mx.edu.itt.provider.repository.UserRepository;

/**
 * Implementación de repositorio de usuarios en memoria.
 * @author hasdai
 *
 */
public class MemoryUserRepository implements UserRepository {
	private HashMap<Integer, User> data;
	private int count;
	
	public MemoryUserRepository() {
		data = new HashMap<>();
		count = 0;
	}

	@Override
	public User save(User sample) {
		if (null != sample) {
			sample.setId(count++);
			data.put(sample.getId(), sample);
		}
		return sample;
	}

	@Override
	public List<User> findAll() {
		return new ArrayList<>(data.values());
	}

	@Override
	public User remove(User sample) {
		return removeById(sample.getId());
	}

	@Override
	public User removeById(Integer id) {
		if (data.containsKey(id)) {
			return data.remove(id);
		}
		return null;
	}

	@Override
	public User findById(Integer id) {
		return data.get(id);
	}

	@Override
	public void clear() {
		data.clear();
	}
}
