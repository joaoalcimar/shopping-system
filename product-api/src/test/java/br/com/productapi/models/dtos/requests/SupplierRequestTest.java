package br.com.productapi.models.dtos.requests;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class SupplierRequestTest {

    @Test
    public void testGetAndSetName() {
        SupplierRequest request = new SupplierRequest();
        request.setName("Test Supplier");

        assertEquals("Test Supplier", request.getName());
    }

    @Test
    public void testEquals() {
        SupplierRequest request1 = new SupplierRequest();
        request1.setName("Test Supplier");

        SupplierRequest request2 = new SupplierRequest();
        request2.setName("Test Supplier");

        SupplierRequest request3 = new SupplierRequest();
        request3.setName("Another Supplier");

        assertEquals(request1, request2);
        assertNotEquals(request1, request3);
    }

    @Test
    public void testHashCode() {
        SupplierRequest request1 = new SupplierRequest();
        request1.setName("Test Supplier");

        SupplierRequest request2 = new SupplierRequest();
        request2.setName("Test Supplier");

        SupplierRequest request3 = new SupplierRequest();
        request3.setName("Another Supplier");

        assertEquals(request1.hashCode(), request2.hashCode());
        assertNotEquals(request1.hashCode(), request3.hashCode());
    }

    @Test
    public void testToString() {
        SupplierRequest request = new SupplierRequest();
        request.setName("Test Supplier");

        assertEquals("SupplierRequest(name=Test Supplier)", request.toString());
    }
}
