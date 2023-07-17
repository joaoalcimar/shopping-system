package br.com.productapi.configs;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource("classpath:services.properties")
public class ServiceConfig {

    @Value("${app-config.services.sales}")
    private String salesApiHost;
}
