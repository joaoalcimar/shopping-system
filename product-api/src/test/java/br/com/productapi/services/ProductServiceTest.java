package br.com.productapi.services;

import br.com.productapi.exceptions.EmptyStringException;
import br.com.productapi.exceptions.NotFoundException;
import br.com.productapi.exceptions.ValidationException;
import br.com.productapi.models.dtos.ProductQuantityDTO;
import br.com.productapi.models.dtos.ProductStockDTO;
import br.com.productapi.models.dtos.requests.ProductCheckStockRequest;
import br.com.productapi.models.dtos.requests.ProductRequest;
import br.com.productapi.models.dtos.responses.ProductResponse;
import br.com.productapi.models.dtos.responses.SuccessResponse;
import br.com.productapi.models.entities.Category;
import br.com.productapi.models.entities.Product;
import br.com.productapi.models.entities.Supplier;
import br.com.productapi.repositories.ProductRepository;
import br.com.productapi.sales.client.SalesClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository mockProductRepository;

    @Mock
    private CategoryService mockCategoryService;

    @Mock
    private SupplierService mockSupplierService;

    @InjectMocks
    private ProductService productService;

    @Mock
    private SalesClient mockSalesClient;

    @Test
    public void testSaveProduct_Successful() {
        // Given
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("Test Product");
        productRequest.setAvailableQuantity(10);
        productRequest.setCategoryId(1);
        productRequest.setSupplierId(1);

        Category category = new Category();
        category.setId(1);
        category.setDescription("Test Category");

        Supplier supplier = new Supplier();
        supplier.setId(1);
        supplier.setName("Test Supplier");

        Product savedProduct = new Product();
        savedProduct.setId(1);
        savedProduct.setName("Test Product");
        savedProduct.setAvailableQuantity(10);
        savedProduct.setCategory(category);
        savedProduct.setSupplier(supplier);

        when(mockCategoryService.findById(1)).thenReturn(category);
        when(mockSupplierService.findById(1)).thenReturn(supplier);
        when(mockProductRepository.save(Mockito.any(Product.class))).thenReturn(savedProduct);

        ProductResponse productResponse = productService.save(productRequest);

        assertNotNull(productResponse);
        assertEquals(savedProduct.getId(), productResponse.getId());
        assertEquals(savedProduct.getName(), productResponse.getName());
        assertEquals(savedProduct.getAvailableQuantity(), productResponse.getAvailableQuantity());
        assertEquals(savedProduct.getCategory().getId(), productResponse.getCategoryResponse().getId());
        assertEquals(savedProduct.getSupplier().getId(), productResponse.getSupplierResponse().getId());

        Mockito.verify(mockCategoryService, Mockito.times(1)).findById(1);
        Mockito.verify(mockSupplierService, Mockito.times(1)).findById(1);
        Mockito.verify(mockProductRepository, Mockito.times(1)).save(Mockito.any(Product.class));
    }

    @Test(expected = EmptyStringException.class)
    public void testSaveProduct_EmptyName_ExceptionThrown() {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setAvailableQuantity(10);
        productRequest.setCategoryId(1);
        productRequest.setSupplierId(1);

        productService.save(productRequest);
    }

    @Test(expected = EmptyStringException.class)
    public void testSaveProduct_NullAvailableQuantity_ExceptionThrown() {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("Test Product");
        productRequest.setCategoryId(1);
        productRequest.setSupplierId(1);

        productService.save(productRequest);
    }

    @Test(expected = EmptyStringException.class)
    public void testFindByName_EmptyName_ExceptionThrown() {
        String productName = "";

        productService.findByName(productName);
    }

    @Test
    public void testFindById_Successful() {
        Integer productId = 1;

        Product product = new Product();
        product.setId(productId);
        product.setName("Test Product");
        product.setAvailableQuantity(10);
        product.setCategory(new Category(1, "Test Category"));
        product.setSupplier(new Supplier(1, "Test Supplier"));

        Mockito.when(mockProductRepository.findById(productId))
                .thenReturn(Optional.of(product));

        Product actualProduct = productService.findById(productId);

        assertNotNull(actualProduct);
        assertEquals(productId, actualProduct.getId());
        assertEquals(product.getName(), actualProduct.getName());
        assertEquals(product.getAvailableQuantity(), actualProduct.getAvailableQuantity());
        assertEquals(product.getCategory().getId(), actualProduct.getCategory().getId());
        assertEquals(product.getSupplier().getId(), actualProduct.getSupplier().getId());

        Mockito.verify(mockProductRepository, Mockito.times(1))
                .findById(productId);
    }

    @Test(expected = NotFoundException.class)
    public void testFindById_ProductNotFound_ExceptionThrown() {
        int productId = 1;

        Mockito.when(mockProductRepository.findById(productId))
                .thenReturn(Optional.empty());

        productService.findById(productId);
    }

    @Test(expected = ValidationException.class)
    public void testFindById_InvalidProductId_ExceptionThrown() {
        Integer productId = null;

        productService.findById(productId);
    }

    @Test
    public void testExistsByCategoryId_CategoryExists_ReturnsTrue() {
        int categoryId = 1;

        Mockito.when(mockProductRepository.existsByCategoryId(categoryId))
                .thenReturn(true);

        boolean result = productService.existsByCategoryId(categoryId);

        assertTrue(result);
        Mockito.verify(mockProductRepository, Mockito.times(1))
                .existsByCategoryId(categoryId);
    }

    @Test
    public void testExistsByCategoryId_CategoryDoesNotExist_ReturnsFalse() {
        int categoryId = 1;

        Mockito.when(mockProductRepository.existsByCategoryId(categoryId))
                .thenReturn(false);

        boolean result = productService.existsByCategoryId(categoryId);

        assertFalse(result);
        Mockito.verify(mockProductRepository, Mockito.times(1))
                .existsByCategoryId(categoryId);
    }

    @Test
    public void testExistsBySupplierId_SupplierExists_ReturnsTrue() {
        int supplierId = 1;

        Mockito.when(mockProductRepository.existsBySupplierId(supplierId))
                .thenReturn(true);

        boolean result = productService.existsBySupplierId(supplierId);

        assertTrue(result);
        Mockito.verify(mockProductRepository, Mockito.times(1))
                .existsBySupplierId(supplierId);
    }

    @Test
    public void testExistsBySupplierId_SupplierDoesNotExist_ReturnsFalse() {
        int supplierId = 1;

        Mockito.when(mockProductRepository.existsBySupplierId(supplierId))
                .thenReturn(false);

        boolean result = productService.existsBySupplierId(supplierId);

        assertFalse(result);
        Mockito.verify(mockProductRepository, Mockito.times(1))
                .existsBySupplierId(supplierId);
    }


    @Test
    public void testValidateIdExistence_ProductExists_NoExceptionThrown() {
        int productId = 1;

        Mockito.when(mockProductRepository.existsById(productId))
                .thenReturn(true);

        productService.validateIdExistence(productId);
    }

    @Test(expected = NotFoundException.class)
    public void testValidateIdExistence_ProductDoesNotExist_ExceptionThrown() {
        int productId = 1;

        Mockito.when(mockProductRepository.existsById(productId))
                .thenReturn(false);

        productService.validateIdExistence(productId);

    }

    @Test(expected = NotFoundException.class)
    public void testDeleteProduct_ProductDoesNotExist_ExceptionThrown() {
        int productId = 1;

        productService.deleteProduct(productId);
    }

    @Test
    public void testValidateQuantityInStock_ProductInStock() {
        ProductQuantityDTO productQuantity = new ProductQuantityDTO();
        productQuantity.setProductId(1);
        productQuantity.setQuantity(5);

        Product existingProduct = new Product();
        existingProduct.setId(1);
        existingProduct.setName("Product 1");
        existingProduct.setAvailableQuantity(10);

        productService.validateQuantityInStock(productQuantity, existingProduct);
    }

    @Test(expected = ValidationException.class)
    public void testValidateQuantityInStock_ProductOutOfStock() {
        ProductQuantityDTO productQuantity = new ProductQuantityDTO();
        productQuantity.setProductId(1);
        productQuantity.setQuantity(15);

        Product existingProduct = new Product();
        existingProduct.setId(1);
        existingProduct.setName("Product 1");
        existingProduct.setAvailableQuantity(10);

        productService.validateQuantityInStock(productQuantity, existingProduct);
    }

    @Test
    public void testValidateUpdateStockData_ValidData() {
        ProductStockDTO productStock = new ProductStockDTO();
        productStock.setSalesId("transactionId");

        ProductQuantityDTO productQuantity1 = new ProductQuantityDTO();
        productQuantity1.setProductId(1);
        productQuantity1.setQuantity(5);

        ProductQuantityDTO productQuantity2 = new ProductQuantityDTO();
        productQuantity2.setProductId(2);
        productQuantity2.setQuantity(10);

        productStock.setProducts(Arrays.asList(productQuantity1, productQuantity2));

        productService.validateUpdateStockData(productStock);

    }

    @Test(expected = ValidationException.class)
    public void testValidateUpdateStockData_NullProduct() {
        ProductStockDTO productStock = null;

        productService.validateUpdateStockData(productStock);
    }

    @Test(expected = ValidationException.class)
    public void testValidateUpdateStockData_NullSalesId() {
        ProductStockDTO productStock = new ProductStockDTO();
        productStock.setSalesId(null);

        productService.validateUpdateStockData(productStock);

    }

    @Test(expected = ValidationException.class)
    public void testValidateUpdateStockData_EmptySalesId() {
        ProductStockDTO productStock = new ProductStockDTO();
        productStock.setSalesId("");

        productService.validateUpdateStockData(productStock);
    }

    @Test(expected = ValidationException.class)
    public void testValidateUpdateStockData_NullProducts() {
        ProductStockDTO productStock = new ProductStockDTO();
        productStock.setSalesId("transactionId");
        productStock.setProducts(null); // Null products

        productService.validateUpdateStockData(productStock);
    }

    @Test(expected = ValidationException.class)
    public void testValidateUpdateStockData_EmptyProductsList() {
        ProductStockDTO productStock = new ProductStockDTO();
        productStock.setSalesId("transactionId");
        productStock.setProducts(Collections.emptyList());

        productService.validateUpdateStockData(productStock);
    }

    @Test(expected = ValidationException.class)
    public void testValidateUpdateStockData_InvalidProductId() {
        ProductStockDTO productStock = new ProductStockDTO();
        productStock.setSalesId("transactionId");

        ProductQuantityDTO productQuantity1 = new ProductQuantityDTO();
        productQuantity1.setProductId(null);
        productQuantity1.setQuantity(5);

        productStock.setProducts(Collections.singletonList(productQuantity1));

        productService.validateUpdateStockData(productStock);
    }

    @Test(expected = ValidationException.class)
    public void testValidateUpdateStockData_NullQuantity() {
        ProductStockDTO productStock = new ProductStockDTO();
        productStock.setSalesId("transactionId");

        ProductQuantityDTO productQuantity1 = new ProductQuantityDTO();
        productQuantity1.setProductId(1);
        productQuantity1.setQuantity(null);

        productStock.setProducts(Collections.singletonList(productQuantity1));

        productService.validateUpdateStockData(productStock);
    }

    @Test(expected = NotFoundException.class)
    public void testFindSalesById_ProductNotFound() {
        Mockito.when(mockProductRepository.findById(1)).thenReturn(Optional.empty());

        productService.findSalesById(1);
    }

    @Test(expected = ValidationException.class)
    public void testFindSalesById_SalesClientError() {
        Product product = new Product();
        product.setId(1);
        product.setName("Laptop");
        product.setAvailableQuantity(10);

        Mockito.when(mockProductRepository.findById(1)).thenReturn(Optional.of(product));
        Mockito.when(mockSalesClient.findSalesByProductId(1)).thenThrow(new RuntimeException("Sales client error"));

        productService.findSalesById(1);
    }

    @Test
    public void testCheckProductsStock_Successful() {
        ProductQuantityDTO product1 = new ProductQuantityDTO(1, 5);
        ProductQuantityDTO product2 = new ProductQuantityDTO(2, 10);
        List<ProductQuantityDTO> products = Arrays.asList(product1, product2);

        Product product1Entity = new Product();
        product1Entity.setId(1);
        product1Entity.setName("Laptop");
        product1Entity.setAvailableQuantity(10);

        Product product2Entity = new Product();
        product2Entity.setId(2);
        product2Entity.setName("Phone");
        product2Entity.setAvailableQuantity(15);

        Mockito.when(mockProductRepository.findById(1)).thenReturn(Optional.of(product1Entity));
        Mockito.when(mockProductRepository.findById(2)).thenReturn(Optional.of(product2Entity));

        ProductCheckStockRequest request = new ProductCheckStockRequest();
        request.setProducts(products);

        SuccessResponse successResponse = productService.checkProductsStock(request);

        assertEquals("Stock is fine.", successResponse.getMessage());
    }

    @Test(expected = ValidationException.class)
    public void testCheckProductsStock_RequestIsNull() {
        productService.checkProductsStock(null);
    }

    @Test(expected = ValidationException.class)
    public void testCheckProductsStock_ProductListIsEmpty() {
        ProductCheckStockRequest request = new ProductCheckStockRequest();
        request.setProducts(Collections.emptyList());

        productService.checkProductsStock(request);
    }

    @Test(expected = ValidationException.class)
    public void testCheckProductsStock_InvalidProductData() {
        ProductQuantityDTO product1 = new ProductQuantityDTO(1, 5);
        ProductQuantityDTO product2 = new ProductQuantityDTO(null, 10); // Invalid product data
        List<ProductQuantityDTO> products = Arrays.asList(product1, product2);

        Product product1Entity = new Product();
        product1Entity.setId(1);
        product1Entity.setName("Laptop");
        product1Entity.setAvailableQuantity(10);

        Mockito.when(mockProductRepository.findById(1)).thenReturn(Optional.of(product1Entity));

        ProductCheckStockRequest request = new ProductCheckStockRequest();
        request.setProducts(products);

        productService.checkProductsStock(request);
    }

    @Test(expected = ValidationException.class)
    public void testCheckProductsStock_OutOfStock() {
        final Integer QUANTITY_AVAILABLE = 15;
        final Integer QUANTITY_ORDERED = 10;
        ProductQuantityDTO product1 = new ProductQuantityDTO(1, QUANTITY_AVAILABLE);
        List<ProductQuantityDTO> products = Collections.singletonList(product1);

        Product product1Entity = new Product();
        product1Entity.setId(1);
        product1Entity.setName("Laptop");
        product1Entity.setAvailableQuantity(QUANTITY_ORDERED);

        Mockito.when(mockProductRepository.findById(1)).thenReturn(Optional.of(product1Entity));

        ProductCheckStockRequest request = new ProductCheckStockRequest();
        request.setProducts(products);

        productService.checkProductsStock(request);
    }

    @Test
    public void testValidateStock_Successful() {
        ProductQuantityDTO productQuantity = new ProductQuantityDTO(1, 5);

        Product productEntity = new Product();
        productEntity.setId(1);
        productEntity.setName("Laptop");
        productEntity.setAvailableQuantity(10);

        Mockito.when(mockProductRepository.findById(1)).thenReturn(Optional.of(productEntity));

        productService.validateStock(productQuantity);
    }

    @Test(expected = ValidationException.class)
    public void testValidateStock_ProductIdIsNull() {
        ProductQuantityDTO productQuantity = new ProductQuantityDTO(null, 5); // Invalid product data

        productService.validateStock(productQuantity);
    }

    @Test(expected = ValidationException.class)
    public void testValidateStock_QuantityIsNull() {
        ProductQuantityDTO productQuantity = new ProductQuantityDTO(1, null); // Invalid product data

        productService.validateStock(productQuantity);
    }

    @Test(expected = ValidationException.class)
    public void testValidateStock_OutOfStock() {
        final Integer QUANTITY_AVAILABLE = 15;
        final Integer QUANTITY_ORDERED = 10;
        ProductQuantityDTO productQuantity = new ProductQuantityDTO(1, QUANTITY_AVAILABLE);

        Product productEntity = new Product();
        productEntity.setId(1);
        productEntity.setName("Laptop");
        productEntity.setAvailableQuantity(QUANTITY_ORDERED);

        Mockito.when(mockProductRepository.findById(1)).thenReturn(Optional.of(productEntity));

        productService.validateStock(productQuantity);
    }
}