package br.com.productapi.models.dtos.requests;

import br.com.productapi.models.dtos.ProductQuantityDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductCheckStockRequest {

    List<ProductQuantityDTO> products;
}
