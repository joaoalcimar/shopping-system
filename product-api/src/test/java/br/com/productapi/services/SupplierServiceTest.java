package br.com.productapi.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.com.productapi.exceptions.EmptyStringException;
import br.com.productapi.exceptions.NotFoundException;
import br.com.productapi.exceptions.ValidationException;
import br.com.productapi.models.dtos.requests.SupplierRequest;
import br.com.productapi.models.dtos.responses.SuccessResponse;
import br.com.productapi.models.dtos.responses.SupplierResponse;
import br.com.productapi.models.entities.Supplier;
import br.com.productapi.repositories.SupplierRepository;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class SupplierServiceTest {

    private final SupplierRepository supplierRepository = mock(SupplierRepository.class);

    private final ProductService productService= mock(ProductService.class);

    private SupplierService supplierService;

    @BeforeEach
    public void setup() {
        supplierService = new SupplierService(supplierRepository, productService);
    }

    @Test
    public void testSaveSupplier() {
        // Mocking the input request
        SupplierRequest request = new SupplierRequest();
        request.setName("Test Supplier");

        // Mocking the Supplier entity returned from the repository
        Supplier supplierFromRepository = new Supplier();
        supplierFromRepository.setId(1);
        supplierFromRepository.setName("Test Supplier");

        when(supplierRepository.save(any(Supplier.class))).thenReturn(supplierFromRepository);

        // Test the service method
        SupplierResponse result = supplierService.save(request);

        // Assertions
        assertNotNull(result);
        assertEquals(supplierFromRepository.getId(), result.getId());
        assertEquals("Test Supplier", result.getName());
    }

    @Test
    public void testSaveSupplierWithEmptyName() {
        SupplierRequest request = new SupplierRequest();
        request.setName(""); // Empty name

        // Test the service method
        assertThrows(EmptyStringException.class, () -> supplierService.save(request));
    }

    @Test
    public void testFindSupplierById() {
        // Mocking the Supplier entity returned from the repository
        Supplier supplierFromRepository = new Supplier();
        supplierFromRepository.setId(1);
        supplierFromRepository.setName("Test Supplier");

        when(supplierRepository.findById(1)).thenReturn(Optional.of(supplierFromRepository));

        // Test the service method
        Supplier result = supplierService.findById(1);

        // Assertions
        assertNotNull(result);
        assertEquals(supplierFromRepository.getId(), result.getId());
        assertEquals("Test Supplier", result.getName());
    }

    @Test
    public void testFindSupplierByIdWithNonExistentId() {
        // Mocking a non-existent supplier ID
        when(supplierRepository.findById(99)).thenReturn(Optional.empty());

        // Test the service method
        assertThrows(ValidationException.class, () -> supplierService.findById(99));
    }

    // Write similar tests for other methods in the SupplierService class

    @Test
    public void testDeleteSupplier() {
        // Mocking an existing supplier ID
        int supplierId = 1;
        when(productService.existsBySupplierId(supplierId)).thenReturn(false);
        doNothing().when(supplierRepository).deleteById(supplierId);
        when(supplierRepository.existsById(supplierId)).thenReturn(true);

        // Test the service method
        SuccessResponse result = supplierService.deleteSupplier(supplierId);

        // Assertions
        assertNotNull(result);
        assertEquals("Supplier successfully deleted.", result.getMessage());
    }

    @Test
    public void testDeleteSupplierWithAttachedToProduct() {
        // Mocking an existing supplier ID attached to a product
        int supplierId = 1;
        when(productService.existsBySupplierId(supplierId)).thenReturn(true);

        // Test the service method
        assertThrows(NotFoundException.class, () -> supplierService.deleteSupplier(supplierId));
    }
}





