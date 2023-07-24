package br.com.productapi.models.dtos.responses;

import static org.junit.Assert.*;

import br.com.productapi.models.entities.Supplier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class SupplierResponseTest {

    @Test
    public void testBuilder() {
        SupplierResponse response = SupplierResponse.builder()
                .id(1)
                .name("Supplier 1")
                .build();

        assertEquals(1, response.getId().intValue());
        assertEquals("Supplier 1", response.getName());
    }

    @Test
    public void testOf() {
        Supplier supplier = new Supplier();
        supplier.setId(1);
        supplier.setName("Supplier 1");

        SupplierResponse response = SupplierResponse.of(supplier);

        assertEquals(1, response.getId().intValue());
        assertEquals("Supplier 1", response.getName());
    }

    @Test
    public void testSetterAndGetterForId() {
        SupplierResponse response = new SupplierResponse();
        response.setId(1);

        assertEquals(1, response.getId().intValue());
    }

    @Test
    public void testSetterAndGetterForName() {
        SupplierResponse response = new SupplierResponse();
        response.setName("Supplier 1");

        assertEquals("Supplier 1", response.getName());
    }

    @Test
    public void testToString() {
        SupplierResponse response = SupplierResponse.builder()
                .id(1)
                .name("Supplier 1")
                .build();

        String expectedString = "SupplierResponse(id=1, name=Supplier 1)";
        assertEquals(expectedString, response.toString());
    }
}

