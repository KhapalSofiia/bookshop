package com.bookshop.service;

import com.bookshop.dto.CategoryDto;
import com.bookshop.dto.CreateCategoryDto;
import java.util.List;

public interface CategoryService {
    List<CategoryDto> findAll();

    CategoryDto getById(Long id);

    CategoryDto save(CreateCategoryDto categoryDto);

    CategoryDto update(Long id, CreateCategoryDto categoryDto);

    void deleteById(Long id);
}
