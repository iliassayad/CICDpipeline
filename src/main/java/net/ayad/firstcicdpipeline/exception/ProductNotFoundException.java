package net.ayad.firstcicdpipeline.exception;

public class ProductNotFoundException extends ProductException {
    public ProductNotFoundException(Long id) {
        super("Product not found with ID: " + id);
    }
}
