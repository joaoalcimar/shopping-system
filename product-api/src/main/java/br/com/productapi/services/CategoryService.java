package br.com.productapi.services;

import br.com.productapi.exceptions.EmptyStringException;
import br.com.productapi.models.dtos.requests.CategoryRequest;
import br.com.productapi.models.dtos.responses.CategoryResponse;
import br.com.productapi.models.entities.Category;
import br.com.productapi.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryResponse save(CategoryRequest request){
        validateCategoryName(request);
        Category category = categoryRepository.save(Category.of(request));
        return CategoryResponse.of(category);
    }

    public void validateCategoryName(CategoryRequest request){
        if(isEmpty(request.getDescription())){
            throw new EmptyStringException("The category description was not informed.");
        }
    }
}
