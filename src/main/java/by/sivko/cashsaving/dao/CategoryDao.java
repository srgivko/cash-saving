package by.sivko.cashsaving.dao;

import by.sivko.cashsaving.models.Category;

import java.util.List;

public interface CategoryDao extends GenericDao<Category, Long> {
    List<Category> findByPartOfName(String partOfCategoryName, Long userId);
}
