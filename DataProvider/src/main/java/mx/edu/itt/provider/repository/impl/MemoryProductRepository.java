package mx.edu.itt.provider.repository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mx.edu.itt.model.Product;
import mx.edu.itt.model.User;
import mx.edu.itt.provider.repository.ProductRepository;
import mx.edu.itt.provider.repository.UserRepository;

/**
 * Implementación de repositorio de productos en memoria.
 * @author hasdai
 *
 */
public class MemoryProductRepository implements ProductRepository {
	private HashMap<Integer, Product> data;
	private int count;
	
	public MemoryProductRepository() {
		data = new HashMap<>();
		count = 0;
	}

	@Override
	public Product save(Product sample) {
		if (null != sample) {
			sample.setId(count++);
			data.put(sample.getId(), sample);
		}
		return sample;
	}

	@Override
	public List<Product> findAll() {
		return new ArrayList<>(data.values());
	}

	@Override
	public Product remove(Product sample) {
		return removeById(sample.getId());
	}

	@Override
	public Product removeById(Integer id) {
		if (data.containsKey(id)) {
			return data.remove(id);
		}
		return null;
	}

	@Override
	public Product findById(Integer id) {
		return data.get(id);
	}

	@Override
	public void clear() {
		data.clear();
	}
}
