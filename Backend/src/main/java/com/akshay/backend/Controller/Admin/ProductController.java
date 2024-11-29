package com.akshay.backend.Controller.Admin;

import com.akshay.backend.Model.Product;
import com.akshay.backend.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/add")
    public Product addProduct(@RequestBody Product product)
    {
       return productService.AddProduct(product);
    }

    @GetMapping("/")
    public List<Product> findAvalableProducts()
    {
        return productService.findAvailableProduct();
    }

    @GetMapping("/all")
    public List<Product> findAllProducts()
    {
        return  productService.findAllProduct();
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id)
    {
       return productService.RemoveProduct(id);

    }

    @PostMapping("/name/{name}")
    public Product findByName(@PathVariable String name)
    {
        return productService.findProductByName(name);
    }

    @GetMapping("/id/{id}")
    public Product findById(@PathVariable Long id)
    {
        return productService.findProductById(id);
    }

}
