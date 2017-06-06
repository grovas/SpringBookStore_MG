package com.example.bookstore.service;

import com.example.bookstore.exception.BookNotFoundException;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.BookDetails;
import com.example.bookstore.model.BookListing;
import com.example.bookstore.repository.BookRepository;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BookServiceTest {

    BookRepository bookRepository;
    BookService bookService;
    BookDescriptionClient descriptionClient;

    @Before
    public void setUp() {
        bookRepository = mock(BookRepository.class);
        descriptionClient = mock(BookDescriptionClient.class);
        bookService = new BookService(bookRepository, descriptionClient);
    }

    @Test
    public void getListingData() {
        // given
        when(bookRepository.findAll()).thenReturn(
                Lists.newArrayList(
                        new Book("1","title1", "autor1"),
                        new Book("2","title2", "autor2")
                )
        );

        // when
        BookListing bookListing = bookService.getListingData();

        // then
        assertEquals(
                new BookListing(
                        Lists.newArrayList(
                                new Book("1","title1", "autor1"),
                                new Book("2", "title2", "autor2")
                        ),
                        2
                ),
                bookListing);
        verify(bookRepository).findAll();
    }

    @Test
    public void shouldReturnEmptyListingWhenNoBooks() {
        // given
        when(bookRepository.findAll()).thenReturn(
                Lists.newArrayList()
        );

        // when
        BookListing bookListing = bookService.getListingData();

        // then
        assertEquals(
                new BookListing(
                        Lists.newArrayList(),
                        0
                ),
                bookListing);
        verify(bookRepository).findAll();
    }


    @Test
    public void shouldGetBookDetailsWhenBookExists() {
        // given
        when(bookRepository.findById("id1")).thenReturn(
            Optional.of(new Book("id1", "test title", "test author"))
        );
        when(descriptionClient.getDescription("id1")).thenReturn(
                "some example description"
        );

        // when
        BookDetails bookDetails = bookService.getBookDetailsById("id1");

        // then
        assertEquals(
                new BookDetails("test title",
                        "test author",
                        "some example description")
                , bookDetails);
        verify(bookRepository).findById("id1");
        verify(descriptionClient).getDescription("id1");
    }

    @Test(expected = BookNotFoundException.class)
    public void shouldThrowExceptionWhenNoBookFound() {
        // given
        when(bookRepository.findById("non-existing-book"))
                .thenReturn(Optional.empty());
        // when
        BookDetails bookDetails = bookService.getBookDetailsById("non-existing-book");
    }

}