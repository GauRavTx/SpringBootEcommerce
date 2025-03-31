package com.main.service;

import com.main.entities.Product;
import java.util.List;

public interface ProductService {
    List<Product> getAllProducts(int page, int size);
    Product getProductById(Long id);
    Product createProduct(Product product);
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);
}
