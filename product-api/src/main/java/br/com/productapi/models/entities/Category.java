package br.com.productapi.models.entities;

import br.com.productapi.models.dtos.requests.CategoryRequest;
import br.com.productapi.models.dtos.responses.CategoryResponse;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

@Data
@Entity
@Table(name = "category")
public class Category{

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "description", nullable = false)
    private String description;

    public static Category of(CategoryRequest request){
        Category category = new Category();
        BeanUtils.copyProperties(request, category);
        return category;
    }
}
