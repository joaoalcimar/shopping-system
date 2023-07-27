package br.com.productapi.interceptors;

import static org.junit.Assert.*;

import br.com.productapi.exceptions.ValidationException;
import feign.RequestTemplate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class FeignClientAuthInterceptorTest {

    private FeignClientAuthInterceptor interceptor;
    private RequestTemplate requestTemplate;

    @Before
    public void setup() {
        interceptor = new FeignClientAuthInterceptor();
        requestTemplate = new RequestTemplate();
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.addHeader("Authorization", "Bearer my-access-token");

        ServletRequestAttributes requestAttributes = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(requestAttributes);
    }

    @Test
    public void testApply() {
        interceptor.apply(requestTemplate);
        String authorizationHeader = requestTemplate.headers().get("Authorization").iterator().next();
        assertEquals("Bearer my-access-token", authorizationHeader);
    }

    @Test(expected = ValidationException.class)
    public void testApplyWhenNoCurrentRequest() {
        RequestContextHolder.setRequestAttributes(null);

        interceptor.apply(requestTemplate);
    }
}
