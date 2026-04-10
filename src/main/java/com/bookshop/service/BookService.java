package com.bookshop.service;

import com.bookshop.dto.BookDto;
import com.bookshop.dto.CreateBookRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    Page<BookDto> findAll(Pageable pageable);

    BookDto getBookById(Long id);

    BookDto updateBook(Long id, CreateBookRequestDto request);

    void deleteBook(Long id);

    List<BookDto> getBooksByCategoryId(Long id);
}
