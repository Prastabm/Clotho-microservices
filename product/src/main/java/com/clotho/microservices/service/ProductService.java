package com.clotho.microservices.service;

import com.clotho.microservices.dto.ProductRequest;
import com.clotho.microservices.dto.ProductResponse;
import com.clotho.microservices.model.Product;
import com.clotho.microservices.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService{
    private final ProductRepository productRepository;

    public Product createProduct(ProductRequest productRequest) {
        // Logic to create a product using the repository
        // Convert ProductRequest to Product entity and save it
        Product product = new Product(
            productRequest.id(),
            productRequest.name(),
            productRequest.description(),
            new BigDecimal(productRequest.price()),
            productRequest.category(),
            productRequest.imageUrl()
        );
        productRepository.save(product);
        log.info("Product created: {}", product);
        return product;
    }

    public ProductResponse getProductById(String id) {
        // Logic to retrieve a product by ID using the repository
        return productRepository.findById(id)
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice().toString(),
                        product.getCategory(),
                        product.getImageUrl()
                ))
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public List<ProductResponse> getAllProducts() {
        // Logic to retrieve all products using the repository
        return productRepository.findAll().stream().map(
            product -> new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice().toString(),
                product.getCategory(),
                product.getImageUrl()
            )
        ).toList();
    }
}
