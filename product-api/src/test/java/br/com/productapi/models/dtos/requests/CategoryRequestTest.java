package br.com.productapi.models.dtos.requests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class CategoryRequestTest {

    @Test
    public void testGetAndSetDescription() {
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setDescription("Test Description");

        assertEquals("Test Description", categoryRequest.getDescription());
    }

    @Test
    public void testEquals() {
        CategoryRequest request1 = new CategoryRequest();
        request1.setDescription("Description");

        CategoryRequest request2 = new CategoryRequest();
        request2.setDescription("Description");

        CategoryRequest request3 = new CategoryRequest();
        request3.setDescription("Another Description");

        assertEquals(request1, request2);
        assertNotEquals(request1, request3);
    }

    @Test
    public void testHashCode() {
        CategoryRequest request1 = new CategoryRequest();
        request1.setDescription("Description");

        CategoryRequest request2 = new CategoryRequest();
        request2.setDescription("Description");

        CategoryRequest request3 = new CategoryRequest();
        request3.setDescription("Another Description");

        assertEquals(request1.hashCode(), request2.hashCode());
        assertNotEquals(request1.hashCode(), request3.hashCode());
    }

    @Test
    public void testToString() {
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setDescription("Test Description");

        assertEquals("CategoryRequest(description=Test Description)", categoryRequest.toString());
    }
}
