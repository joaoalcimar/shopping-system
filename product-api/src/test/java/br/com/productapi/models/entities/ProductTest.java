package br.com.productapi.models.entities;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import br.com.productapi.models.dtos.requests.ProductRequest;
import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class ProductTest {

    @Test
    public void testConstructorWithFields() {
        Category category = new Category(1, "Test Category");
        Supplier supplier = new Supplier(1, "Test Supplier");

        Product product = new Product(1, "Test Product", 10, category, supplier, LocalDateTime.now());

        assertEquals(1, product.getId().intValue());
        assertEquals("Test Product", product.getName());
        assertEquals(10, product.getAvailableQuantity().intValue());
        assertEquals(category, product.getCategory());
        assertEquals(supplier, product.getSupplier());
        assertNotNull(product.getCreatedAt());
    }

    @Test
    public void testGetAndSetId() {
        Product product = new Product();
        product.setId(1);

        assertEquals(1, product.getId().intValue());
    }

    @Test
    public void testGetAndSetName() {
        Product product = new Product();
        product.setName("Test Product");

        assertEquals("Test Product", product.getName());
    }

    @Test
    public void testGetAndSetAvailableQuantity() {
        Product product = new Product();
        product.setAvailableQuantity(10);

        assertEquals(10, product.getAvailableQuantity().intValue());
    }

    @Test
    public void testGetAndSetCategory() {
        Category category = new Category(1, "Test Category");
        Product product = new Product();
        product.setCategory(category);

        assertEquals(category, product.getCategory());
    }

    @Test
    public void testGetAndSetSupplier() {
        Supplier supplier = new Supplier(1, "Test Supplier");
        Product product = new Product();
        product.setSupplier(supplier);

        assertEquals(supplier, product.getSupplier());
    }

    @Test
    public void testGetAndSetCreatedAt() {
        LocalDateTime createdAt = LocalDateTime.now();
        Product product = new Product();
        product.setCreatedAt(createdAt);

        assertEquals(createdAt, product.getCreatedAt());
    }

    @Test
    public void testOf() {
        Category category = new Category(1, "Test Category");
        Supplier supplier = new Supplier(1, "Test Supplier");
        ProductRequest request = new ProductRequest();
        request.setName("Test Product");
        request.setAvailableQuantity(10);

        Product product = Product.of(request, category, supplier);

        assertNull(product.getId());
        assertEquals("Test Product", product.getName());
        assertEquals(10, product.getAvailableQuantity().intValue());
        assertEquals(category, product.getCategory());
        assertEquals(supplier, product.getSupplier());
    }

    @Test
    public void testUpdateStock() {
        Product product = new Product();
        product.setAvailableQuantity(10);

        product.updateStock(5);

        assertEquals(5, product.getAvailableQuantity().intValue());
    }

    @Test
    public void testEquals() {
        Category category = new Category(1, "Test Category");
        Supplier supplier = new Supplier(1, "Test Supplier");
        Product product1 = new Product(1, "Test Product", 10, category, supplier, LocalDateTime.now());
        Product product2 = new Product(1, "Test Product", 10, category, supplier, LocalDateTime.now());
        Product product3 = new Product(2, "Test Product", 10, category, supplier, LocalDateTime.now());
        Product product4 = new Product(1, "Different Product", 10, category, supplier, LocalDateTime.now());

        assertEquals(product1, product2);
        assertNotEquals(product1, product3);
        assertNotEquals(product1, product4);
    }

    @Test
    public void testHashCode() {
        Category category = new Category(1, "Test Category");
        Supplier supplier = new Supplier(1, "Test Supplier");
        Product product1 = new Product(1, "Test Product", 10, category, supplier, LocalDateTime.now());
        Product product2 = new Product(1, "Test Product", 10, category, supplier, LocalDateTime.now());
        Product product3 = new Product(2, "Test Product", 10, category, supplier, LocalDateTime.now());
        Product product4 = new Product(1, "Different Product", 10, category, supplier, LocalDateTime.now());

        assertEquals(product1.hashCode(), product2.hashCode());
        assertNotEquals(product1.hashCode(), product3.hashCode());
        assertNotEquals(product1.hashCode(), product4.hashCode());
    }

    @Test
    public void testToString() {
        Category category = new Category(1, "Test Category");
        Supplier supplier = new Supplier(1, "Test Supplier");
        Product product = new Product(1, "Test Product", 10, category, supplier, LocalDateTime.now());
        String expectedString = "Product(id=1, name=Test Product, availableQuantity=10, category=Category(id=1, description=Test Category), supplier=Supplier(id=1, name=Test Supplier), createdAt=" + product.getCreatedAt() + ")";

        assertEquals(expectedString, product.toString());
    }
}