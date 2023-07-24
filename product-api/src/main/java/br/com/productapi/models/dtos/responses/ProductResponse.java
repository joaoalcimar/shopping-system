package br.com.productapi.models.dtos.responses;

import br.com.productapi.models.entities.Product;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private Integer id;
    private String name;
    private Integer availableQuantity;
    private CategoryResponse categoryResponse;
    private SupplierResponse supplierResponse;

    @JsonProperty("created_at")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;

    public static ProductResponse of(Product product){
        return ProductResponse
                .builder()
                .id(product.getId())
                .name(product.getName())
                .availableQuantity(product.getAvailableQuantity())
                .categoryResponse(CategoryResponse.of(product.getCategory()))
                .supplierResponse(SupplierResponse.of(product.getSupplier()))
                .createdAt(product.getCreatedAt())
                .build();
    }
}
