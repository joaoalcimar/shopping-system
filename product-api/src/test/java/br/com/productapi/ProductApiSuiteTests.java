package br.com.productapi;

import br.com.productapi.configs.InterceptorConfigTest;
import br.com.productapi.configs.MessagingConfigTest;
import br.com.productapi.configs.SecretsConfigTest;
import br.com.productapi.configs.ServiceConfigTest;
import br.com.productapi.controllers.StatusControllerTest;
import br.com.productapi.exceptions.settings.ExceptionDetailsTest;
import br.com.productapi.exceptions.settings.GlobalExceptionHandlerTest;
import br.com.productapi.interceptors.AuthInterceptorTest;
import br.com.productapi.interceptors.FeignClientAuthInterceptorTest;
import br.com.productapi.listeners.ProductStockListenerTest;
import br.com.productapi.models.dtos.ProductQuantityDTOTest;
import br.com.productapi.models.dtos.ProductStockDTOTest;
import br.com.productapi.models.dtos.requests.CategoryRequestTest;
import br.com.productapi.models.dtos.requests.ProductCheckStockRequestTest;
import br.com.productapi.models.dtos.requests.ProductRequestTest;
import br.com.productapi.models.dtos.requests.SupplierRequestTest;
import br.com.productapi.models.dtos.responses.JwtResponseTest;
import br.com.productapi.models.dtos.responses.ProductResponseTest;
import br.com.productapi.models.dtos.responses.SalesResponseTest;
import br.com.productapi.models.dtos.responses.SuccessResponseTest;
import br.com.productapi.services.CategoryServiceTest;
import br.com.productapi.services.JwtServiceTest;
import br.com.productapi.services.SupplierServiceTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import static org.junit.Assert.assertTrue;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        // configs dir
        InterceptorConfigTest.class,
        MessagingConfigTest.class,
        SecretsConfigTest.class,
        ServiceConfigTest.class,
        // controllers dir
        StatusControllerTest.class,
        // exceptions
        ExceptionDetailsTest.class,
        GlobalExceptionHandlerTest.class,
        // interceptors dir
        AuthInterceptorTest.class,
        FeignClientAuthInterceptorTest.class,
        // listeners dir
        ProductStockListenerTest.class,
        // models dir
        CategoryRequestTest.class,
        ProductCheckStockRequestTest.class,
        ProductRequestTest.class,
        SupplierRequestTest.class,
        CategoryRequestTest.class,
        JwtResponseTest.class,
        ProductResponseTest.class,
        SalesResponseTest.class,
        SuccessResponseTest.class,
        SupplierRequestTest.class,
        ProductQuantityDTOTest.class,
        ProductStockDTOTest.class,
        // sales dir
        // services dir
        CategoryServiceTest.class,
        JwtServiceTest.class,
        SupplierServiceTest.class
})

public class ProductApiSuiteTests {

    @Test
    public void contextLoads() {
        assertTrue(true);
    }
}
