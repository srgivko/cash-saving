package by.sivko.cashsaving.services;

import by.sivko.cashsaving.exceptions.NotFoundEntityException;
import by.sivko.cashsaving.models.Category;
import by.sivko.cashsaving.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    @Override
    public Category addCategory(Category category) {
        return this.categoryRepository.save(category);
    }

    @Transactional
    @Override
    public void removeCategory(Category category) {
        this.categoryRepository.delete(category);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Category> getCategoryById(Long id) {
        return this.categoryRepository.findById(id);
    }

    @Transactional
    @Override
    public void removeCategoryById(Long id) throws NotFoundEntityException {
        this.categoryRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<Category> getAllCategoriesByUserId(Long id) {
        return this.categoryRepository.findAllByUserId(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<Category> getAllCategoriesByUserUsername(String username) {
        return this.categoryRepository.findAllByUserUsername(username);
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<Category> getAllCategoriesByNameLike(String name) {
        return this.categoryRepository.findAllByNameLike(name);
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<Category> getAllCategoriesBetweenDates(Date start, Date end) {
        return this.categoryRepository.findAllByCreateAtBetween(start, end);
    }

}
