package br.com.productapi.models.dtos.responses;

import br.com.productapi.models.entities.Supplier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SupplierResponse {

    private Integer id;

    private String name;

    public static SupplierResponse of(Supplier supplier){
        SupplierResponse response = new SupplierResponse();
        BeanUtils.copyProperties(supplier, response);
        return response;
    }
}
