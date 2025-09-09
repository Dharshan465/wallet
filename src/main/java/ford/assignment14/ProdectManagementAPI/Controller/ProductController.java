package ford.assignment14.ProdectManagementAPI.Controller;


import ford.assignment14.ProdectManagementAPI.Model.Product;
import ford.assignment14.ProdectManagementAPI.ProductException.InvalidProductDataException;
import ford.assignment14.ProdectManagementAPI.ProductException.ProductException;
import ford.assignment14.ProdectManagementAPI.ProductException.ProductNotFoundException;
import ford.assignment14.ProdectManagementAPI.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) throws ProductException {
        Product createProduct= productService.addProduct(product);
        return new ResponseEntity<>(createProduct,HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id) throws ProductNotFoundException{
        Product product= productService.getProductById(id);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Collection<Product>> getAllProducts() throws ProductNotFoundException{
        Collection<Product> products= productService.getAllProducts();
        return new ResponseEntity<>(products,HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer id, @RequestBody Product product) throws ProductNotFoundException, InvalidProductDataException {
        Product updatedProduct= productService.updateProduct(id,product);
        return new ResponseEntity<>(updatedProduct,HttpStatus.OK);
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<Product> updateStock(@PathVariable Integer id, @RequestParam Integer stock) {
        Product updatedProduct = productService.updateStock(id, stock);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id) throws ProductNotFoundException{
        String response= productService.deleteProduct(id);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    
    @GetMapping("/search")
    public ResponseEntity<Product> getProductByName(@RequestParam(value = "name", defaultValue = "User", required = false) String keyword ) throws ProductNotFoundException{
        Product product= productService.searchProductByName(keyword);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }
    

    


}
