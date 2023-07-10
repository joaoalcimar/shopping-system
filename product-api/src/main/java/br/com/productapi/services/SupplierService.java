package br.com.productapi.services;

import br.com.productapi.exceptions.EmptyStringException;
import br.com.productapi.models.dtos.requests.SupplierRequest;
import br.com.productapi.models.dtos.responses.SupplierResponse;
import br.com.productapi.models.entities.Supplier;
import br.com.productapi.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public SupplierResponse save(SupplierRequest request){

        validateSupplierName(request);
        Supplier supplier = supplierRepository.save(Supplier.of(request));
        return SupplierResponse.of(supplier);
    }

    private void validateSupplierName(SupplierRequest request){

        if(isEmpty(request.getName())){
            throw new EmptyStringException("The supplier name was not informed.");
        }
    }

    public Supplier findById(Integer id) throws ValidationException {

        return supplierRepository
                .findById(id)
                .orElseThrow(() -> new ValidationException("There is no supplier for the given id."));
    }
}
