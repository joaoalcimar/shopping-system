package br.com.productapi.configs;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class ServiceConfigTest {

    @Autowired
    private ServiceConfig serviceConfig;

    @Autowired
    private Environment environment;

    @Test
    public void testSalesApiHost() {
        assertNotNull(serviceConfig);
        String expectedSalesApiHost = environment.getProperty("app-config.services.sales");
        assertEquals(expectedSalesApiHost, serviceConfig.getSalesApiHost());
    }
}