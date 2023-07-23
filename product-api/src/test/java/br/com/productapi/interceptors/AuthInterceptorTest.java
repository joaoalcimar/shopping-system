package br.com.productapi.interceptors;

import br.com.productapi.exceptions.AuthenticationException;
import br.com.productapi.services.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
public class AuthInterceptorTest {

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthInterceptor authInterceptor;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    public void testPreHandle_ShouldAllowOptionsRequest() throws Exception {
        request.setMethod(HttpMethod.OPTIONS.name());

        boolean result = authInterceptor.preHandle(request, response, null);

        assert (result);
        verifyNoInteractions(jwtService);
    }

    @Test
    public void testPreHandle_ShouldValidateAuthorizationHeader() throws Exception {
        String validToken = "valid_token";
        request.addHeader("Authorization", validToken);

        doNothing().when(jwtService).validateAuthorization(validToken);

        boolean result = authInterceptor.preHandle(request, response, null);

        assert (result);
        verify(jwtService, times(1)).validateAuthorization(validToken);
    }

    @Test
    public void testPreHandle_ShouldRejectInvalidAuthorizationHeader() throws Exception {
        String invalidToken = "invalid_token";
        request.addHeader("Authorization", invalidToken);

        doThrow(AuthenticationException.class).when(jwtService).validateAuthorization(invalidToken);

        assertThrows(AuthenticationException.class, () ->  authInterceptor.preHandle(request, response, null));

        verify(jwtService, times(1)).validateAuthorization(invalidToken);
    }
}