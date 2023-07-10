package br.com.productapi.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "available_quantity", nullable = false)
    private Integer availableQuantity;

    @ManyToOne
    @JoinColumn(name = "fk_category", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "fk_supplier", nullable = false)
    private Supplier supplier;
}
