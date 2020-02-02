package de.malmansari.playground.onlineshop.exception;

/**
 * <pre>
 * This exception is thrown if requested number of the 
 * items to be ordered are more than the available ones
 * in the stock
 * </pre>
 * 
 * @author malmansari
 *
 */
public class ProductHasNotEnoughStockException extends RuntimeException {

	private static final long serialVersionUID = 4550491831681383226L;

	public ProductHasNotEnoughStockException() {
        super();
    }

    public ProductHasNotEnoughStockException(final String message) {
        super(message);
    }
}