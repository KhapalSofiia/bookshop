package com.bookshop.service.impl;

import com.bookshop.dto.BookDto;
import com.bookshop.dto.CreateBookRequestDto;
import com.bookshop.dto.UpdateBookRequestDto;
import com.bookshop.exception.EntityNotFoundException;
import com.bookshop.mapper.BookMapper;
import com.bookshop.model.Book;
import com.bookshop.repository.BookRepository;
import com.bookshop.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        return bookMapper
                .toBookDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toBookDto)
                .toList();
    }

    @Override
    public BookDto getBookById(Long id) {
        return bookRepository.getBookById(id).stream()
                .map(bookMapper::toBookDto)
                .findAny().orElseThrow(() -> new EntityNotFoundException("Book with id: "
                        + id + " not found"));
    }

    @Override
    public BookDto updateBook(@PathVariable Long id,
                              @RequestBody UpdateBookRequestDto request) {
        Book book = bookRepository.getBookById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with id: "
                + id + " not found"));

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setPrice(request.getPrice());
        book.setIsbn(request.getIsbn());
        book.setDescription(request.getDescription());
        book.setCoverImage(request.getCoverImage());

        return bookMapper.toBookDto(bookRepository.save(book));
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.getBookById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with id: "
                        + id + " not found"));

        bookRepository.deleteBookById(id);
    }
}
