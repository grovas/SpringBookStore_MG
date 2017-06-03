package com.example.bookstore.endpoint;

import com.example.bookstore.exception.BookNotFoundException;
import com.example.bookstore.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BookStoreErrorHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BookNotFoundException.class)
    public ErrorResponse handleBookNotFoundException(BookNotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }
}
