package com.example.bookstore.service;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.BookListing;
import com.example.bookstore.repository.BookRepository;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceSpringTest {

    @Autowired
    BookRepository bookRepository;
    @Autowired
    BookService bookService;

    @Before
    public void setUp() {
        bookRepository.deleteAll();
    }

    @Test
    public void getListingData() {
        // given
        Book book1 = bookRepository.save(new Book("ksiazka 1", "autor 1"));
        Book book2 = bookRepository.save(new Book("ksiazka 2", "autor 2"));

        // when
        BookListing bookListing = bookService.getListingData();

        // then
        assertEquals(
                new BookListing(
                        Lists.newArrayList(
                                new Book(book1.getId(), "ksiazka 1", "autor 1"),
                                new Book(book2.getId(), "ksiazka 2", "autor 2")
                        ), 2),
                bookListing
        );
    }

}