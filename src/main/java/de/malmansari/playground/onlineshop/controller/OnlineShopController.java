package de.malmansari.playground.onlineshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.malmansari.playground.onlineshop.exception.OnlineShopExceptionHandler;
import de.malmansari.playground.onlineshop.model.Product;
import de.malmansari.playground.onlineshop.service.ProductService;

/**
 * <pre>
 * The view layer representation of the online shop.
 * 
 * It supports currently 3 API functionalities:
 *  - getting the full list of the available products
 *  - ordering a product with certain quantity
 *  - adding items of a product to the stock
 * </pre>
 * 
 * @see OnlineShopExceptionHandler
 * @author malmansari
 *
 */
@RestController
@RequestMapping("/api")
public class OnlineShopController {
	
	/**
	 * The product service bean.
	 */
	@Autowired
	private ProductService productService;

	/**
	 * Gets all available products in DB.
	 * 
	 * @return product list
	 */
    @GetMapping("/products")
    public Iterable<Product> getProducts() {
        return productService.getAllProducts();
    }

    /**
     * Performs an order operation for the given product.
     * 
     * @param productId the product ID to be ordered 
     * @param quantity the amount of items to be ordered from the product
     */
    @GetMapping("/order/products/{productId}/{quantity}")
    public Product order(@PathVariable long productId, @PathVariable long quantity) {
    	return productService.order(productId, quantity);
    }
    
    /**
     * Performs the stock operation to increase the available product stock.
     * 
     * @param productId the product ID to be increased in the stock
     * @param quantity the amount of the items to be added to the stock
     */
    @GetMapping("/add/products/{productId}/{quantity}")
    public Product addToStock(@PathVariable long productId, @PathVariable long quantity) {
    	return productService.addToStock(productId, quantity);
    }
}