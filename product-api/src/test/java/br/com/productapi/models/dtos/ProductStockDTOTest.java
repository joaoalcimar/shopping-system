package br.com.productapi.models.dtos;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class ProductStockDTOTest {

    @Test
    public void testGetAndSetSalesId() {
        ProductStockDTO dto = new ProductStockDTO();
        dto.setSalesId("12345");

        assertEquals("12345", dto.getSalesId());
    }

    @Test
    public void testGetAndSetProducts() {
        List<ProductQuantityDTO> products = Arrays.asList(
                new ProductQuantityDTO(1, 5),
                new ProductQuantityDTO(2, 10)
        );

        ProductStockDTO dto = new ProductStockDTO();
        dto.setProducts(products);

        assertEquals(products, dto.getProducts());
    }

    @Test
    public void testEquals() {
        List<ProductQuantityDTO> products1 = Arrays.asList(
                new ProductQuantityDTO(1, 5),
                new ProductQuantityDTO(2, 10)
        );
        List<ProductQuantityDTO> products2 = Arrays.asList(
                new ProductQuantityDTO(1, 5),
                new ProductQuantityDTO(2, 10)
        );
        List<ProductQuantityDTO> products3 = Arrays.asList(
                new ProductQuantityDTO(3, 5),
                new ProductQuantityDTO(4, 10)
        );

        ProductStockDTO dto1 = new ProductStockDTO("12345", products1);
        ProductStockDTO dto2 = new ProductStockDTO("12345", products2);
        ProductStockDTO dto3 = new ProductStockDTO("54321", products1);
        ProductStockDTO dto4 = new ProductStockDTO("12345", products3);

        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertNotEquals(dto1, dto4);
    }

    @Test
    public void testHashCode() {
        List<ProductQuantityDTO> products1 = Arrays.asList(
                new ProductQuantityDTO(1, 5),
                new ProductQuantityDTO(2, 10)
        );
        List<ProductQuantityDTO> products2 = Arrays.asList(
                new ProductQuantityDTO(1, 5),
                new ProductQuantityDTO(2, 10)
        );
        List<ProductQuantityDTO> products3 = Arrays.asList(
                new ProductQuantityDTO(3, 5),
                new ProductQuantityDTO(4, 10)
        );

        ProductStockDTO dto1 = new ProductStockDTO("12345", products1);
        ProductStockDTO dto2 = new ProductStockDTO("12345", products2);
        ProductStockDTO dto3 = new ProductStockDTO("54321", products1);
        ProductStockDTO dto4 = new ProductStockDTO("12345", products3);

        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
        assertNotEquals(dto1.hashCode(), dto4.hashCode());
    }

    @Test
    public void testToString() {
        List<ProductQuantityDTO> products = Arrays.asList(
                new ProductQuantityDTO(1, 5),
                new ProductQuantityDTO(2, 10)
        );

        ProductStockDTO dto = new ProductStockDTO("12345", products);
        String expectedString = "ProductStockDTO(salesId=12345, products=[ProductQuantityDTO(productId=1, quantity=5), ProductQuantityDTO(productId=2, quantity=10)])";
        assertEquals(expectedString, dto.toString());
    }
}

