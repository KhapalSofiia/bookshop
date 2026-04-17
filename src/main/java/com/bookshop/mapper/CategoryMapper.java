package com.bookshop.mapper;

import com.bookshop.config.MapperConfig;
import com.bookshop.dto.CategoryDto;
import com.bookshop.dto.CreateBookRequestDto;
import com.bookshop.dto.CreateCategoryDto;
import com.bookshop.model.Book;
import com.bookshop.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryDto toCategoryDto(Category category);

    Category toEntity(CategoryDto categoryDto);

    Category toEntity(CreateCategoryDto createCategoryDto);

    void createCategoryFromDto(CreateCategoryDto updateBookRequestDto,
                           @MappingTarget Category category);
}
