package de.malmansari.playground.onlineshop.service;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.validation.annotation.Validated;

import de.malmansari.playground.onlineshop.exception.OnlineShopExceptionHandler;
import de.malmansari.playground.onlineshop.model.Product;

/**
 * <pre>
 * The service layer for {@link Product} related requests.
 *
 * Currently, it supports the following operations:
 *  - get all products
 *  - order a product
 *  - increment a product stock.
 *  
 *  Please note that the order operation is really simple here, 
 *  we just need to provide how many items from the product we 
 *  want to order. This will decrease the stock value of the 
 *  product, see {@link Product#getStock()}.
 *  
 *  This API can throw a couple of exceptions, which are 
 *  going to intercepted by the {@link OnlineShopExceptionHandler}
 *  aspect and delegated further nicely to the view layer. 
 * </pre>
 * 
 * @author malmansari
 *
 */
@Validated
public interface ProductService {
	/**
	 * Returns all available products.
	 * 
	 * @return the list of products
	 */
    @NotNull Iterable<Product> getAllProducts();

    /**
     * <pre>
     * Represents a simple notion of order process.
     * 
     * It checks if the product designated by its id is found, 
     * then if so, it checks if the given quantity is allowed,
     * i.e., not bigger than available stock of this product.
     * </pre>
     * 
     * @param id the id of the product to be ordered, must be strictly positive
     * @param quantity the number of the items that to be ordered from the product, must be strictly positive
     * 
     * @return the updated product if found
     */
    Product order(final @Positive long id, final @Positive long quantity);
    
    /**
     * <pre>
     * Adds the given amount of items of the given product
     * to the stock.
     * </pre>
     * 
     * @param id the id of the product to be ordered, must be strictly positive
     * @param quantity the number of the items that to be ordered from the product, must be strictly positive
     * 
     * @return the updated product if found
     */
    Product addToStock(final @Positive long id, final @Positive long quantity);
}