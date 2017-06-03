package com.example.bookstore.service;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.BookListing;
import com.example.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookListing getListingData() {
        List<Book> books = bookRepository.findAll();

        return new BookListing(
                books,
                books.size()
        );
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteBookById(String id) {
        Optional<Book> book = bookRepository.findById(id);
        book.ifPresent(bookRepository::delete);
    }
}
