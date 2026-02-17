package com.bookshop;

import com.bookshop.model.Book;
import com.bookshop.repository.BookRepository;
import java.math.BigDecimal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookshopApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookshopApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(BookRepository repository) {
        return args -> {
            Book book = new Book();
            book.setTitle("It");
            book.setAuthor("Stephen King");
            book.setIsbn("1111");
            book.setPrice(new BigDecimal("200"));
            repository.save(book);

            System.out.println("The book successfully added to db");
        };
    }
}
