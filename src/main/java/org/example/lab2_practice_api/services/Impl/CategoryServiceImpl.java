package org.example.lab2_practice_api.services.Impl;

import org.example.lab2_practice_api.entities.Category;
import org.example.lab2_practice_api.repositories.CategoryRepository;
import org.example.lab2_practice_api.services.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
