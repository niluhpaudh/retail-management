package co.id.niluh.retail.management.service;

import co.id.niluh.retail.management.auth.model.AuditIdentity;
import co.id.niluh.retail.management.entity.auth.Product;

import java.util.List;

public interface ProductService {
	List<Product> getAllProduct();
	void createProduct(Product create, AuditIdentity identity);
	void updateProduct(Product update, AuditIdentity identity);
	void deleteProduct(String productId, AuditIdentity identity);

}
