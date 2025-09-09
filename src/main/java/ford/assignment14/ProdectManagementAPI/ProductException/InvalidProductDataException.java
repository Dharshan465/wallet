package ford.assignment14.ProdectManagementAPI.ProductException;

public class InvalidProductDataException extends RuntimeException {
  public InvalidProductDataException(String message) {
    super(message);
  }
}
