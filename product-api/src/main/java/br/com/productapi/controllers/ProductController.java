package br.com.productapi.controllers;

import br.com.productapi.models.dtos.requests.ProductRequest;
import br.com.productapi.models.dtos.responses.ProductResponse;
import br.com.productapi.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ProductResponse save(@RequestBody ProductRequest request){
       return productService.save(request);
    }

    @GetMapping
    public List<ProductResponse> findAll(){
        return productService.findByAll();
    }

    @GetMapping("{id}")
    public ProductResponse findById(@PathVariable Integer id){
        return productService.findByIdResponse(id);
    }

    @GetMapping("name/{name}")
    public List<ProductResponse> findByDescription(@PathVariable String name){
        return productService.findByName(name);
    }
}
