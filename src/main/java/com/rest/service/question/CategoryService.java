package com.rest.service.question;

import com.rest.model.question.Category;

import java.util.Set;

public interface CategoryService {
    Category addCategory(Category category);
    Category updateCategory(Category category);
    Category getCategory(Long categoryId);
    Set<Category> getAllCategory();
    void deleteCategory(Long categoryId);
}
