package br.com.productapi.sales.rabbitmq;

import br.com.productapi.sales.dtos.SalesConfirmationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Setter
@Component
public class SalesConfirmationSender {

    @Autowired
    private RabbitTemplate template;

    @Value("${app-config.rabbit.exchange.product}")
    private String productTopicExchange;

    @Value("${app-config.rabbit.routingKey.sales-confirmation}")
    private String salesConfirmationKey;

    public void sendSalesConfirmationMessage(SalesConfirmationDTO message){
        try{
            log.info("Sending message : {}", new ObjectMapper().writeValueAsString(message));
            template.convertAndSend(productTopicExchange, salesConfirmationKey, message);
            log.info("Message was sent successfully.");
        }catch (Exception e){
            log.info("Error while trying to send sales confirmation message: .", e);
        }

    }
}
