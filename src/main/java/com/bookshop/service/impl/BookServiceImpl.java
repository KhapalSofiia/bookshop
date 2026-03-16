package com.bookshop.service.impl;

import com.bookshop.dto.BookDto;
import com.bookshop.dto.CreateBookRequestDto;
import com.bookshop.exception.EntityNotFoundException;
import com.bookshop.mapper.BookMapper;
import com.bookshop.model.Book;
import com.bookshop.repository.BookRepository;
import com.bookshop.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public Page<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(bookMapper::toBookDto);
    }

    @Override
    public BookDto getBookById(Long id) {
        return bookRepository.findById(id).stream()
                .map(bookMapper::toBookDto)
                .findAny().orElseThrow(() -> new EntityNotFoundException("Book with id: "
                        + id + " not found"));
    }

    @Override
    public BookDto updateBook(Long id, CreateBookRequestDto request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with id: "
                + id + " not found"));

        bookMapper.updateBookFromDto(request, book);

        return bookMapper.toBookDto(bookRepository.save(book));
    }

    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Book with id: " + id
                    + " not found");
        }
        bookRepository.deleteById(id);
    }
}
