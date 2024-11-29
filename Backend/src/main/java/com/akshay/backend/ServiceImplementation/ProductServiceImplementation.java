package com.akshay.backend.ServiceImplementation;

import com.akshay.backend.Model.Product;
import com.akshay.backend.Repository.ProductRepository;
import com.akshay.backend.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ProductServiceImplementation implements ProductService {

    private final ProductRepository productRepository;
    @Override
    public Product findProductById(Long id) {
        Optional<Product>product= productRepository.findById(id);
        return product.orElse(null);
    }

    @Override
    public Product findProductByName(String name)
    {
        Optional<Product> product=productRepository.findByName(name);
        return product.orElse(null);
    }


    @Override
    public List<Product> findAllProduct() {
        return productRepository.findAll();
    }

    //    it should return only the product whose quantity is more than 0
    @Override
    public List<Product> findAvailableProduct() {
        return productRepository.findAvailableProducts();
    }


    @Override
    public Product AddProduct(Product product) {
        int quantity=0;
        Product newProduct = new Product();
        newProduct.setName(product.getName());
        newProduct.setDescription(product.getDescription());
        newProduct.setLocationInMall(product.getLocationInMall());
        newProduct.setPrice(product.getPrice());
        Optional<Product>existingProduct= productRepository.findByName(product.getName());
        if(existingProduct.isPresent())
        {
            quantity= quantity+existingProduct.get().getStockQuantity();
        }

        newProduct.setStockQuantity(quantity+product.getStockQuantity());

        return productRepository.save(newProduct);
    }

    @Override
    public String RemoveProduct(Long id) {
        productRepository.deleteById(id);
        return "Product deleted";
    }


}
