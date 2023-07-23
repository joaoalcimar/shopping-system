package br.com.productapi.interceptors;

import br.com.productapi.exceptions.ValidationException;
import feign.RequestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
public class FeignClientAuthInterceptorTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private RequestTemplate template;

    @InjectMocks
    private FeignClientAuthInterceptor interceptor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        RequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
    }

    @Test
    public void testApply() {
        String authorizationValue = "Bearer abc123";
        when(request.getHeader("Authorization")).thenReturn(authorizationValue);

        interceptor.apply(template);

        verify(template, times(1)).header("Authorization", authorizationValue);
    }

    @Test
    public void testApplyThrowsValidationExceptionWhenNoRequestContext() {
        RequestContextHolder.resetRequestAttributes();

        assertThrows(ValidationException.class, () -> interceptor.apply(template));
    }
}
