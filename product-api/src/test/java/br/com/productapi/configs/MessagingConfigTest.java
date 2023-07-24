package br.com.productapi.configs;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class MessagingConfigTest {

    private MessagingConfig messagingConfig;

    @Before
    public void setup() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MessagingConfig.class);

        messagingConfig = context.getBean(MessagingConfig.class);;
    }

    @Test
    public void testProductTopicExchange() {
        TopicExchange topicExchange = messagingConfig.productTopicExchange();
        assertNotNull(topicExchange);
        assertEquals("product.topic", topicExchange.getName());
    }

    @Test
    public void testProductStockMq() {
        Queue productStockMq = messagingConfig.productStockMq();
        assertNotNull(productStockMq);
        assertEquals("product-stock-update.queue", productStockMq.getName());
        assertTrue(productStockMq.isDurable());
    }

    @Test
    public void testSalesConfirmationMq() {
        Queue salesConfirmationMq = messagingConfig.salesConfirmationMq();
        assertNotNull(salesConfirmationMq);
        assertEquals("sales-confirmation.queue", salesConfirmationMq.getName());
        assertTrue(salesConfirmationMq.isDurable());
    }

    @Test
    public void testProductStockMqBinding() {
        TopicExchange topicExchange = messagingConfig.productTopicExchange();
        Binding productStockMqBinding = messagingConfig.productStockMqBinding(topicExchange);
        assertNotNull(productStockMqBinding);
        assertEquals("product-stock-update.queue", productStockMqBinding.getDestination());
        assertEquals("product.topic", productStockMqBinding.getExchange());
        assertEquals("product-stock-update.routingKey", productStockMqBinding.getRoutingKey());
    }

    @Test
    public void testSalesConfirmationMqBinding() {
        TopicExchange topicExchange = messagingConfig.productTopicExchange();
        Binding salesConfirmationMqBinding = messagingConfig.salesConfirmationMqBinding(topicExchange);
        assertNotNull(salesConfirmationMqBinding);
        assertEquals("sales-confirmation.queue", salesConfirmationMqBinding.getDestination());
        assertEquals("product.topic", salesConfirmationMqBinding.getExchange());
        assertEquals("sales-confirmation.routingKey", salesConfirmationMqBinding.getRoutingKey());
    }

    @Test
    public void testJsonMessageConverter() {
        MessageConverter jsonMessageConverter = messagingConfig.jsonMessageConverter();
        assertNotNull(jsonMessageConverter);
        assertTrue(jsonMessageConverter instanceof Jackson2JsonMessageConverter);
    }
}

