package br.com.productapi.models.dtos.requests;

import static org.junit.Assert.*;

import br.com.productapi.models.dtos.ProductQuantityDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class ProductCheckStockRequestTest {

    @Test
    public void testGetAndSetProducts() {
        List<ProductQuantityDTO> products = new ArrayList<>();
        products.add(new ProductQuantityDTO(1, 10));
        products.add(new ProductQuantityDTO(2, 5));

        ProductCheckStockRequest request = new ProductCheckStockRequest();
        request.setProducts(products);

        assertEquals(products, request.getProducts());
    }

    @Test
    public void testEquals() {
        List<ProductQuantityDTO> products1 = new ArrayList<>();
        products1.add(new ProductQuantityDTO(1, 10));
        products1.add(new ProductQuantityDTO(2, 5));

        List<ProductQuantityDTO> products2 = new ArrayList<>();
        products2.add(new ProductQuantityDTO(1, 10));
        products2.add(new ProductQuantityDTO(2, 5));

        List<ProductQuantityDTO> products3 = new ArrayList<>();
        products3.add(new ProductQuantityDTO(3, 15));
        products3.add(new ProductQuantityDTO(4, 8));

        ProductCheckStockRequest request1 = new ProductCheckStockRequest(products1);
        ProductCheckStockRequest request2 = new ProductCheckStockRequest(products2);
        ProductCheckStockRequest request3 = new ProductCheckStockRequest(products3);

        assertEquals(request1, request2);
        assertNotEquals(request1, request3);
    }

    @Test
    public void testHashCode() {
        List<ProductQuantityDTO> products1 = new ArrayList<>();
        products1.add(new ProductQuantityDTO(1, 10));
        products1.add(new ProductQuantityDTO(2, 5));

        List<ProductQuantityDTO> products2 = new ArrayList<>();
        products2.add(new ProductQuantityDTO(1, 10));
        products2.add(new ProductQuantityDTO(2, 5));

        List<ProductQuantityDTO> products3 = new ArrayList<>();
        products3.add(new ProductQuantityDTO(3, 15));
        products3.add(new ProductQuantityDTO(4, 8));

        ProductCheckStockRequest request1 = new ProductCheckStockRequest(products1);
        ProductCheckStockRequest request2 = new ProductCheckStockRequest(products2);
        ProductCheckStockRequest request3 = new ProductCheckStockRequest(products3);

        assertEquals(request1.hashCode(), request2.hashCode());
        assertNotEquals(request1.hashCode(), request3.hashCode());
    }

    @Test
    public void testToString() {
        List<ProductQuantityDTO> products = new ArrayList<>();
        products.add(new ProductQuantityDTO(1, 10));
        products.add(new ProductQuantityDTO(2, 5));

        ProductCheckStockRequest request = new ProductCheckStockRequest(products);

        assertEquals("ProductCheckStockRequest(products=[ProductQuantityDTO(productId=1, quantity=10), ProductQuantityDTO(productId=2, quantity=5)])", request.toString());
    }
}