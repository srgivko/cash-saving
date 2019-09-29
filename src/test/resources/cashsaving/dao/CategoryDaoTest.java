package by.sivko.cashsaving.dao;

import by.sivko.cashsaving.AbstractDatabaseAnnotationInclude;
import by.sivko.cashsaving.models.Category;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PersistenceException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class CategoryDaoTest extends AbstractDatabaseAnnotationInclude {

    private static final int CATEGORIES_SIZE = 6;
    private static Category EXIST_CATEGORY_1;
    private static final long EXIST_CATEGORY_ID_1 = 100000L;
    private static final long NOT_EXIST_CATEGORY_ID = 100500L;
    private static final Category NEW_CATEGORY = new Category();

    @Autowired
    CategoryDao categoryDao;

    @Before
    public void init() {
        NEW_CATEGORY.setId(null);
        NEW_CATEGORY.setDescription("new");
        NEW_CATEGORY.setCapacity(new BigDecimal(10));
        NEW_CATEGORY.setCreateAt(new Date());
        NEW_CATEGORY.setEventList(null);
        NEW_CATEGORY.setName("new");
        EXIST_CATEGORY_1 = this.categoryDao.getById(EXIST_CATEGORY_ID_1).orElseThrow(RuntimeException::new);
    }

    @Test
    public void categoryDaoNotNull() {
        assertNotNull(categoryDao);
    }

    @Test
    public void getExistsCategory() {
        Category category = this.categoryDao.getById(EXIST_CATEGORY_ID_1).orElseThrow(RuntimeException::new);
        assertEquals(EXIST_CATEGORY_1, category);
    }

    @Test
    public void getNotExistsCategory() {
        Optional<Category> category = this.categoryDao.getById(NOT_EXIST_CATEGORY_ID);
        assertEquals(Optional.empty(), category);
    }

    @Test
    public void getAllCategories() {
        assertEquals(this.categoryDao.getAll().size(), CATEGORIES_SIZE);
    }

    @Test
    public void getAllCategoriesById() {
        Category category = this.categoryDao.getById(EXIST_CATEGORY_ID_1).orElseThrow(RuntimeException::new);
        List<Category> allCategories = this.categoryDao.getAllCategories(category.getUser().getId());
        assertEquals(CATEGORIES_SIZE, allCategories.size());
    }

    @Test
    public void notNullTimestamp() {
        assertNotNull(EXIST_CATEGORY_1.getCreateAt());
    }

    @Test
    public void saveNewCategory() {
        assertEquals(this.categoryDao.getAll().size(), CATEGORIES_SIZE);
        assertNull(NEW_CATEGORY.getId());
        this.categoryDao.add(NEW_CATEGORY);
        assertNotNull(NEW_CATEGORY.getId());
        assertNull(NEW_CATEGORY.getUser());
        assertEquals(this.categoryDao.getAll().size(), CATEGORIES_SIZE + 1);
    }

    @Test
    public void deleteExistCategory() {
        assertEquals(this.categoryDao.getAll().size(), CATEGORIES_SIZE);
        this.categoryDao.delete(this.categoryDao.getById(EXIST_CATEGORY_ID_1).orElseThrow(RuntimeException::new));
        assertEquals(this.categoryDao.getAll().size(), CATEGORIES_SIZE - 1);
    }

    @Test
    public void deleteTransientCategory() {
        assertEquals(this.categoryDao.getAll().size(), CATEGORIES_SIZE);
        this.categoryDao.delete(NEW_CATEGORY);
        assertEquals(this.categoryDao.getAll().size(), CATEGORIES_SIZE);
    }

    @Test(expected = PersistenceException.class)
    public void insertNewCategoryWithNullName() {
        assertEquals(this.categoryDao.getAll().size(), CATEGORIES_SIZE);
        NEW_CATEGORY.setName(null);
        this.categoryDao.add(NEW_CATEGORY);
        assertEquals(this.categoryDao.getAll().size(), CATEGORIES_SIZE);
    }

    @Test
    public void insertNewCategoryWithEmptyName() {
        assertEquals(this.categoryDao.getAll().size(), CATEGORIES_SIZE);
        NEW_CATEGORY.setName("");
        this.categoryDao.add(NEW_CATEGORY);
        assertEquals(this.categoryDao.getAll().size(), CATEGORIES_SIZE + 1);
    }

    @Test
    public void editExistCategory() {
        assertEquals(this.categoryDao.getAll().size(), CATEGORIES_SIZE);
        Category category = this.categoryDao.getById(EXIST_CATEGORY_ID_1).orElseThrow(RuntimeException::new);
        String nameRemainder = EXIST_CATEGORY_1.getName();
        assertEquals(category.getName(), nameRemainder);
        category.setName("new name");
        this.categoryDao.edit(category);
        category = this.categoryDao.getById(EXIST_CATEGORY_ID_1).orElseThrow(RuntimeException::new);
        assertNotEquals(category.getName(), nameRemainder);
        assertEquals(this.categoryDao.getAll().size(), CATEGORIES_SIZE);
    }

    @Test
    public void editTransientCategory() {
        assertEquals(this.categoryDao.getAll().size(), CATEGORIES_SIZE);
        this.categoryDao.edit(NEW_CATEGORY);
        assertEquals(this.categoryDao.getAll().size(), CATEGORIES_SIZE + 1);
    }
}

