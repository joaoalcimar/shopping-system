package br.com.productapi.services;

import br.com.productapi.exceptions.EmptyStringException;
import br.com.productapi.exceptions.NotFoundException;
import br.com.productapi.exceptions.ValidationException;
import br.com.productapi.models.dtos.ProductQuantityDTO;
import br.com.productapi.models.dtos.ProductStockDTO;
import br.com.productapi.models.dtos.requests.ProductCheckStockRequest;
import br.com.productapi.models.dtos.requests.ProductRequest;
import br.com.productapi.models.dtos.responses.ProductResponse;
import br.com.productapi.models.dtos.responses.SalesResponse;
import br.com.productapi.models.dtos.responses.SuccessResponse;
import br.com.productapi.models.entities.Category;
import br.com.productapi.models.entities.Product;
import br.com.productapi.models.entities.Supplier;
import br.com.productapi.repositories.ProductRepository;
import br.com.productapi.sales.client.SalesClient;
import br.com.productapi.sales.dtos.SalesConfirmationDTO;
import br.com.productapi.sales.dtos.responses.SalesProductResponse;
import br.com.productapi.sales.enums.SalesStatus;
import br.com.productapi.sales.rabbitmq.SalesConfirmationSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@Service
public class ProductService {

    private static final Integer ZERO = 0;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private SalesConfirmationSender messageSender;

    @Autowired
    private SalesClient salesClient;

    public ProductResponse save(ProductRequest request){
        validateName(request.getName());
        validateAvailability(request);
        validateCategoryId(request.getCategoryId());
        validateSupplierId(request.getSupplierId());

        Category category = categoryService.findById(request.getCategoryId());
        Supplier supplier = supplierService.findById(request.getSupplierId());

        Product product = productRepository.save(Product.of(request, category, supplier));
        return ProductResponse.of(product);
    }

    protected void validateAvailability(ProductRequest request){
        if(isEmpty(request.getAvailableQuantity())){
            throw new EmptyStringException("The product available quantity must to be informed.");
        }
        if(request.getAvailableQuantity() < ZERO){
            throw new EmptyStringException("The product available quantity should not be lesser than zero.");
        }
    }

    protected void validateName(String name){
        if(isEmpty(name)){
            throw new EmptyStringException("The product name must to be informed.");
        }
    }

    protected void validateIdExistence(Integer id){
        if(!productRepository.existsById(id)){
            throw new NotFoundException("There is no product for the given id.");
        }
    }

    protected void validateCategoryId(Integer categoryId){
        if(isEmpty(categoryId)){
            throw new EmptyStringException("The category id must to be informed.");
        }
    }

    protected void validateSupplierId(Integer supplierId){
        if(isEmpty(supplierId)){
            throw new EmptyStringException("The supplier id must to be informed.");
        }
    }

    protected void validateIdFormat(Integer productId){
        if(isEmpty(productId)){
            throw new ValidationException("The product id must to be informed.");
        }
    }

    public List<ProductResponse> findByName(String name){
        validateName(name);

        return productRepository.findByNameIgnoreCaseContaining(name)
                .stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> findByAll(){
        return productRepository.findAll()
                .stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    public ProductResponse findByIdResponse(Integer id){
        return ProductResponse.of(findById(id));
    }

    public Product findById(Integer id){
        validateIdFormat(id);

        return productRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("There is no product for the given id."));
    }

    public Boolean existsByCategoryId(Integer categoryId){
        return productRepository.existsByCategoryId(categoryId);
    }

    public Boolean existsBySupplierId(Integer supplierId){
        return productRepository.existsBySupplierId(supplierId);
    }

    public SuccessResponse deleteProduct(Integer id){
        validateIdFormat(id);
        validateIdExistence(id);

        productRepository.deleteById(id);

        return SuccessResponse.create("Product successfully deleted.");
    }

    public ProductResponse updateById(ProductRequest request, Integer id) {
        validateIdFormat(id);
        validateIdExistence(id);
        validateName(request.getName());
        validateAvailability(request);
        validateCategoryId(request.getCategoryId());
        validateSupplierId(request.getSupplierId());

        Category category = categoryService.findById(request.getCategoryId());
        Supplier supplier = supplierService.findById(request.getSupplierId());
        Product product = Product.of(request, category, supplier);
        product.setId(id);

        productRepository.save(product);

        return ProductResponse.of(product);
    }

    public void updateProductStock(ProductStockDTO productStock){
        try{
            validateUpdateStockData(productStock);
            updateStock(productStock);

        }catch (Exception e){
            log.error("Error while trying to update stock for message with error: {}", e.getMessage(), e);
            SalesConfirmationDTO rejectedMessage = new SalesConfirmationDTO(productStock.getSalesId(), SalesStatus.REJECTED);
            messageSender.sendSalesConfirmationMessage(rejectedMessage);
        }
    }

    @Transactional
    public void updateStock(ProductStockDTO productStock){
        List<Product> productsToUpdate = new ArrayList<>();

        productStock
                .getProducts()
                .forEach(salesProduct -> {

                    Product existingProduct = findById(salesProduct.getProductId());
                    validateQuantityInStock(salesProduct, existingProduct);
                    existingProduct.updateStock(salesProduct.getQuantity());

                    productsToUpdate.add(existingProduct);
                });

        if(!isEmpty(productsToUpdate)){
            productRepository.saveAll(productsToUpdate);

            String transactionId = productStock.getSalesId();
            SalesConfirmationDTO message = new SalesConfirmationDTO(transactionId, SalesStatus.APPROVED);
            messageSender.sendSalesConfirmationMessage(message);
        }

    }

    protected void validateQuantityInStock(ProductQuantityDTO salesProduct, Product existingProduct){
        if(salesProduct.getQuantity() > existingProduct.getAvailableQuantity()){
            throw new ValidationException(
                    String.format("The product %s is out of stock", existingProduct.getId())
            );
        }
    }

    protected void validateUpdateStockData(ProductStockDTO product){
        if(isEmpty(product) || isEmpty(product.getSalesId())){
            throw new ValidationException("The product data or sales id must be informed.");
        }
        if(isEmpty(product.getProducts())){
            throw new ValidationException("The sales products must be informed.");
        }

        product
                .getProducts()
                .forEach(salesProducts -> {
                    if(isEmpty(salesProducts.getQuantity())
                        || isEmpty(salesProducts.getProductId())){
                        throw new ValidationException("The productId and the quantity must be informed.");
                    }
                });
    }

    public SalesResponse findSalesById(Integer id){
        Product product = findById(id);

        try{
            SalesProductResponse sales = salesClient.findSalesByProductId(id)
                    .orElseThrow(() -> new NotFoundException("The sales associated to this product was not found."));
            return SalesResponse.of(product, sales.getSalesIds());
        }catch (Exception e){
            throw new ValidationException("There is an error trying to get the product sales.");
        }
    }

    public SuccessResponse checkProductsStock(ProductCheckStockRequest request){
        if(isEmpty(request) || isEmpty(request.getProducts())){
            throw new ValidationException("The request data and products must be informed");
        }

        request
                .getProducts()
                .forEach(this::validateStock);

        return SuccessResponse.create("Stock is fine.");
    }

    protected void validateStock(ProductQuantityDTO productQuantity){
        if(isEmpty(productQuantity.getProductId()) || isEmpty(productQuantity.getQuantity())){
            throw new ValidationException("Product id and quantity must be informed.");
        }

        Product product = findById(productQuantity.getProductId());

        if (productQuantity.getQuantity() > product.getAvailableQuantity()){
            throw new ValidationException(String.format("The product %s is out of stock.", product.getId()));
        }


    }
}
