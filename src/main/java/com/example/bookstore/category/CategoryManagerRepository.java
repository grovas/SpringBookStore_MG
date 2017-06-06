package com.example.bookstore.category;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryManagerRepository extends
        MongoRepository<CategoryManager, String> {
}
