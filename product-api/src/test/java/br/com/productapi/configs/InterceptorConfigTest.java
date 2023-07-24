package br.com.productapi.configs;

import br.com.productapi.interceptors.AuthInterceptor;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@ActiveProfiles("test")
public class InterceptorConfigTest {

    @Test
    public void authInterceptorShouldReturnInstanceOfAuthInterceptor() {
        InterceptorConfig interceptorConfig = new InterceptorConfig();

        AuthInterceptor authInterceptor = interceptorConfig.authInterceptor();

        assertInstanceOf(AuthInterceptor.class, authInterceptor);
    }
}
