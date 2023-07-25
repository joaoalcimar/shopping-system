package br.com.productapi.configs;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@ActiveProfiles("test")
public class ServiceConfigTest {

    private ServiceConfig serviceConfig;

    @Before
    public void setUp(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ServiceConfig.class);

        serviceConfig = context.getBean(ServiceConfig.class);
    }

    @Test
    public void testSalesApiHost() {
        assertNotNull(serviceConfig);
        String expectedAPIHost = "http://localhost:8082";
        assertEquals(expectedAPIHost, serviceConfig.getSalesApiHost());
    }
}