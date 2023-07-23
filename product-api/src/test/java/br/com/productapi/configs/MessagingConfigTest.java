package br.com.productapi.configs;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.amqp.core.Queue;
import org.springframework.test.context.ActiveProfiles;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class MessagingConfigTest {

    @Autowired
    private TopicExchange productTopicExchange;

    @Autowired
    private Queue productStockMq;

    @Autowired
    private Queue salesConfirmationMq;

    @Autowired
    private Binding productStockMqBinding;

    @Autowired
    private Binding salesConfirmationMqBinding;

    @Autowired
    private MessageConverter jsonMessageConverter;

    @Test
    public void testProductTopicExchange() {
        assertNotNull(productTopicExchange);
    }

    @Test
    public void testProductStockMq() {
        assertNotNull(productStockMq);
    }

    @Test
    public void testSalesConfirmationMq() {
        assertNotNull(salesConfirmationMq);
    }

    @Test
    public void testProductStockMqBinding() {
        assertNotNull(productStockMqBinding);
    }

    @Test
    public void testSalesConfirmationMqBinding() {
        assertNotNull(salesConfirmationMqBinding);
    }

    @Test
    public void testJsonMessageConverter() {
        assertNotNull(jsonMessageConverter);
    }
}
