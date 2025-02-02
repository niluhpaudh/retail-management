package co.id.niluh.retail.management.service.impl;

import co.id.niluh.retail.management.auth.model.AuditIdentity;
import co.id.niluh.retail.management.entity.auth.Product;
import co.id.niluh.retail.management.entity.auth.TransactionLog;
import co.id.niluh.retail.management.enumz.ErrorCodes;
import co.id.niluh.retail.management.enumz.TypeTransaction;
import co.id.niluh.retail.management.model.TransactionLogModel;
import co.id.niluh.retail.management.repository.ProductRepository;
import co.id.niluh.retail.management.repository.TransactionLogRepository;
import co.id.niluh.retail.management.service.ProductService;
import co.id.niluh.retail.management.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@SuppressWarnings("unchecked")
public class TransactionServiceImpl implements TransactionService {
	@Autowired
	TransactionLogRepository transactionLogRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	ProductService productService;

	@Override
	public List<TransactionLog> getAllTransaction() {
		return transactionLogRepository.findAll();
	}

	@Override
	public List<TransactionLog> getTransactionList(LocalDate startDate, LocalDate endDate, String productId) {
		if(startDate.isAfter(endDate)){
			throw new RuntimeException("Tanggal akhir tiddak boleh lebih kecil dari tanggal mulai");
		}
		if(productId == null || productId.equals("")){
			return transactionLogRepository.findAllByTransactionDateBetween(startDate,endDate);
		} else if(productId.isEmpty() || productId == ""){
			return transactionLogRepository.findAllByTransactionDateBetween(startDate,endDate);
		} else {
			return transactionLogRepository.findAllByProductIdAndTransactionDateBetween(productId,startDate,endDate);
		}

	}


	@Override
	public void doTransaction(TransactionLogModel trx, AuditIdentity identity) {
		Product product = productRepository.findTopByProductId(trx.getProductId());

		Integer updatedStock = 0;
		if(trx.getTypeTrx().equals(TypeTransaction.REFUND)){
			updatedStock = product.getStock()+ trx.getAmount();
			product.setStock(updatedStock);
			productService.updateProduct(product,AuditIdentity.get());
		} else {
			if(product.getStock()<=0){
				throw new RuntimeException(ErrorCodes.OUT_OF_STOCK.getDefaultMessage());
			}
			updatedStock = product.getStock()+ trx.getAmount();
			product.setStock(updatedStock);
			productService.updateProduct(product,AuditIdentity.get());
		}
		TransactionLog trxLog = new TransactionLog();
		trxLog.setTrxId(UUID.randomUUID());
		trxLog.setProductId(trx.getProductId());
		trxLog.setTypeTrx(trx.getTypeTrx());
		trxLog.setAmount(trx.getAmount());
		trxLog.setTransactionDate(LocalDateTime.now());
		trxLog.setCreatedBy(identity.getUserName());
		trxLog.setCreatedDate(LocalDateTime.now());
		transactionLogRepository.save(trxLog);
	}
}
