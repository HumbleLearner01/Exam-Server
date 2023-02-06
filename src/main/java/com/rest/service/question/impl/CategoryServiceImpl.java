package com.rest.service.question.impl;

import com.rest.model.question.Category;
import com.rest.repository.question.CategoryRepository;
import com.rest.service.question.CategoryService;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Data
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepo;

    @Override
    public Category addCategory(Category category) {
        return categoryRepo.save(category);
    }

    @Override
    public Category updateCategory(Category category) {
        return categoryRepo.save(category);
    }

    @Override
    public Category getCategory(Long categoryId) {
        return categoryRepo.findById(categoryId).get();
    }

    @Override
    public Set<Category> getAllCategory() {
        return new HashSet<>(categoryRepo.findAll());
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category = new Category();
        category.setCategoryId(categoryId);
        categoryRepo.delete(category);
    }
}