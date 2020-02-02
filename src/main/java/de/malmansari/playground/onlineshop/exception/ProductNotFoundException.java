package de.malmansari.playground.onlineshop.exception;

/**
 * This exception is thrown if the Product object is not found.
 * 
 * @author malmansari
 *
 */
public class ProductNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -1888622937214847466L;

	public ProductNotFoundException() {
        super();
    }

	public ProductNotFoundException(final String message) {
        super(message);
    }
}