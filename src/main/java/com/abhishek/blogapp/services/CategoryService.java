package com.abhishek.blogapp.services;


import com.abhishek.blogapp.payloads.CategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoryService {

    // create
    CategoryDto createCategory(CategoryDto categoryDto);

    // update
    CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

    // delete
    void deleteCategory(Integer categoryId);

    // get single category
    CategoryDto getCategory(Integer categoryId);

    // get all categories
    List<CategoryDto> getAllCategory();

}
