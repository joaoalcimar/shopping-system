package br.com.productapi.models.dtos.requests;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class ProductRequestTest {

    @Test
    public void testGetAndSetName() {
        ProductRequest request = new ProductRequest();
        request.setName("Test Product");

        assertEquals("Test Product", request.getName());
    }

    @Test
    public void testGetAndSetAvailableQuantity() {
        ProductRequest request = new ProductRequest();
        request.setAvailableQuantity(100);

        assertEquals(Integer.valueOf(100), request.getAvailableQuantity());
    }

    @Test
    public void testGetAndSetCategoryId() {
        ProductRequest request = new ProductRequest();
        request.setCategoryId(1);

        assertEquals(Integer.valueOf(1), request.getCategoryId());
    }

    @Test
    public void testGetAndSetSupplierId() {
        ProductRequest request = new ProductRequest();
        request.setSupplierId(1);

        assertEquals(Integer.valueOf(1), request.getSupplierId());
    }

    @Test
    public void testEquals() {
        ProductRequest request1 = new ProductRequest();
        request1.setName("Test Product");
        request1.setAvailableQuantity(100);
        request1.setCategoryId(1);
        request1.setSupplierId(1);

        ProductRequest request2 = new ProductRequest();
        request2.setName("Test Product");
        request2.setAvailableQuantity(100);
        request2.setCategoryId(1);
        request2.setSupplierId(1);

        ProductRequest request3 = new ProductRequest();
        request3.setName("Another Product");
        request3.setAvailableQuantity(50);
        request3.setCategoryId(2);
        request3.setSupplierId(2);

        assertEquals(request1, request2);
        assertNotEquals(request1, request3);
    }

    @Test
    public void testHashCode() {
        ProductRequest request1 = new ProductRequest();
        request1.setName("Test Product");
        request1.setAvailableQuantity(100);
        request1.setCategoryId(1);
        request1.setSupplierId(1);

        ProductRequest request2 = new ProductRequest();
        request2.setName("Test Product");
        request2.setAvailableQuantity(100);
        request2.setCategoryId(1);
        request2.setSupplierId(1);

        ProductRequest request3 = new ProductRequest();
        request3.setName("Another Product");
        request3.setAvailableQuantity(50);
        request3.setCategoryId(2);
        request3.setSupplierId(2);

        assertEquals(request1.hashCode(), request2.hashCode());
        assertNotEquals(request1.hashCode(), request3.hashCode());
    }

    @Test
    public void testToString() {
        ProductRequest request = new ProductRequest();
        request.setName("Test Product");
        request.setAvailableQuantity(100);
        request.setCategoryId(1);
        request.setSupplierId(1);

        assertEquals("ProductRequest(name=Test Product, availableQuantity=100, categoryId=1, supplierId=1)", request.toString());
    }
}
