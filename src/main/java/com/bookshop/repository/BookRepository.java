package com.bookshop.repository;

import com.bookshop.model.Book;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByCategoryId(Long categoryId);
}
