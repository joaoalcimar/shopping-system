package br.com.productapi.services;

import br.com.productapi.exceptions.EmptyStringException;
import br.com.productapi.exceptions.ValidationException;
import br.com.productapi.models.dtos.requests.SupplierRequest;
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

    public Supplier findById(Integer id){
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
}
