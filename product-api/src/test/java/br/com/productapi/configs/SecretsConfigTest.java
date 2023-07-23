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
public class SecretsConfigTest {

    @Autowired
    private SecretsConfig secretsConfig;

    @Autowired
    private Environment environment;

    @Test
    public void testApiSecret() {
        assertNotNull(secretsConfig);
        String expectedApiSecret = environment.getProperty("app-config.secrets.api-secret");
        assertEquals(expectedApiSecret, secretsConfig.getApiSecret());
    }
}
