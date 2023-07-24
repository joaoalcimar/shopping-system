package br.com.productapi.models.dtos.responses;

import static org.junit.Assert.*;

import br.com.productapi.models.entities.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class CategoryResponseTest {

    @Test
    public void testConstructorWithFields() {
        CategoryResponse response = new CategoryResponse(1, "Test Category");

        assertEquals(1, response.getId().intValue());
        assertEquals("Test Category", response.getDescription());
    }

    @Test
    public void testGetAndSetId() {
        CategoryResponse response = new CategoryResponse();
        response.setId(1);

        assertEquals(1, response.getId().intValue());
    }

    @Test
    public void testGetAndSetDescription() {
        CategoryResponse response = new CategoryResponse();
        response.setDescription("Test Category");

        assertEquals("Test Category", response.getDescription());
    }

    @Test
    public void testOf() {
        Category category = new Category();
        category.setId(1);
        category.setDescription("Test Category");

        CategoryResponse response = CategoryResponse.of(category);

        assertEquals(1, response.getId().intValue());
        assertEquals("Test Category", response.getDescription());
    }

    @Test
    public void testEquals() {
        CategoryResponse response1 = new CategoryResponse(1, "Test Category");
        CategoryResponse response2 = new CategoryResponse(1, "Test Category");
        CategoryResponse response3 = new CategoryResponse(2, "Test Category");
        CategoryResponse response4 = new CategoryResponse(1, "Different Category");

        assertEquals(response1, response2);
        assertNotEquals(response1, response3);
        assertNotEquals(response1, response4);
    }

    @Test
    public void testHashCode() {
        CategoryResponse response1 = new CategoryResponse(1, "Test Category");
        CategoryResponse response2 = new CategoryResponse(1, "Test Category");
        CategoryResponse response3 = new CategoryResponse(2, "Test Category");
        CategoryResponse response4 = new CategoryResponse(1, "Different Category");

        assertEquals(response1.hashCode(), response2.hashCode());
        assertNotEquals(response1.hashCode(), response3.hashCode());
        assertNotEquals(response1.hashCode(), response4.hashCode());
    }

    @Test
    public void testToString() {
        CategoryResponse response = new CategoryResponse(1, "Test Category");
        String expectedString = "CategoryResponse(id=1, description=Test Category)";

        assertEquals(expectedString, response.toString());
    }
}
