package br.com.productapi.sales.dtos.responses;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class SalesProductResponseTest {

    @Test
    public void testNoArgsConstructor() {
        SalesProductResponse response = new SalesProductResponse();

        // Ensure the list is initialized as an empty list
        assertEquals(0, response.getSalesIds().size());
    }

    @Test
    public void testAllArgsConstructor() {
        List<String> salesIds = Arrays.asList("SALE001", "SALE002");
        SalesProductResponse response = new SalesProductResponse(salesIds);

        // Ensure the list is correctly initialized with the given salesIds
        assertEquals(salesIds, response.getSalesIds());
    }

    @Test
    public void testSetterAndGetter() {
        List<String> salesIds = Arrays.asList("SALE001", "SALE002");
        SalesProductResponse response = new SalesProductResponse();

        // Set the salesIds using the setter method
        response.setSalesIds(salesIds);

        // Ensure the getter method returns the correct salesIds
        assertEquals(salesIds, response.getSalesIds());
    }
}
