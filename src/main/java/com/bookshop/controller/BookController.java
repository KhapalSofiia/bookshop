package com.bookshop.controller;

import com.bookshop.dto.BookDto;
import com.bookshop.dto.CreateBookRequestDto;
import java.util.List;

public interface BookController {
    List<BookDto> findAll();

    BookDto getBookById(Long id);

    BookDto createBook(CreateBookRequestDto bookDto);
}
