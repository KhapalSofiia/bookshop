package com.bookshop.service.impl;

import com.bookshop.dto.BookDto;
import com.bookshop.dto.CreateBookRequestDto;
import com.bookshop.exception.EntityNotFoundException;
import com.bookshop.mapper.BookMapper;
import com.bookshop.model.Book;
import com.bookshop.model.Category;
import com.bookshop.repository.BookRepository;
import com.bookshop.repository.CategoryRepository;
import com.bookshop.service.BookService;
import jakarta.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        Set<Category> categories = new HashSet<>(categoryRepository
                .findAllById(requestDto.getCategoryIds()));

        book.setCategories(categories);
        return bookMapper
                .toBookDto(bookRepository.save(book));
    }

    @Transactional
    @Override
    public Page<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(bookMapper::toBookDto);
    }

    @Transactional
    @Override
    public BookDto getBookById(Long id) {
        return bookRepository.findById(id).stream()
                .map(bookMapper::toBookDto)
                .findAny().orElseThrow(() -> new EntityNotFoundException("Book with id: "
                        + id + " not found"));
    }

    @Transactional
    @Override
    public BookDto updateBook(Long id, CreateBookRequestDto request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with id: "
                + id + " not found"));

        bookMapper.createBookFromDto(request, book);

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

    @Transactional
    @Override
    public List<BookDto> getBooksByCategoryId(Long id) {
        return bookRepository.findAllByCategories_Id(id)
                .stream()
                .map(bookMapper::toBookDto)
                .toList();
    }
}
