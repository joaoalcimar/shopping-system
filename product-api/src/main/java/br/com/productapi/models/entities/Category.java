package br.com.productapi.models.entities;

import br.com.productapi.models.dtos.requests.CategoryRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
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
