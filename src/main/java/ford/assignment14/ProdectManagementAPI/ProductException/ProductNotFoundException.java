package ford.assignment14.ProdectManagementAPI.ProductException;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
