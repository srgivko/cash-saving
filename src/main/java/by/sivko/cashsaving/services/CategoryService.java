package by.sivko.cashsaving.services;

import by.sivko.cashsaving.models.Category;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

public interface CategoryService {
    Category addCategory(Category category, String username);

    Optional<Category> getCategoryById(Long id);

    void removeCategoryById(Long id);

    Collection<Category> getAllCategoriesByUserUsername(String username);

    Collection<Category> getAllCategoriesByNameLike(String name);

    Collection<Category> getAllCategoriesBetweenDates(Date start, Date end);
}
