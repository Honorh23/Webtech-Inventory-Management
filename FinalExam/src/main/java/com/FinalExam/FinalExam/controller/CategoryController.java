package com.FinalExam.FinalExam.controller;

import com.FinalExam.FinalExam.exception.CategoryNotFoundException;
import com.FinalExam.FinalExam.model.Category;
import com.FinalExam.FinalExam.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping("/category")
    public Category createCategory(@RequestBody Category newCategory) {
        return categoryRepository.save(newCategory);
    }

    @GetMapping("/category")
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @GetMapping("/category/{id}")
    public Category getCategoryById(@PathVariable Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
    }

    @PutMapping("/category/{id}")
    public Category updateCategory(@RequestBody Category newCategory, @PathVariable Long id) {
        return categoryRepository.findById(id).map(category -> {
            category.setName(newCategory.getName());
            category.setDescription(newCategory.getDescription());
            category.setProducts(newCategory.getProducts());
            return categoryRepository.save(category);
        }).orElseThrow(() -> new CategoryNotFoundException(id));
    }

    @DeleteMapping("/category/{id}")
    public String deleteCategory(@PathVariable Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException(id);
        }
        categoryRepository.deleteById(id);
        return "Category deleted successfully";
    }
}
