package ford.assignment14.ProdectManagementAPI.Service;

import ford.assignment14.ProdectManagementAPI.Model.Product;
import ford.assignment14.ProdectManagementAPI.ProductException.InvalidProductDataException;
import ford.assignment14.ProdectManagementAPI.ProductException.ProductException;
import ford.assignment14.ProdectManagementAPI.ProductException.ProductNotFoundException;

import java.util.Collection;

public interface ProductService {
    Product addProduct(Product product) throws ProductException,InvalidProductDataException;

    Product getProductById(Integer productId) throws ProductNotFoundException;

    Collection<Product> getAllProducts() throws ProductNotFoundException;

    Product updateProduct(Integer productId, Product newProduct) throws ProductNotFoundException, InvalidProductDataException;

    Product updateStock(Integer productId, Integer quantity) throws ProductNotFoundException, InvalidProductDataException;

    String deleteProduct(Integer productId) throws ProductNotFoundException;

    Product searchProductByName(String keyword) throws ProductNotFoundException;




}
