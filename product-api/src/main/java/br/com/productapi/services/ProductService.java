package br.com.productapi.services;

import br.com.productapi.exceptions.EmptyStringException;
import br.com.productapi.models.dtos.requests.ProductRequest;
import br.com.productapi.models.dtos.responses.ProductResponse;
import br.com.productapi.models.entities.Category;
import br.com.productapi.models.entities.Product;
import br.com.productapi.models.entities.Supplier;
import br.com.productapi.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;

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

    public ProductResponse save(ProductRequest request) throws ValidationException {
        validateProductData(request);
        validateCategoryId(request);
        validateSupplierId(request);

        Category category = categoryService.findById(request.getCategoryId());
        Supplier supplier = supplierService.findById(request.getCategoryId());

        Product product = productRepository.save(Product.of(request, category, supplier));
        return ProductResponse.of(product);
    }

    private void validateProductData(ProductRequest request){
        if(isEmpty(request.getName())){
            throw new EmptyStringException("The product name was not informed");
        }
        if(isEmpty(request.getAvailableQuantity())){
            throw new EmptyStringException("The product available quantity was not informed");
        }
        if(request.getAvailableQuantity() < 0){
            throw new EmptyStringException("The product available quantity should not be lesser than zero");
        }
    }

    private void validateCategoryId(ProductRequest request){
        if(isEmpty(request.getCategoryId())){
            throw new EmptyStringException("The category id was not informed");
        }
    }

    private void validateSupplierId(ProductRequest request){
        if(isEmpty(request.getSupplierId())){
            throw new EmptyStringException("The supplier id was not informed");
        }
    }

}
