package br.com.productapi.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:secrets.properties")
public class SecretsConfig {

    @Value("${app-config.secrets.api-secret}")
    private String apiSecret;

    public String getApiSecret() {
        return apiSecret;
    }
}
