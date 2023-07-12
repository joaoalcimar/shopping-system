package br.com.productapi.services;

import br.com.productapi.exceptions.EmptyStringException;
import br.com.productapi.exceptions.NotFoundException;
import br.com.productapi.exceptions.ValidationException;
import br.com.productapi.models.dtos.ProductStockDTO;
import br.com.productapi.models.dtos.requests.ProductRequest;
import br.com.productapi.models.dtos.responses.ProductResponse;
import br.com.productapi.models.dtos.responses.SuccessResponse;
import br.com.productapi.models.entities.Category;
import br.com.productapi.models.entities.Product;
import br.com.productapi.models.entities.Supplier;
import br.com.productapi.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class ProductService {

    private static final Integer ZERO = 0;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SupplierService supplierService;

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

    private void validateAvailability(ProductRequest request){
        if(isEmpty(request.getAvailableQuantity())){
            throw new EmptyStringException("The product available quantity must to be informed.");
        }
        if(request.getAvailableQuantity() < ZERO){
            throw new EmptyStringException("The product available quantity should not be lesser than zero.");
        }
    }

    private void validateName(String name){
        if(isEmpty(name)){
            throw new EmptyStringException("The product name must to be informed.");
        }
    }

    private void validateIdExistence(Integer id){
        if(!productRepository.existsById(id)){
            throw new NotFoundException("There is no product for the given id.");
        }
    }

    private void validateCategoryId(Integer categoryId){
        if(isEmpty(categoryId)){
            throw new EmptyStringException("The category id must to be informed.");
        }
    }

    private void validateSupplierId(Integer supplierId){
        if(isEmpty(supplierId)){
            throw new EmptyStringException("The supplier id must to be informed.");
        }
    }

    private void validateIdFormat(Integer productId){
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
                .orElseThrow(() -> new ValidationException("There is no product for the given id."));
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

    public void updateProductStock(ProductStockDTO productStockDTO){

    }
}
