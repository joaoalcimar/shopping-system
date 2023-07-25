package br.com.productapi.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

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

    @Before
    public void setup() {
        supplierService = new SupplierService(supplierRepository, productService);
    }

    @Test
    public void testSaveSupplier() {
        SupplierRequest request = new SupplierRequest();
        request.setName("Test Supplier");

        Supplier supplierFromRepository = new Supplier();
        supplierFromRepository.setId(1);
        supplierFromRepository.setName("Test Supplier");

        when(supplierRepository.save(any(Supplier.class))).thenReturn(supplierFromRepository);

        SupplierResponse result = supplierService.save(request);

        assertNotNull(result);
        assertEquals(supplierFromRepository.getId(), result.getId());
        assertEquals("Test Supplier", result.getName());
    }

    @Test
    public void testSaveSupplierWithEmptyName() {
        SupplierRequest request = new SupplierRequest();
        request.setName("");

        assertThrows(EmptyStringException.class, () -> supplierService.save(request));
    }

    @Test
    public void testFindSupplierById() {
        Supplier supplierFromRepository = new Supplier();
        supplierFromRepository.setId(1);
        supplierFromRepository.setName("Test Supplier");

        when(supplierRepository.findById(1)).thenReturn(Optional.of(supplierFromRepository));

        Supplier result = supplierService.findById(1);

        assertNotNull(result);
        assertEquals(supplierFromRepository.getId(), result.getId());
        assertEquals("Test Supplier", result.getName());
    }

    @Test
    public void testFindSupplierByIdWithNonExistentId() {
        when(supplierRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ValidationException.class, () -> supplierService.findById(99));
    }

    @Test
    public void testDeleteSupplier() {
        int supplierId = 1;
        when(productService.existsBySupplierId(supplierId)).thenReturn(false);
        doNothing().when(supplierRepository).deleteById(supplierId);
        when(supplierRepository.existsById(supplierId)).thenReturn(true);

        SuccessResponse result = supplierService.deleteSupplier(supplierId);

        assertNotNull(result);
        assertEquals("Supplier successfully deleted.", result.getMessage());
    }

    @Test
    public void testDeleteSupplierWithAttachedToProduct() {
        int supplierId = 1;
        when(productService.existsBySupplierId(supplierId)).thenReturn(true);

        assertThrows(NotFoundException.class, () -> supplierService.deleteSupplier(supplierId));
    }
}





