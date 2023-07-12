package br.com.productapi.controllers;

import br.com.productapi.models.dtos.requests.CategoryRequest;
import br.com.productapi.models.dtos.responses.CategoryResponse;
import br.com.productapi.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public CategoryResponse save(@RequestBody CategoryRequest request){
        return categoryService.save(request);
    }

    @GetMapping
    public List<CategoryResponse> findAll(){
        return categoryService.findByAll();
    }

    @GetMapping("{id}")
    public CategoryResponse findById(@PathVariable Integer id){
        return categoryService.findByIdResponse(id);
    }

    @GetMapping("description/{description}")
    public List<CategoryResponse> findByDescription(@PathVariable String description){
        return categoryService.findByDescription(description);
    }
}
