package by.sivko.cashsaving.dao;

import by.sivko.cashsaving.models.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryDaoImpl extends GenericDaoImpl<Category, Long> implements CategoryDao {

    @Override
    public List<Category> getAllCategories(Long userId) {
        return this.entityManager.createNamedQuery("Category.getAllCategoriesByUserId", Category.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
