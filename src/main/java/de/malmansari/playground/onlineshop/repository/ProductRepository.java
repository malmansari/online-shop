package de.malmansari.playground.onlineshop.repository;

import org.springframework.data.repository.CrudRepository;

import de.malmansari.playground.onlineshop.model.Product;

/**
 * The DAO layer for the {@link Product} entity.
 * 
 * @author malmansari
 *
 */
public interface ProductRepository extends CrudRepository<Product, Long> {
}