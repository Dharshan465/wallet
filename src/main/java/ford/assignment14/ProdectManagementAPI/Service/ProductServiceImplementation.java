package ford.assignment14.ProdectManagementAPI.Service;


import ford.assignment14.ProdectManagementAPI.Model.Product;
import ford.assignment14.ProdectManagementAPI.ProductException.InvalidProductDataException;
import ford.assignment14.ProdectManagementAPI.ProductException.ProductException;
import ford.assignment14.ProdectManagementAPI.ProductException.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductServiceImplementation implements ProductService {

    private final Map<Integer, Product> productRepo = new HashMap<>();


    @Override
    public Product addProduct(Product product) throws ProductException, InvalidProductDataException {
        if(product.getId() == null || product.getName() == null || product.getPrice() == null || product.getStock() == null){
            throw new InvalidProductDataException("Invalid product data provided.");
        }
        if(productRepo.containsKey(product.getId())){
            throw new ProductException("Product with ID "+product.getId()+" already exists.");
        }
        productRepo.put(product.getId(),product);
        return product;

    }

    @Override
    public Product getProductById(Integer productId) throws ProductNotFoundException {
        if(productRepo.containsKey(productId)){
            return productRepo.get(productId);
        }
        else{
            throw new ProductNotFoundException("Product with ID "+productId+" not found.");
        }


    }

    @Override
    public Collection<Product> getAllProducts() throws ProductNotFoundException {
        if(productRepo.isEmpty()){
            throw new ProductNotFoundException("No products found.");
        }
        return productRepo.values();
    }

    @Override
    public Product updateProduct(Integer productId, Product newProduct) throws ProductNotFoundException, InvalidProductDataException {

            if(!productRepo.containsKey(productId)){
                throw new ProductNotFoundException("Product with ID "+productId+" not found.");
            }
            if(newProduct.getId() == null || newProduct.getName() == null || newProduct.getPrice() == null || newProduct.getStock() == null){
                throw new InvalidProductDataException("Invalid product data provided.");
            }
            if(newProduct.getPrice()<=0){
                throw new InvalidProductDataException("Product price must be greater than zero.");
            }
            productRepo.put(productId,newProduct);
            return newProduct;


}


    @Override
    public Product updateStock(Integer productId, Integer quantity) throws ProductNotFoundException, InvalidProductDataException {
        if(!productRepo.containsKey(productId)){
            throw new ProductNotFoundException("Product with ID "+productId+" not found.");
        }
        if(quantity == null || quantity < 0){
            throw new InvalidProductDataException("Invalid stock quantity provided.");
        }
        Product product = productRepo.get(productId);
        product.setStock(quantity);
        productRepo.put(productId,product);
        return product;
    }

    @Override
    public String deleteProduct(Integer productId) throws ProductNotFoundException {
        if(!productRepo.containsKey(productId)){
            throw new ProductNotFoundException("Product with ID "+productId+" not found.");
        }
        productRepo.remove(productId);
        return "Product with ID "+productId+" deleted successfully.";
    }

    @Override
    public Product searchProductByName(String keyword) throws ProductNotFoundException {
        for(Product product : productRepo.values()){
            if(product.getName().toLowerCase().contains(keyword.toLowerCase())){
                return product;
            }
        }
        throw new ProductNotFoundException("No product found with name containing '"+keyword+"'.");
    }
}
