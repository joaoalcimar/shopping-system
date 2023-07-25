package br.com.productapi.models.entities;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import br.com.productapi.models.dtos.requests.CategoryRequest;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class CategoryTest {

    @Test
    public void testConstructorWithFields() {
        Category category = new Category(1, "Test Category");

        assertEquals(1, category.getId().intValue());
        assertEquals("Test Category", category.getDescription());
    }

    @Test
    public void testGetAndSetId() {
        Category category = new Category();
        category.setId(1);

        assertEquals(1, category.getId().intValue());
    }

    @Test
    public void testGetAndSetDescription() {
        Category category = new Category();
        category.setDescription("Test Category");

        assertEquals("Test Category", category.getDescription());
    }

    @Test
    public void testOf() {
        CategoryRequest request = new CategoryRequest();
        request.setDescription("Test Category");

        Category category = Category.of(request);

        assertNull(category.getId());
        assertEquals("Test Category", category.getDescription());
    }

    @Test
    public void testEquals() {
        Category category1 = new Category(1, "Test Category");
        Category category2 = new Category(1, "Test Category");
        Category category3 = new Category(2, "Test Category");
        Category category4 = new Category(1, "Different Category");

        assertEquals(category1, category2);
        assertNotEquals(category1, category3);
        assertNotEquals(category1, category4);
    }

    @Test
    public void testHashCode() {
        Category category1 = new Category(1, "Test Category");
        Category category2 = new Category(1, "Test Category");
        Category category3 = new Category(2, "Test Category");
        Category category4 = new Category(1, "Different Category");

        assertEquals(category1.hashCode(), category2.hashCode());
        assertNotEquals(category1.hashCode(), category3.hashCode());
        assertNotEquals(category1.hashCode(), category4.hashCode());
    }

    @Test
    public void testToString() {
        Category category = new Category(1, "Test Category");
        String expectedString = "Category(id=1, description=Test Category)";

        assertEquals(expectedString, category.toString());
    }
}