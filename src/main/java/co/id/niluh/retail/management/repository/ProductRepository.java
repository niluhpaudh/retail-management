package co.id.niluh.retail.management.repository;

import co.id.niluh.retail.management.entity.auth.Privilege;
import co.id.niluh.retail.management.entity.auth.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Product findTopByProductId(String productId);
}
