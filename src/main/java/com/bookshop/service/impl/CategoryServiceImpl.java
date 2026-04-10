package com.bookshop.service.impl;

import com.bookshop.dto.CategoryDto;
import com.bookshop.dto.CreateCategoryDto;
import com.bookshop.exception.EntityNotFoundException;
import com.bookshop.mapper.CategoryMapper;
import com.bookshop.model.Category;
import com.bookshop.repository.CategoryRepository;
import com.bookshop.service.CategoryService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toCategoryDto)
                .toList();
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
    public CategoryDto update(Long id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found."));
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

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
