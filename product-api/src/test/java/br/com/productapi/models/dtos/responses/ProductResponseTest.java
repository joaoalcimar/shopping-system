package br.com.productapi.models.dtos.responses;

import static org.junit.Assert.*;

import br.com.productapi.models.entities.Category;
import br.com.productapi.models.entities.Product;
import br.com.productapi.models.entities.Supplier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class ProductResponseTest {

    @Test
    public void testBuilder() {
        ProductResponse response = ProductResponse.builder()
                .id(1)
                .name("Product 1")
                .availableQuantity(10)
                .categoryResponse(CategoryResponse.builder()
                        .id(100)
                        .description("Category A")
                        .build())
                .supplierResponse(SupplierResponse.builder()
                        .id(200)
                        .name("Supplier X")
                        .build())
                .createdAt(LocalDateTime.of(2023, 7, 24, 12, 0))
                .build();

        assertEquals(1, response.getId().intValue());
        assertEquals("Product 1", response.getName());
        assertEquals(10, response.getAvailableQuantity().intValue());
        assertEquals(100, response.getCategoryResponse().getId().intValue());
        assertEquals("Category A", response.getCategoryResponse().getDescription());
        assertEquals(200, response.getSupplierResponse().getId().intValue());
        assertEquals("Supplier X", response.getSupplierResponse().getName());
        assertEquals(LocalDateTime.of(2023, 7, 24, 12, 0), response.getCreatedAt());
    }

    @Test
    public void testOf() {
        Product product = new Product();
        product.setId(1);
        product.setName("Product 1");
        product.setAvailableQuantity(10);
        Category category = new Category();
        category.setId(100);
        category.setDescription("Category A");
        product.setCategory(category);
        Supplier supplier = new Supplier();
        supplier.setId(200);
        supplier.setName("Supplier X");
        product.setSupplier(supplier);
        product.setCreatedAt(LocalDateTime.of(2023, 7, 24, 12, 0));

        ProductResponse response = ProductResponse.of(product);

        assertEquals(1, response.getId().intValue());
        assertEquals("Product 1", response.getName());
        assertEquals(10, response.getAvailableQuantity().intValue());
        assertEquals(100, response.getCategoryResponse().getId().intValue());
        assertEquals("Category A", response.getCategoryResponse().getDescription());
        assertEquals(200, response.getSupplierResponse().getId().intValue());
        assertEquals("Supplier X", response.getSupplierResponse().getName());
        assertEquals(LocalDateTime.of(2023, 7, 24, 12, 0), response.getCreatedAt());
    }

    @Test
    public void testSetterAndGetterForId() {
        ProductResponse response = new ProductResponse();
        response.setId(1);

        assertEquals(1, response.getId().intValue());
    }

    @Test
    public void testSetterAndGetterForName() {
        ProductResponse response = new ProductResponse();
        response.setName("Product 1");

        assertEquals("Product 1", response.getName());
    }

    @Test
    public void testToString() {
        ProductResponse response = ProductResponse.builder()
                .id(1)
                .name("Product 1")
                .availableQuantity(10)
                .categoryResponse(CategoryResponse.builder()
                        .id(100)
                        .description("Category A")
                        .build())
                .supplierResponse(SupplierResponse.builder()
                        .id(200)
                        .name("Supplier X")
                        .build())
                .createdAt(LocalDateTime.of(2023, 7, 24, 12, 0))
                .build();

        String expectedString = "ProductResponse(id=1, name=Product 1, availableQuantity=10, " +
                "categoryResponse=CategoryResponse(id=100, description=Category A), " +
                "supplierResponse=SupplierResponse(id=200, name=Supplier X), " +
                "createdAt=2023-07-24T12:00)";

        assertEquals(expectedString, response.toString());
    }
}

