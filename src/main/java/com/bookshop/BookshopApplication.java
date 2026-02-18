package com.bookshop;

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

    @Bean
    public CommandLineRunner demo(BookService service) {
        return args -> {
            Book book = new Book();
            book.setTitle("It");
            book.setAuthor("Stephen King");
            book.setIsbn("1111");
            book.setPrice(new BigDecimal("200"));
            service.save(book);
            System.out.println(book);

            List<Book> books = service.findAll();
            System.out.println(books);
        };
    }
}
