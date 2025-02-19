package org.example.lab2_practice_api.services;

import org.example.lab2_practice_api.entities.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> getAllCategories();
}
