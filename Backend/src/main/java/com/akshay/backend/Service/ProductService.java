package com.akshay.backend.Service;

import com.akshay.backend.Model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    Product findProductById(Long id);
    Product AddProduct(Product product);
    String RemoveProduct(Long id);

    Product findProductByName(String name);
    List<Product> findAllProduct();

    List<Product> findAvailableProduct();

}
