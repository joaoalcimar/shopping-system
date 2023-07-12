package br.com.productapi.models.dtos.responses;

import br.com.productapi.models.entities.Supplier;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class SupplierResponse {

    private Integer id;

    private String name;

    public static SupplierResponse of(Supplier supplier){
        SupplierResponse response = new SupplierResponse();
        BeanUtils.copyProperties(supplier, response);
        return response;
    }
}
