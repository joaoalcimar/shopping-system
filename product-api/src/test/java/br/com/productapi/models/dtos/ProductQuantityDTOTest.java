package br.com.productapi.models.dtos;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class ProductQuantityDTOTest {

    @Test
    public void testGetAndSetProductId() {
        ProductQuantityDTO dto = new ProductQuantityDTO();
        dto.setProductId(123);

        assertEquals(Integer.valueOf(123), dto.getProductId());
    }

    @Test
    public void testGetAndSetQuantity() {
        ProductQuantityDTO dto = new ProductQuantityDTO();
        dto.setQuantity(10);

        assertEquals(Integer.valueOf(10), dto.getQuantity());
    }

    @Test
    public void testEquals() {
        ProductQuantityDTO dto1 = new ProductQuantityDTO(1, 5);
        ProductQuantityDTO dto2 = new ProductQuantityDTO(1, 5);
        ProductQuantityDTO dto3 = new ProductQuantityDTO(2, 5);
        ProductQuantityDTO dto4 = new ProductQuantityDTO(1, 10);

        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertNotEquals(dto1, dto4);
    }

    @Test
    public void testHashCode() {
        ProductQuantityDTO dto1 = new ProductQuantityDTO(1, 5);
        ProductQuantityDTO dto2 = new ProductQuantityDTO(1, 5);
        ProductQuantityDTO dto3 = new ProductQuantityDTO(2, 5);
        ProductQuantityDTO dto4 = new ProductQuantityDTO(1, 10);

        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
        assertNotEquals(dto1.hashCode(), dto4.hashCode());
    }

    @Test
    public void testToString() {
        ProductQuantityDTO dto = new ProductQuantityDTO(1, 5);
        assertEquals("ProductQuantityDTO(productId=1, quantity=5)", dto.toString());
    }
}

