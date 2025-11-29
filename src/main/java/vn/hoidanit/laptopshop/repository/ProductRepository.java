package vn.hoidanit.laptopshop.repository;

import org.springframework.stereotype.Repository;

import vn.hoidanit.laptopshop.domain.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product save(Product hoidanit);

    Product findById(long id);

    Void deleteById(long id);
}
