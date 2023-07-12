package br.com.productapi.services;

import br.com.productapi.exceptions.EmptyStringException;
import br.com.productapi.exceptions.ValidationException;
import br.com.productapi.models.dtos.requests.SupplierRequest;
import br.com.productapi.models.dtos.responses.SuccessResponse;
import br.com.productapi.models.dtos.responses.SupplierResponse;
import br.com.productapi.models.entities.Supplier;
import br.com.productapi.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ProductService productService;

    public SupplierResponse save(SupplierRequest request){

        validateSupplierName(request.getName());
        Supplier supplier = supplierRepository.save(Supplier.of(request));
        return SupplierResponse.of(supplier);
    }

    private void validateSupplierName(String name){
        if(isEmpty(name)){
            throw new EmptyStringException("The supplier name was not informed.");
        }
    }

    private void validateId(Integer id){
        if(isEmpty(id)){
            throw new ValidationException("The supplier id must to be informed.");
        }
    }

    public Supplier findById(Integer id){
        validateId(id);
        return supplierRepository
                .findById(id)
                .orElseThrow(() -> new ValidationException("There is no supplier for the given id."));
    }

    public List<SupplierResponse> findByName(String name){
        validateSupplierName(name);

        return supplierRepository.findByNameIgnoreCaseContaining(name)
                .stream()
                .map(SupplierResponse::of)
                .collect(Collectors.toList());
    }

    public List<SupplierResponse> findByAll(){
        return supplierRepository.findAll()
                .stream()
                .map(SupplierResponse::of)
                .collect(Collectors.toList());
    }

    public SupplierResponse findByIdResponse(Integer id){
        return SupplierResponse.of(findById(id));
    }

    public SuccessResponse deleteSupplier(Integer id){
        validateId(id);
        if(productService.existsBySupplierId(id)){
            throw new ValidationException("You cannot delete this supplier because it's already attached to a product.");
        }

        supplierRepository.deleteById(id);
        return SuccessResponse.create("Supplier successfully deleted.");
    }

}
