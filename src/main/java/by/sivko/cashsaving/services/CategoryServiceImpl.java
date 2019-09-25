package by.sivko.cashsaving.services;

import by.sivko.cashsaving.annotations.Logging;
import by.sivko.cashsaving.dao.CategoryDao;
import by.sivko.cashsaving.exceptions.NotFoundEntityException;
import by.sivko.cashsaving.models.Category;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Logging
    private Logger logger;

    private final CategoryDao categoryDao;

    @Autowired
    public CategoryServiceImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public Category addCategory(Category category) {
        category = this.categoryDao.add(category);
        logger.info(String.format("Add category with %d", category.getId()));
        return category;
    }

    @Override
    public Category removeCategory(Category category) {
        category = this.categoryDao.delete(category);
        logger.info(String.format("Delete category with %d", category.getId()));
        return category;
    }

    @Override
    public Category updateCategory(Category category) {
        category = this.categoryDao.edit(category);
        logger.info(String.format("Update category with %d", category.getId()));
        return category;
    }

    @Override
    public Category getCategoryById(Long id) throws NotFoundEntityException {
        Category category = this.categoryDao.getById(id).orElseThrow(() -> new NotFoundEntityException(id));
        logger.info(String.format("Get category with %d", id));
        return category;
    }

    @Override
    public Category removeCategoryById(Long id) throws NotFoundEntityException {
        Category category = this.categoryDao.getById(id).orElseThrow(() -> new NotFoundEntityException(id));
        this.categoryDao.delete(category);
        logger.info(String.format("Delete category with %d", id));
        return category;
    }

    @Override
    public List<Category> getAllCategoriesByUserId(Long userId) {
        List<Category> categoryList = this.categoryDao.getAllCategories(userId);
        logger.info(String.format("Get all categories by userId=[%d]", userId));
        return categoryList;
    }

    @Override
    public List<Category> getAllCategoriesByUserIdAndPartOfName(Long userId, String pathOfName) {
        List<Category> categoryList = this.getAllCategoriesByUserId(userId);
        if(!categoryList.isEmpty())
            categoryList = categoryList.stream().filter(category -> category.getName().toLowerCase().contains(pathOfName.toLowerCase())).collect(Collectors.toList());
        logger.info(String.format("Get all categories by userId=[%d] and pathOfName=[%s]", userId, pathOfName));
        return categoryList;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}
