package com.rest.controller.question;

import com.rest.model.question.Category;
import com.rest.service.question.CategoryService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@CrossOrigin("*")
@Data
public class CategoryController {
    private final CategoryService categoryService;

    //add category
    @PostMapping("")
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        return ResponseEntity.ok(categoryService.addCategory(category));
    }

    //update category
    @PutMapping("")
    public Category updateCategory(@RequestBody Category category) {
        return categoryService.updateCategory(category);
    }

    //get single category
    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getSingleCategory(@PathVariable("categoryId") Long categoryId) {
        return ResponseEntity.ok(categoryService.getCategory(categoryId));
    }

    //get all category
    @GetMapping("")
    public ResponseEntity<?> getAllCategory() {
        return ResponseEntity.ok(categoryService.getAllCategory());
    }

    //delete category
    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable("categoryId") Long categoryId) {
        categoryService.deleteCategory(categoryId);
    }
}
