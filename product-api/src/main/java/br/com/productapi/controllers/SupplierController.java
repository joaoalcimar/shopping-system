package br.com.productapi.controllers;

import br.com.productapi.models.dtos.requests.SupplierRequest;
import br.com.productapi.models.dtos.responses.SuccessResponse;
import br.com.productapi.models.dtos.responses.SupplierResponse;
import br.com.productapi.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @PostMapping
    public SupplierResponse save(@RequestBody SupplierRequest request){
        return supplierService.save(request);
    }

    @GetMapping
    public List<SupplierResponse> findAll(){
        return supplierService.findByAll();
    }

    @GetMapping("{id}")
    public SupplierResponse findById(@PathVariable Integer id){
        return supplierService.findByIdResponse(id);
    }

    @DeleteMapping("{id}")
    public SuccessResponse deleteById(@PathVariable Integer id){
        return supplierService.deleteSupplier(id);
    }

    @PutMapping("{id}")
    public SupplierResponse updateById(@RequestBody SupplierRequest request, @PathVariable Integer id){
        return supplierService.updateById(request, id);
    }

    @GetMapping("name/{name}")
    public List<SupplierResponse> findByDescription(@PathVariable String name){
        return supplierService.findByName(name);
    }
}
