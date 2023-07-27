package br.com.productapi.configs;

import br.com.productapi.interceptors.AuthInterceptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class InterceptorConfigTest {

    @Test
    public void authInterceptorShouldReturnInstanceOfAuthInterceptor() {
        InterceptorConfig interceptorConfig = new InterceptorConfig();

        AuthInterceptor authInterceptor = interceptorConfig.authInterceptor();

        assertInstanceOf(AuthInterceptor.class, authInterceptor);
    }
}
