package com.bookshop.service.impl;

import com.bookshop.dto.CategoryDto;
import com.bookshop.dto.CreateCategoryDto;
import com.bookshop.exception.EntityNotFoundException;
import com.bookshop.mapper.CategoryMapper;
import com.bookshop.model.Category;
import com.bookshop.repository.CategoryRepository;
import com.bookshop.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Page<CategoryDto> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(categoryMapper::toCategoryDto);
    }

    @Override
    public CategoryDto getById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category with id"
                        + id + "not found."));

        return categoryMapper.toCategoryDto(category);
    }

    @Override
    public CategoryDto save(CreateCategoryDto categoryDto) {
        Category category = categoryMapper.toEntity(categoryDto);

        return categoryMapper.toCategoryDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto update(Long id, CreateCategoryDto categoryDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category with id "
                        + id + " not found."));

        categoryMapper.createCategoryFromDto(categoryDto, category);

        return categoryMapper.toCategoryDto(categoryRepository.save(category));
    }

    @Override
    public void deleteById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Category with id: " + id
                    + " not found");
        }
        categoryRepository.deleteById(id);
    }
}
