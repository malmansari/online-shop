package de.malmansari.playground.onlineshop.service.impl;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.malmansari.playground.onlineshop.exception.ProductHasNotEnoughStockException;
import de.malmansari.playground.onlineshop.exception.ProductNotFoundException;
import de.malmansari.playground.onlineshop.model.Product;
import de.malmansari.playground.onlineshop.repository.ProductRepository;
import de.malmansari.playground.onlineshop.service.ProductService;

/**
 * The implementation of the product service layer.
 * 
 * @author malmansari
 *
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {
	private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
	@Override
	public @NotNull Iterable<Product> getAllProducts() {
        return productRepository.findAll();

	}

	@Override
	public Product order(final long id, final long quantity) {
		Product product = productRepository
		          .findById(id)
		          //.filter(p -> p.getStock() >= quantity)
		          .orElseThrow(() -> new ProductNotFoundException("Product not found"));
		
		if (product.getStock() < quantity) {
			throw new ProductHasNotEnoughStockException("Product has not enough stock for your order");
		}
		
		return productRepository.save(new Product(id, product.getName(), product.getStock() - quantity)); 
	}

	@Override
	public Product addToStock(final long id, final long quantity) {
		Product product = productRepository
		          .findById(id)
		          .orElseThrow(() -> new ProductNotFoundException("Product not found"));
		
		return productRepository.save(new Product(id, product.getName(), product.getStock() + quantity));
	}
}
