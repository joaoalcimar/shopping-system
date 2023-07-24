package br.com.productapi.models.entities;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import br.com.productapi.models.dtos.requests.SupplierRequest;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class SupplierTest {

    @Test
    public void testConstructorWithFields() {
        Supplier supplier = new Supplier(1, "Test Supplier");

        assertEquals(1, supplier.getId().intValue());
        assertEquals("Test Supplier", supplier.getName());
    }

    @Test
    public void testGetAndSetId() {
        Supplier supplier = new Supplier();
        supplier.setId(1);

        assertEquals(1, supplier.getId().intValue());
    }

    @Test
    public void testGetAndSetName() {
        Supplier supplier = new Supplier();
        supplier.setName("Test Supplier");

        assertEquals("Test Supplier", supplier.getName());
    }

    @Test
    public void testOf() {
        SupplierRequest request = new SupplierRequest();
        request.setName("Test Supplier");

        Supplier supplier = Supplier.of(request);

        assertNull(supplier.getId());
        assertEquals("Test Supplier", supplier.getName());
    }

    @Test
    public void testEquals() {
        Supplier supplier1 = new Supplier(1, "Test Supplier");
        Supplier supplier2 = new Supplier(1, "Test Supplier");
        Supplier supplier3 = new Supplier(2, "Test Supplier");
        Supplier supplier4 = new Supplier(1, "Different Supplier");

        assertEquals(supplier1, supplier2);
        assertNotEquals(supplier1, supplier3);
        assertNotEquals(supplier1, supplier4);
    }

    @Test
    public void testHashCode() {
        Supplier supplier1 = new Supplier(1, "Test Supplier");
        Supplier supplier2 = new Supplier(1, "Test Supplier");
        Supplier supplier3 = new Supplier(2, "Test Supplier");
        Supplier supplier4 = new Supplier(1, "Different Supplier");

        assertEquals(supplier1.hashCode(), supplier2.hashCode());
        assertNotEquals(supplier1.hashCode(), supplier3.hashCode());
        assertNotEquals(supplier1.hashCode(), supplier4.hashCode());
    }

    @Test
    public void testToString() {
        Supplier supplier = new Supplier(1, "Test Supplier");
        String expectedString = "Supplier(id=1, name=Test Supplier)";

        assertEquals(expectedString, supplier.toString());
    }
}
