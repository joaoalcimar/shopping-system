package br.com.productapi.configs;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class SecretsConfigTest {

    private SecretsConfig secretsConfig;

    @Before
    public void setup() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SecretsConfig.class);
        secretsConfig = context.getBean(SecretsConfig.class);
    }

    @Test
    public void testApiSecret() {
        String expectedApiSecret = "c2VjcmV0LWFwaS0xMjM0NTYtcGFzc3dvcmQtZW5jb2Rlci02NC1AQEA=";
        assertEquals(expectedApiSecret, secretsConfig.getApiSecret());
    }
}