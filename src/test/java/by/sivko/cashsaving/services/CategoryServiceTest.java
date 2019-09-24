package by.sivko.cashsaving.services;

import by.sivko.cashsaving.dao.CategoryDao;
import by.sivko.cashsaving.exceptions.NotFoundEntityException;
import by.sivko.cashsaving.models.Category;
import by.sivko.cashsaving.models.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CategoryServiceTest extends AbstractServiceAnnotationInclude {

    private static final String NAME_CATEGORY = "PART_OF_NAME_CATEGORY";
    private static final String PART_OF_NAME_CATEGORY = NAME_CATEGORY.substring(3);
    private static final String NOT_EXIST_PART_OF_NAME_CATEGORY = "NOT_EXIST_PART_OF_NAME_CATEGORY";

    private static Logger logger = LoggerFactory.getLogger(EventServiceTest.class);
    private static final Category CATEGORY = new Category();

    @Mock
    CategoryDao categoryDao;

    @InjectMocks
    CategoryServiceImpl categoryService;

    @Before
    public void setUp() {
        CATEGORY.setId(0L);
        CATEGORY.setName(NAME_CATEGORY);
        CATEGORY.setUser(new User(1L, "USER", "USER", "USER", Collections.singletonList(CATEGORY), null));
        CATEGORY.setCapacity(BigDecimal.ZERO);
        CATEGORY.setDescription("CATEGORY");
        CATEGORY.setEventList(null);
        categoryService.setLogger(logger);
        when(this.categoryDao.getById(CATEGORY.getId())).thenReturn(Optional.of(CATEGORY));
        when(this.categoryDao.edit(CATEGORY)).thenReturn(CATEGORY);
        when(this.categoryDao.delete(CATEGORY)).thenReturn(CATEGORY);
        when(this.categoryDao.add(CATEGORY)).thenReturn(CATEGORY);
        when(this.categoryDao.getAllCategories(CATEGORY.getUser().getId())).thenReturn(Collections.singletonList(CATEGORY));
    }

    @Test
    public void addCategory() {
        Category category = this.categoryService.addCategory(CATEGORY);
        assertEquals(CATEGORY, category);
        verify(categoryDao, times(1)).add(CATEGORY);
    }

    @Test
    public void removeCategory() {
        Category category = this.categoryService.removeCategory(CATEGORY);
        assertEquals(CATEGORY, category);
        verify(categoryDao, times(1)).delete(CATEGORY);
    }

    @Test
    public void updateCategory() {
        Category category = this.categoryService.updateCategory(CATEGORY);
        assertEquals(CATEGORY, category);
        verify(categoryDao, times(1)).edit(CATEGORY);
    }

    @Test
    public void getCategoryById() throws NotFoundEntityException {
        Category category = this.categoryService.getCategoryById(CATEGORY.getId());
        assertEquals(CATEGORY, category);
        verify(categoryDao, times(1)).getById(CATEGORY.getId());
    }

    @Test
    public void removeCategoryById() throws NotFoundEntityException {
        Category category = this.categoryService.removeCategoryById(CATEGORY.getId());
        assertEquals(CATEGORY, category);
        verify(categoryDao, times(1)).getById(CATEGORY.getId());
        verify(categoryDao, times(1)).delete(CATEGORY);
    }

    @Test
    public void getAllCategoriesByUserId() {
        List<Category> categoryList = this.categoryService.getAllCategoriesByUserId(CATEGORY.getUser().getId());
        assertFalse(categoryList.isEmpty());
        assertEquals(CATEGORY, categoryList.get(0));
        verify(categoryDao, times(1)).getAllCategories(CATEGORY.getUser().getId());
    }

    @Test
    public void getAllCategoriesByUserIdAndPathOfName() {
        List<Category> categoryList = this.categoryService.getAllCategoriesByUserIdAndPartOfName(CATEGORY.getUser().getId(), PART_OF_NAME_CATEGORY);
        assertFalse(categoryList.isEmpty());
        assertEquals(CATEGORY, categoryList.get(0));
        verify(categoryDao, times(1)).getAllCategories(CATEGORY.getUser().getId());
    }

    @Test
    public void ifNotExistCategoriesWithPathOfName() {
        List<Category> categoryList = this.categoryService.getAllCategoriesByUserIdAndPartOfName(CATEGORY.getUser().getId(), NOT_EXIST_PART_OF_NAME_CATEGORY);
        assertTrue(categoryList.isEmpty());
        verify(categoryDao, times(1)).getAllCategories(CATEGORY.getUser().getId());
    }

}