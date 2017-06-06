package com.example.bookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookListingDto {
    private List<BookDto> books;
    private int count;

    public BookListingDto(
            @JsonProperty("books") List<BookDto> books,
            @JsonProperty("count") int count) {
        this.books = books;
        this.count = count;
    }

    public List<BookDto> getBooks() {
        return books;
    }

    public int getCount() {
        return count;
    }

    public static BookListingDto toDto(BookListing bookListing) {
        return new BookListingDto(
                bookListing.getBooks()
                        .stream()
                        .map(BookDto::toDto)
                        .collect(Collectors.toList()),
                bookListing.getCount());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookListingDto that = (BookListingDto) o;
        return count == that.count &&
                Objects.equals(books, that.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(books, count);
    }

    @Override
    public String toString() {
        return "BookListingDto{" +
                "books=" + books +
                ", count=" + count +
                '}';
    }
}
