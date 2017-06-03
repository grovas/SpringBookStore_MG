package com.example.bookstore.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("bookDescriptionLoripsum")
public class BookDescriptionLoripsum implements BookDescriptionClient {
    @Override
    public String getDescription(String bookId) {
        return "loripsum";
    }
}
