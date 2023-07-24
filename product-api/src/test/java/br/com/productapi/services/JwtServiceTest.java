package br.com.productapi.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.productapi.configs.SecretsConfig;
import br.com.productapi.exceptions.AuthenticationException;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class JwtServiceTest {

    private JwtService jwtService;

    private SecretsConfig secretsConfig;

    @BeforeEach
    public void setup() {
        secretsConfig = mock(SecretsConfig.class);
        jwtService = new JwtService(secretsConfig);
    }

    @Test
    public void testValidateAuthorizationWithInvalidToken() {
        String apiSecret = "your_api_secret_here";
        when(secretsConfig.getApiSecret()).thenReturn(apiSecret);

        String accessToken = "invalid.access.token";

        assertThrows(AuthenticationException.class, () -> jwtService.validateAuthorization(accessToken));
    }

    @Test
    public void testValidateAuthorizationWithEmptyToken() {
        assertThrows(AuthenticationException.class, () -> jwtService.validateAuthorization(""));
    }

    @Test
    public void testExtractTokenWithSpace() {
        String tokenWithSpace = "Bearer valid_access_token";

        String extractedToken = jwtService.extractToken(tokenWithSpace);

        assertEquals("valid_access_token", extractedToken);
    }

    @Test
    public void testExtractTokenWithoutSpace() {
        String tokenWithoutSpace = "valid_access_token";

        String extractedToken = jwtService.extractToken(tokenWithoutSpace);

        assertEquals("valid_access_token", extractedToken);
    }

    @Test
    public void testExtractTokenWithEmptyToken() {
        assertThrows(AuthenticationException.class, () -> jwtService.extractToken(""));
    }
}