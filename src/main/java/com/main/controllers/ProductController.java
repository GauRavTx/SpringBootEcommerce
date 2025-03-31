package com.main.controllers;

import com.main.entities.Product;
import com.main.repository.ProductRepository;
import com.main.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Get all products with pagination (Default: page = 0, size = 5)
     */
    @GetMapping
    public Page<Product> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "5") int size) {
        return productRepository.findAll(PageRequest.of(page, size));
    }

    /**
     * Create a new product
     */
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    /**
     * Get product by ID with category details (using traditional HashMap instead of Map.of)
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isPresent()) {
            Product p = product.get();
            Map<String, Object> response = new HashMap<>();
            response.put("id", p.getId());
            response.put("name", p.getName());
            response.put("price", p.getPrice());

            // Adding category details
            Map<String, Object> categoryMap = new HashMap<>();
            categoryMap.put("id", p.getCategory().getId());
            categoryMap.put("name", p.getCategory().getName());
            response.put("category", categoryMap);

            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(404).body("Product not found");
    }

    /**
     * Update product by ID
     */
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(updatedProduct.getName());
                    product.setPrice(updatedProduct.getPrice());
                    return productRepository.save(product);
                }).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    /**
     * Delete product by ID
     */
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return "Product deleted successfully";
    }
}
