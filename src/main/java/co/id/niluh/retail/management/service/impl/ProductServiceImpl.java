package co.id.niluh.retail.management.service.impl;

import co.id.niluh.retail.management.auth.model.AuditIdentity;
import co.id.niluh.retail.management.entity.auth.Product;
import co.id.niluh.retail.management.entity.auth.User;
import co.id.niluh.retail.management.enumz.ErrorCodes;
import co.id.niluh.retail.management.enumz.UserStatus;
import co.id.niluh.retail.management.repository.ProductRepository;
import co.id.niluh.retail.management.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@SuppressWarnings("unchecked")
public class ProductServiceImpl implements ProductService {
	@Autowired
	ProductRepository productRepository;


	@Override
	public List<Product> getAllProduct() {
		return productRepository.findAll();
	}


	@Override
	public void createProduct(Product create, AuditIdentity identity) {
		if (productRepository.findById(create.getProductId()).isPresent()){
			throw new RuntimeException("Produk sudah ada");
		}
		create.setCreatedBy(identity.getUserName());
		create.setCreatedDate(LocalDateTime.now());
		productRepository.save(create);
	}

	@Override
	public void updateProduct(Product update, AuditIdentity identity) {
		if (!productRepository.findById(update.getProductId()).isPresent()){
			throw new RuntimeException(ErrorCodes.PRODUCT_NOT_FOUND.getDefaultMessage());
		}
		update.setUpdatedBy(identity.getUserName());
		update.setUpdatedDate(LocalDateTime.now());
		productRepository.save(update);
	}

	@Override
	public void deleteProduct(String productId, AuditIdentity identity) {
		Product product = productRepository.findById(productId).
				orElseThrow(() -> new RuntimeException(ErrorCodes.USER_NOT_FOUND.getDefaultMessage()));
		productRepository.delete(product);
	}
}
