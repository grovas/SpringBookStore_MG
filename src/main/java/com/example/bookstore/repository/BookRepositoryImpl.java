package com.example.bookstore.repository;

import com.example.bookstore.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.List;

import org.springframework.data.mongodb.core.query.*;

public class BookRepositoryImpl implements BookRepositoryCustom {

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public List<Book> searchSpringBooks(int limit) {
        Query query = new Query();
        query.limit(limit);
        query.addCriteria(Criteria.where("title").regex("/Spring/"));
        return mongoOperations.find(query, Book.class);
    }
}
