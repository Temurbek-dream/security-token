package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ProductController
{
     private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository)
    {
        this.productRepository = productRepository;
    }//equals @AutoWired

    @GetMapping(value = "/getproducts")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public List<Product> getStudents() {

        return productRepository.findAll();

    }

    @PostMapping(value = "/addproduct")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addProduct(@RequestBody Product product)
    {
        productRepository.save(product);
        return "This is added product : "+product.getName();
    }

    @GetMapping(value = "/getproduct/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Optional<Product> getStudent(@PathVariable UUID id) {

        return productRepository.findById(id);

    }

    @DeleteMapping(value = "/deleteproduct/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteStudent(@PathVariable UUID id) {

        productRepository.deleteById(id);
        return "This is deleted product :"+ id;
    }

    @PutMapping(value = "/updateproduct")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
        public ResponseEntity<Product> updating(@PathVariable(value = "id") UUID myProductId,
                                                @Valid @RequestBody Product product)
                throws ResourceNotFoundException
        {
            Product myProduct=productRepository.findById(myProductId).orElseThrow(
                    () -> new ResourceNotFoundException("User not found"+myProductId));
            myProduct.setName(product.getName());
            myProduct.setPrice(product.getPrice());
            myProduct.setDescription(product.getDescription());
            final Product updateUser=productRepository.save(myProduct);
            return ResponseEntity.ok(updateUser);
        }

}
