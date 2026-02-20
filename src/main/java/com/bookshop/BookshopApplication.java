package com.bookshop;

import com.bookshop.controller.BookController;
import com.bookshop.dto.BookDto;
import com.bookshop.dto.CreateBookRequestDto;
import com.bookshop.model.Book;
import com.bookshop.service.BookService;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookshopApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookshopApplication.class, args);
    }
}
