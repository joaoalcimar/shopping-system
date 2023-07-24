package br.com.productapi.listeners;

import br.com.productapi.models.dtos.ProductStockDTO;
import br.com.productapi.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
public class ProductStockListenerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductStockListener productStockListener;

    @Captor
    private ArgumentCaptor<ProductStockDTO> productStockDTOCaptor;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testReceiveProductStockMessage() throws JsonProcessingException {
        // Create a sample ProductStockDTO
        ProductStockDTO productStockDTO = new ProductStockDTO();
        productStockDTO.setSalesId("{\"salesId\":\"test123\"}");
        productStockDTO.setProducts(new ArrayList<>());

        // Convert the ProductStockDTO to JSON string
        String productStockJson = new ObjectMapper().writeValueAsString(productStockDTO);

        // Call the receiveProductStockMessage method with the JSON message
        productStockListener.receiveProductStockMessage(productStockDTO);

        // Verify that the productService.updateProductStock method is called with the correct argument
        verify(productService, times(1)).updateProductStock(productStockDTOCaptor.capture());

        // Check the captured argument and verify its content
        ProductStockDTO capturedProductStockDTO = productStockDTOCaptor.getValue();
        assertEquals(productStockDTO.getProducts(), capturedProductStockDTO.getProducts());
        assertEquals(productStockDTO.getSalesId(), capturedProductStockDTO.getSalesId());
    }
}