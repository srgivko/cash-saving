package by.sivko.cashsaving.services;

import by.sivko.cashsaving.dao.CategoryDao;
import by.sivko.cashsaving.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao;

    @Autowired
    public CategoryServiceImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public Category addCategory(Category category) {
        return this.categoryDao.add(category);
    }

    @Override
    public Category removeCategory(Category category) {
        return this.categoryDao.delete(category);
    }

    @Override
    public Category updateCategory(Category category) {
        return this.categoryDao.edit(category);
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return this.categoryDao.getById(id);
    }
}
