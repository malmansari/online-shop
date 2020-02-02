package de.malmansari.playground.onlineshop.exception;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * <pre>
 * This handler intercepts four different errors that the 
 * service layer can throw:
 *  - {@link ProductNotFoundException} 
 *  - {@link ProductHasNotEnoughStockException}
 *  - {@link ConstraintViolationException}
 *  - {@link RuntimeException}
 *  
 *  See the respective methods for specific details.
 * </pre>
 * 
 * @author malmansari
 *
 */
@RestControllerAdvice
public class OnlineShopExceptionHandler {
    /**
     * <pre>
     * Intercepts the errors when the product is not found, and 
     * delegates it to the view layer as a not found HTTP error: 404.
     * </pre>
     * 
     * @param e the exception caught
     * @return the error message
     */
	@ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleProductNotFoundError(final ProductNotFoundException e) {
    	return e.getMessage();
    }
    
	/**
	 * <pre>
     * Intercepts the errors when the ordered amount of the product 
     * exceeds the available amount in the stock. Then it delegates 
     * it to the view layer as a bad request HTTP error: 400.
     * </pre>
     * 
	 * @param e the exception caught
     * @return the error message
	 */
    @ExceptionHandler(ProductHasNotEnoughStockException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleProductHasNotEnoughStockError(final ProductHasNotEnoughStockException e) {
    	return e.getMessage();
    }
    
    /**
     * <pre>
     * Intercepts the errors when a given request parameter is wrong.
     * Then it delegates it to the view layer as a bad request HTTP 
     * error: 400.
     * </pre>
     * 
     * @param e the exception caught
     * @return the error message
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleConstraintViolationError(final ConstraintViolationException e) {
    	return e.getMessage();
    }
    
    /**
     * <pre>
     * Intercepts any runtime error. Then it delegates it to the 
     * view layer as a internal server HTTP error: 500.
     * </pre>
     * 
     * @param e the exception caught
     * @return the error message
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGeneralError(final RuntimeException e) {
    	return e.getMessage();
    }
}