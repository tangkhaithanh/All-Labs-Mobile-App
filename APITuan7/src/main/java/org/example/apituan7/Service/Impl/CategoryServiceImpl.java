package org.example.apituan7.Service.Impl;

import org.example.apituan7.Entity.Category;
import org.example.apituan7.Repository.CategoryRepository;
import org.example.apituan7.Service.ICategoryService;
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
