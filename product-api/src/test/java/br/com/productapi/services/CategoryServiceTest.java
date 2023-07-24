package br.com.productapi.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.productapi.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.productapi.exceptions.EmptyStringException;
import br.com.productapi.exceptions.ValidationException;
import br.com.productapi.models.dtos.requests.CategoryRequest;
import br.com.productapi.models.dtos.responses.CategoryResponse;
import br.com.productapi.models.dtos.responses.SuccessResponse;
import br.com.productapi.models.entities.Category;
import br.com.productapi.repositories.CategoryRepository;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductService productService;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveCategory() {
        CategoryRequest request = new CategoryRequest();
        request.setDescription("Test Category");

        Category categoryFromRepository = new Category();
        categoryFromRepository.setId(1);
        categoryFromRepository.setDescription("Test Category");

        when(categoryRepository.save(any(Category.class))).thenReturn(categoryFromRepository);

        CategoryResponse result = categoryService.save(request);

        assertNotNull(result);
        assertEquals(categoryFromRepository.getId(), result.getId());
        assertEquals(categoryFromRepository.getDescription(), result.getDescription());
    }

    @Test
    public void testSaveCategoryWithEmptyDescription() {
        CategoryRequest request = new CategoryRequest();
        request.setDescription(""); // Empty description

        assertThrows(EmptyStringException.class, () -> categoryService.save(request));
    }

    @Test
    public void testFindById() {
        int categoryId = 1;
        Category categoryFromRepository = new Category();
        categoryFromRepository.setId(categoryId);
        categoryFromRepository.setDescription("Test Category");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(categoryFromRepository));

        Category result = categoryService.findById(categoryId);

        assertNotNull(result);
        assertEquals(categoryId, result.getId());
        assertEquals("Test Category", result.getDescription());
    }

    @Test
    public void testFindByIdNotFound() {
        int categoryId = 1;

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(ValidationException.class, () -> categoryService.findById(categoryId));
    }

    @Test
    public void testFindByDescription() {
        String description = "Test";
        List<Category> categoriesFromRepository = new ArrayList<>();
        categoriesFromRepository.add(new Category(1, "Test Category 1"));
        categoriesFromRepository.add(new Category(2, "Test Category 2"));
        categoriesFromRepository.add(new Category(3, "Test Category 3"));

        when(categoryRepository.findByDescriptionIgnoreCaseContaining(description)).thenReturn(categoriesFromRepository);

        List<CategoryResponse> result = categoryService.findByDescription(description);

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("Test Category 1", result.get(0).getDescription());
        assertEquals("Test Category 2", result.get(1).getDescription());
        assertEquals("Test Category 3", result.get(2).getDescription());
    }

    @Test
    public void testFindByDescriptionEmpty() {
        String description = "";

        assertThrows(EmptyStringException.class, () -> categoryService.findByDescription(description));
    }

    @Test
    public void testFindByAll() {
        List<Category> categoriesFromRepository = new ArrayList<>();
        categoriesFromRepository.add(new Category(1, "Test Category 1"));
        categoriesFromRepository.add(new Category(2, "Another Test Category"));
        categoriesFromRepository.add(new Category(3, "Test Category 3"));

        when(categoryRepository.findAll()).thenReturn(categoriesFromRepository);

        List<CategoryResponse> result = categoryService.findByAll();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("Test Category 1", result.get(0).getDescription());
        assertEquals("Test Category 3", result.get(2).getDescription());
    }

    @Test
    public void testFindByIdResponse() {
        int categoryId = 1;
        Category categoryFromRepository = new Category();
        categoryFromRepository.setId(categoryId);
        categoryFromRepository.setDescription("Test Category");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(categoryFromRepository));

        CategoryResponse result = categoryService.findByIdResponse(categoryId);

        assertNotNull(result);
        assertEquals(categoryId, result.getId());
        assertEquals("Test Category", result.getDescription());
    }

    @Test
    public void testDeleteCategory() {
        int categoryId = 1;

        when(productService.existsByCategoryId(categoryId)).thenReturn(false);
        when(categoryRepository.existsById(categoryId)).thenReturn(true);

        SuccessResponse result = categoryService.deleteCategory(categoryId);

        assertNotNull(result);
        assertEquals("Category successfully deleted.", result.getMessage());
        verify(categoryRepository, times(1)).deleteById(categoryId);
    }

    @Test
    public void testDeleteCategoryAssociatedWithProducts() {
        int categoryId = 1;

        when(productService.existsByCategoryId(categoryId)).thenReturn(true);

        assertThrows(NotFoundException.class, () -> categoryService.deleteCategory(categoryId));
    }

    @Test
    public void testUpdateById() {
        int categoryId = 1;

        CategoryRequest request = new CategoryRequest();
        request.setDescription("Updated Category");

        Category categoryFromRepository = new Category();
        categoryFromRepository.setId(categoryId);
        categoryFromRepository.setDescription("Test Category");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(categoryFromRepository));
        when(categoryRepository.save(any(Category.class))).thenReturn(categoryFromRepository);
        when(categoryRepository.existsById(categoryId)).thenReturn(true);

        CategoryResponse result = categoryService.updateById(request, categoryId);

        assertNotNull(result);
        assertEquals(categoryId, result.getId());
        assertEquals("Updated Category", result.getDescription());
    }

    @Test
    public void testUpdateByIdNotFound() {
        int categoryId = 1;

        CategoryRequest request = new CategoryRequest();
        request.setDescription("Updated Category");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> categoryService.updateById(request, categoryId));
    }

    @Test
    public void testUpdateByIdWithEmptyDescription() {
        int categoryId = 1;

        CategoryRequest request = new CategoryRequest();
        request.setDescription(""); // Empty description

        assertThrows(NotFoundException.class, () -> categoryService.updateById(request, categoryId));
    }

}