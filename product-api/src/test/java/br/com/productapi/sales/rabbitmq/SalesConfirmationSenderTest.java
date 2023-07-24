package br.com.productapi.sales.rabbitmq;

import br.com.productapi.sales.dtos.SalesConfirmationDTO;
import br.com.productapi.sales.enums.SalesStatus;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.Mockito.*;

public class SalesConfirmationSenderTest {

    @Mock
    private RabbitTemplate mockTemplate;

    private SalesConfirmationSender salesConfirmationSender;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        salesConfirmationSender = new SalesConfirmationSender();
        salesConfirmationSender.setTemplate(mockTemplate);
    }

    @Test
    public void testSendSalesConfirmationMessage_Failure() {
        SalesConfirmationDTO message = new SalesConfirmationDTO("sales-123", SalesStatus.REJECTED);

        doThrow(new RuntimeException("Failed to send message")).when(mockTemplate).convertAndSend(
                anyString(), anyString(), eq(message)
        );

        salesConfirmationSender.sendSalesConfirmationMessage(message);
    }

}