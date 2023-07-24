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
        response.setSalesIds(List.of());

        assertEquals(0, response.getSalesIds().size());
    }

    @Test
    public void testAllArgsConstructor() {
        List<String> salesIds = Arrays.asList("SALE001", "SALE002");
        SalesProductResponse response = new SalesProductResponse(salesIds);

        assertEquals(salesIds, response.getSalesIds());
    }

    @Test
    public void testSetterAndGetter() {
        List<String> salesIds = Arrays.asList("SALE001", "SALE002");
        SalesProductResponse response = new SalesProductResponse();

        response.setSalesIds(salesIds);

        assertEquals(salesIds, response.getSalesIds());
    }
}
