package by.sivko.cashsaving.services;

import by.sivko.cashsaving.models.Category;

import java.util.Optional;

public interface CategoryService {
    Category addCategory(Category category);

    Category removeCategory(Category category);

    Category updateCategory(Category category);

    Optional<Category> getCategoryById(Long id);
}
