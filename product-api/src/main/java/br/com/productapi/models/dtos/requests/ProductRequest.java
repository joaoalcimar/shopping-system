package br.com.productapi.models.dtos.requests;

import lombok.Data;

@Data
public class ProductRequest {

    private String name;
    private Integer availableQuantity;
    private Integer categoryId;
    private Integer supplierId;
}
