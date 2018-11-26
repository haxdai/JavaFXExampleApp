package mx.edu.itt.provider.service.impl;

import java.util.List;

import mx.edu.itt.model.Product;
import mx.edu.itt.provider.repository.ProductRepository;
import mx.edu.itt.provider.repository.impl.SQLProductRepository;
import mx.edu.itt.provider.service.ProductService;

/**
 * Implementación de servicio de gestión de productos con SQL por defecto.
 * @author hasdai
 *
 */
public class ProductServiceImpl implements ProductService {
	private ProductRepository repository;

	public ProductServiceImpl() {
		repository = SQLProductRepository.getInstance();
        ((SQLProductRepository)repository).connect("jdbc:mysql://localhost/inventariodb?useSSL=false", "root", "");
	}
	
	public ProductServiceImpl(ProductRepository repo) {
		this.repository = repo;
	}
	
	@Override
	public Product save(Product sample) {
		return repository.save(sample);
	}

	@Override
	public List<Product> findAll() {
		return repository.findAll();
	}

	@Override
	public Product remove(Product sample) {
		return repository.remove(sample);
	}

	@Override
	public Product removeById(int id) {
		return repository.removeById(id);
	}

	@Override
	public Product findById(int id) {
		return repository.findById(id);
	}

	@Override
	public void clear() {
		repository.clear();
	}
}
