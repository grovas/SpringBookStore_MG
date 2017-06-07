package com.example.bookstore.endpoint;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.BookDetailsDto;
import com.example.bookstore.model.BookDto;
import com.example.bookstore.model.BookListingDto;
import com.example.bookstore.repository.BookRepository;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class BookStoreEndpointSpringTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8199);

    @Autowired
    BookRepository bookRepository;

    TestRestTemplate restTemplate;

    @Before
    public void setUp() {
        bookRepository.deleteAll();
        restTemplate = new TestRestTemplate();
    }

    @Test
    public void shouldGetBookListing() {
        // given
        bookRepository.save(new Book("test title 1", "test author 1"));
        bookRepository.save(new Book("test title 2", "test author 2"));

        // when
        ResponseEntity<BookListingDto> bookListingEntity =
                restTemplate.getForEntity("http://localhost:8190/api/books",
                        BookListingDto.class);
        // then
        assertEquals(HttpStatus.OK,
                bookListingEntity.getStatusCode());
        assertEquals(new BookListingDto(
                        Lists.newArrayList(
                                new BookDto("test title 1", "test author 1"),
                                new BookDto("test title 2", "test author 2")
                        ), 2
                ),
                bookListingEntity.getBody());
    }

    @Test
    public void shouldAddBook() {
        // given
        bookRepository.save(new Book("title 1", "author 1"));

        // when
        ResponseEntity<BookDto> responseEntity = restTemplate.postForEntity("http://localhost:8190/api/books",
                new BookDto("title 2", "author 2"),
                BookDto.class);

        // then
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(new BookDto("title 2", "author 2"),
                responseEntity.getBody());
        assertEquals(2, bookRepository.count());
    }

    @Test
    public void shouldDeleteBook() {
        // given
        Book bookToDelete = bookRepository.save(
                new Book("title 1", "author 1"));
        bookRepository.save(new Book("title 2", "author 2"));

        // when
        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                "http://localhost:8190/api/books/" + bookToDelete.getId(),
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Void.class
        );

        // then
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertEquals(1, bookRepository.count());
        assertTrue(bookRepository.findUsingSomeCustomQuery("title 2").isPresent());
    }

    @Test
    public void shouldGetBookDetails() {
        // given
        Book book = bookRepository.save(new Book("title 1", "author 1"));
        stubFor(get(urlEqualTo("/api/plaintext"))
        .willReturn(aResponse()
                .withStatus(HttpStatus.OK.value())
                .withBody("loripsum description")));

        // when
        ResponseEntity<BookDetailsDto> responseEntity =
                restTemplate.getForEntity(
                        "http://localhost:8190/api/books/" + book.getId(),
                BookDetailsDto.class);

        // then
        verify(1, getRequestedFor(urlEqualTo("/api/plaintext")));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(
                new BookDetailsDto("title 1", "author 1", "loripsum description"),
                responseEntity.getBody());
    }

    @Test
    public void shouldGet404WhenAskingForNonExistingBookDetails() {
        // given

        // when
        ResponseEntity<BookDetailsDto> responseEntity = restTemplate.getForEntity(
                "http://localhost:8190/api/books/not-existing-book",
                BookDetailsDto.class);

        // then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(0, getRequestedFor(urlEqualTo("/api/plaintext")));
    }
}
