package br.com.productapi.services;

import br.com.productapi.exceptions.EmptyStringException;
import br.com.productapi.exceptions.ValidationException;
import br.com.productapi.models.dtos.requests.CategoryRequest;
import br.com.productapi.models.dtos.responses.CategoryResponse;
import br.com.productapi.models.dtos.responses.SuccessResponse;
import br.com.productapi.models.entities.Category;
import br.com.productapi.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductService productService;

    public CategoryResponse save(CategoryRequest request){
        validateCategoryDescription(request.getDescription());

        Category category = categoryRepository.save(Category.of(request));
        return CategoryResponse.of(category);
    }

    private void validateCategoryDescription(String description){
        if(isEmpty(description)){
            throw new EmptyStringException("The category description was not informed.");
        }
    }

    private void validateId(Integer id){
        if(isEmpty(id)){
            throw new ValidationException("The category id must to be informed.");
        }
    }

    public Category findById(Integer id){
        validateId(id);

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
        validateId(id);
        if(productService.existsByCategoryId(id)){
            throw new ValidationException("You cannot delete this category because it's already attached to a product.");
        }

        categoryRepository.deleteById(id);
        return SuccessResponse.create("Category successfully deleted.");
    }

}
