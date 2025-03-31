package com.main.service;

import com.main.entities.Category;
import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories(int page, int size);
    Category getCategoryById(Long id);
    Category createCategory(Category category);
    Category updateCategory(Long id, Category category);
    void deleteCategory(Long id);
}
