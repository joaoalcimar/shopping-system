package br.com.productapi.services;

import br.com.productapi.exceptions.EmptyStringException;
import br.com.productapi.exceptions.NotFoundException;
import br.com.productapi.exceptions.ValidationException;
import br.com.productapi.models.dtos.requests.CategoryRequest;
import br.com.productapi.models.dtos.responses.CategoryResponse;
import br.com.productapi.models.dtos.responses.SuccessResponse;
import br.com.productapi.models.entities.Category;
import br.com.productapi.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Lazy
    @Autowired
    private ProductService productService;

    public CategoryResponse save(CategoryRequest request){
        validateCategoryDescription(request.getDescription());
        Category category = categoryRepository.save(Category.of(request));

        return CategoryResponse.of(category);
    }

    protected void validateIdExistence(Integer id){
        if(!categoryRepository.existsById(id)){
            throw new NotFoundException("There is no category for the given id.");
        }
    }

    protected void validateCategoryDescription(String description){
        if(isEmpty(description)){
            throw new EmptyStringException("The category description was not informed.");
        }
    }

    protected void validateIdFormat(Integer id){
        if(isEmpty(id)){
            throw new ValidationException("The category id must to be informed.");
        }
    }

    public Category findById(Integer id){
        validateIdFormat(id);

        return categoryRepository
                .findById(id)
                .orElseThrow(() -> new ValidationException("There is no category for the given id."));
    }

    public List<CategoryResponse> findByDescription(String description){
        validateCategoryDescription(description);

        return categoryRepository.findByDescriptionIgnoreCaseContaining(description)
                .stream()
                .map(CategoryResponse::of)
                .collect(Collectors.toList());
    }

    public List<CategoryResponse> findByAll(){

        return categoryRepository.findAll()
                .stream()
                .map(CategoryResponse::of)
                .collect(Collectors.toList());
    }

    public CategoryResponse findByIdResponse(Integer id){

        return CategoryResponse.of(findById(id));
    }

    public SuccessResponse deleteCategory(Integer id){
        validateIdFormat(id);
        validateIdExistence(id);

        if(productService.existsByCategoryId(id)){
            throw new ValidationException("You cannot delete this category because it's already attached to a product.");
        }

        categoryRepository.deleteById(id);

        return SuccessResponse.create("Category successfully deleted.");
    }

    public CategoryResponse updateById(CategoryRequest request, Integer id) {
        validateIdFormat(id);
        validateIdExistence(id);
        validateCategoryDescription(request.getDescription());

        Category category = Category.of(request);
        category.setId(id);

        categoryRepository.save(category);

        return CategoryResponse.of(category);
    }
}
