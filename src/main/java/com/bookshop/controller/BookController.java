package com.bookshop.controller;

import com.bookshop.dto.BookDto;
import com.bookshop.dto.CreateBookRequestDto;
import com.bookshop.dto.UpdateBookRequestDto;
import java.util.List;

public interface BookController {
    List<BookDto> findAll();

    BookDto getBookById(Long id);

    BookDto createBook(CreateBookRequestDto bookDto);

    BookDto updateBook(Long id, UpdateBookRequestDto updateBookRequestDto);

    void deleteBook(Long id);
}
