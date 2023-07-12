package br.com.productapi.configs;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource("classpath:secrets.properties")
public class SecretsConfig {

    @Value("${app-config.secrets.api-secret}")
    private String apiSecret;

}
