package com.example.bookstore.category;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "category_manager")
public class CategoryManager {
    @Id
    private String id;
    private String name;

    public CategoryManager() {
    }

    public CategoryManager(String id) {
        this.id = id;
    }

    public CategoryManager(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
