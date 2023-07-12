package br.com.productapi.repositories;

import br.com.productapi.models.dtos.responses.ProductResponse;
import br.com.productapi.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByNameIgnoreCaseContaining(String name);

    Boolean existsByCategoryId(Integer id);

    Boolean existsBySupplierId(Integer id);
}
