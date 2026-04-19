package com.bookshop.mapper;

import com.bookshop.config.MapperConfig;
import com.bookshop.dto.BookDto;
import com.bookshop.dto.BookDtoWithoutCategoryIds;
import com.bookshop.dto.CreateBookRequestDto;
import com.bookshop.model.Book;
import com.bookshop.model.Category;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    @Mapping(target = "categoryIds", ignore = true)
    BookDto toBookDto(Book book);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        List<Long> categoriesIds = book.getCategories()
                .stream()
                .map(Category::getId)
                .toList();
        bookDto.setCategoryIds(categoriesIds);
    }

    @Mapping(target = "categories", ignore = true)
    Book toModel(CreateBookRequestDto createBookRequestDto);

    @AfterMapping
    default void setCategories(@MappingTarget Book book, BookDto bookDto) {
        Set<Category> categories = bookDto.getCategoryIds()
                .stream()
                .map(Category::new)
                .collect(Collectors.toSet());
        book.setCategories(categories);
    }

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    void createBookFromDto(CreateBookRequestDto updateBookRequestDto,
                           @MappingTarget Book book);
}
