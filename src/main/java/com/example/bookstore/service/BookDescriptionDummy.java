package com.example.bookstore.service;

import org.springframework.stereotype.Component;

@Component
public class BookDescriptionDummy implements BookDescriptionClient {
    @Override
    public String getDescription(String bookId) {
        return "book description";
    }
}
