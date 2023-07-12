package br.com.productapi.models.entities;

import br.com.productapi.models.dtos.requests.SupplierRequest;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

@Data
@Entity
@Table(name = "supplier")
public class Supplier {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    public static Supplier of(SupplierRequest request){
        Supplier supplier = new Supplier();
        BeanUtils.copyProperties(request, supplier);
        return supplier;
    }
}
