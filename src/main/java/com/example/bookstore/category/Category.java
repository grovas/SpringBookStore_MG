package com.example.bookstore.category;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class Category {
    @Id
    private String id;
    private String name;
    @Field("manager")
    @DBRef
    private CategoryManager categoryManager;

    public Category() {
    }

    public Category(String name, CategoryManager categoryManager) {
        this.name = name;
        this.categoryManager = categoryManager;
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

    public CategoryManager getCategoryManager() {
        return categoryManager;
    }

    public void setCategoryManager(CategoryManager categoryManager) {
        this.categoryManager = categoryManager;
    }
}
