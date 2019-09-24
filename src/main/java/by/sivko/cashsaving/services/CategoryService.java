package by.sivko.cashsaving.services;

import by.sivko.cashsaving.exceptions.NotFoundEntityException;
import by.sivko.cashsaving.models.Category;

import java.util.List;

public interface CategoryService {
    Category addCategory(Category category);

    Category removeCategory(Category category);

    Category updateCategory(Category category);

    Category getCategoryById(Long id) throws NotFoundEntityException;

    Category removeCategoryById(Long id) throws NotFoundEntityException;

    List<Category> getAllCategoriesByUserId(Long userId);

    List<Category> getAllCategoriesByUserIdAndPartOfName(Long userId, String pathOfName);
}
