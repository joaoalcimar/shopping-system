package br.com.productapi.models.dtos.responses;

import br.com.productapi.models.entities.Category;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class CategoryResponse {
    private Integer id;

    private String description;

    public static CategoryResponse of(Category category){
        CategoryResponse response = new CategoryResponse();
        BeanUtils.copyProperties(category, response);
        return response;
    }
}
