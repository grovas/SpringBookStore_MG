package com.example.bookstore.endpoint;

import com.example.bookstore.model.BookDetailsDto;
import com.example.bookstore.model.BookDto;
import com.example.bookstore.model.BookListing;
import com.example.bookstore.model.BookListingDto;
import com.example.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookStoreEndpoint {

    private final BookService bookService;

    @Autowired
    public BookStoreEndpoint(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "/hello", produces = MediaType.TEXT_PLAIN_VALUE)
    public String hello() {
        return "<h1>Hello WOrld!</h1><p>Ohohoho</p>";
    }

    @GetMapping("/book")
    public BookDto getBook() {
        return new BookDto("title", "author");
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BookListingDto getBooks() {
        return BookListingDto.toDto(bookService.getListingData());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public BookDto addBooks(@RequestBody BookDto bookDto) {
        return BookDto.toDto(bookService.addBook(bookDto.fromDto()));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteBookById(@PathVariable String id) {
        bookService.deleteBookById(id);
    }

    // np. "/api/books/12"
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BookDetailsDto getBookDetails(@PathVariable String id) {
        return BookDetailsDto.toDto(bookService.getBookDetailsById(id));
    }
}
