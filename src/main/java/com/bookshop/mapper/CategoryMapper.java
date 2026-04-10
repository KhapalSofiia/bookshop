package com.bookshop.mapper;

import com.bookshop.config.MapperConfig;
import com.bookshop.dto.CategoryDto;
import com.bookshop.dto.CreateCategoryDto;
import com.bookshop.model.Category;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryDto toCategoryDto(Category category);

    Category toEntity(CategoryDto categoryDTO);

    Category toEntity(CreateCategoryDto createCategoryDto);
}
