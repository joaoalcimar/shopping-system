package br.com.productapi.services;

import br.com.productapi.exceptions.EmptyStringException;
import br.com.productapi.exceptions.ValidationException;
import br.com.productapi.models.dtos.requests.ProductRequest;
import br.com.productapi.models.dtos.responses.ProductResponse;
import br.com.productapi.models.dtos.responses.SupplierResponse;
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
            throw new EmptyStringException("The product available quantity was not informed");
        }
        if(request.getAvailableQuantity() < ZERO){
            throw new EmptyStringException("The product available quantity should not be lesser than zero");
        }
    }

    private void validateName(String name){
        if(isEmpty(name)){
            throw new EmptyStringException("The product name was not informed");
        }
    }

    private void validateCategoryId(Integer categoryId){
        if(isEmpty(categoryId)){
            throw new EmptyStringException("The category id was not informed");
        }
    }

    private void validateSupplierId(Integer supplierId){
        if(isEmpty(supplierId)){
            throw new EmptyStringException("The supplier id was not informed");
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
        return productRepository
                .findById(id)
                .orElseThrow(() -> new ValidationException("There is no product for the given id."));
    }

}
