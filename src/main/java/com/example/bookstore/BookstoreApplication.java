package com.example.bookstore;

import com.example.bookstore.category.Category;
import com.example.bookstore.category.CategoryManager;
import com.example.bookstore.category.CategoryManagerRepository;
import com.example.bookstore.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookstoreApplication implements CommandLineRunner {

	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private CategoryManagerRepository categoryManagerRepository;

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		categoryManagerRepository.save(new CategoryManager("10", "manager_name"));
		Category savedCategory = categoryRepository.save(
				new Category("category_name",
						new CategoryManager("10")));
		Category readCategory = categoryRepository.findOne(savedCategory.getId());

		System.out.println("MANAGER NAME: " + readCategory.getCategoryManager().getName());
	}
}
