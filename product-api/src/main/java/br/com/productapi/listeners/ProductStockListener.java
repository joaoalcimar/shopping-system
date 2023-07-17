package br.com.productapi.listeners;

import br.com.productapi.models.dtos.ProductStockDTO;
import br.com.productapi.services.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductStockListener {

    @Autowired
    private ProductService productService;

    @RabbitListener(queues = "${app-config.rabbit.queue.product-stock}")
    public void receiveProductStockMessage(ProductStockDTO product) throws JsonProcessingException {
        log.info("Receiving message: {}", new ObjectMapper().writeValueAsString(product));
        productService.updateProductStock(product);
    }
}
