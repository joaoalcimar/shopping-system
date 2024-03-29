package br.com.productapi.controllers;

import br.com.productapi.models.dtos.requests.ProductCheckStockRequest;
import br.com.productapi.models.dtos.requests.ProductRequest;
import br.com.productapi.models.dtos.responses.ProductResponse;
import br.com.productapi.models.dtos.responses.SalesResponse;
import br.com.productapi.models.dtos.responses.SuccessResponse;
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

    @DeleteMapping("{id}")
    public SuccessResponse deleteById(@PathVariable Integer id){
        return productService.deleteProduct(id);
    }

    @PutMapping("{id}")
    public ProductResponse updateById(@RequestBody ProductRequest request, @PathVariable Integer id){
        return productService.updateById(request, id);
    }

    @GetMapping("name/{name}")
    public List<ProductResponse> findByDescription(@PathVariable String name){
        return productService.findByName(name);
    }

    @GetMapping("{id}/sales")
    public SalesResponse findProductSales(@PathVariable Integer id){
        return productService.findSalesById(id);
    }

    @PostMapping("check-stock")
    public SuccessResponse checkProductStock(@RequestBody ProductCheckStockRequest request){
        return productService.checkProductsStock(request);
    }

}
