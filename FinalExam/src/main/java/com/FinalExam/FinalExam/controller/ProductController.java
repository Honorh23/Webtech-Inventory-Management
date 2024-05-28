package com.FinalExam.FinalExam.controller;

import com.FinalExam.FinalExam.dto.ProductDTO;
import com.FinalExam.FinalExam.model.Category;
import com.FinalExam.FinalExam.model.Product;
import com.FinalExam.FinalExam.model.Supplier;
import com.FinalExam.FinalExam.repository.CategoryRepository;
import com.FinalExam.FinalExam.repository.ProductRepository;
import com.FinalExam.FinalExam.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("http://localhost:3000")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @GetMapping("/product")
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/product/{id}")
    public ProductDTO getProductById(@PathVariable Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return convertToDTO(product);
    }

    @PostMapping("/product")
    public ProductDTO createProduct(@RequestBody ProductDTO productDTO) {
        Product product = convertToEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }

    @PutMapping("/product/{id}")
    public ProductDTO updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setQuantity(productDTO.getQuantity());

        Category category = categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(() -> new RuntimeException("Category not found"));
        existingProduct.setCategory(category);

        Supplier supplier = supplierRepository.findById(productDTO.getSupplierId()).orElseThrow(() -> new RuntimeException("Supplier not found"));
        existingProduct.setSupplier(supplier);

        Product updatedProduct = productRepository.save(existingProduct);
        return convertToDTO(updatedProduct);
    }

    @DeleteMapping("/product/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setQuantity(product.getQuantity());

        // Check if category is not null before accessing its ID
        if (product.getCategory() != null) {
            productDTO.setCategoryId(product.getCategory().getId());
        }

        // Check if supplier is not null before accessing its ID
        if (product.getSupplier() != null) {
            productDTO.setSupplierId(product.getSupplier().getId());
        }

        return productDTO;
    }


    private Product convertToEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());

        // Check if category ID is not null
        if (productDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            product.setCategory(category);
        } else {
            throw new IllegalArgumentException("Category ID cannot be null");
        }

        // Check if supplier ID is not null
        if (productDTO.getSupplierId() != null) {
            Supplier supplier = supplierRepository.findById(productDTO.getSupplierId())
                    .orElseThrow(() -> new RuntimeException("Supplier not found"));
            product.setSupplier(supplier);
        } else {
            throw new IllegalArgumentException("Supplier ID cannot be null");
        }

        return product;
    }

}
