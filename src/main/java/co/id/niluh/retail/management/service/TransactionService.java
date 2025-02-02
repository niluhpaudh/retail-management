package co.id.niluh.retail.management.service;

import co.id.niluh.retail.management.auth.model.AuditIdentity;
import co.id.niluh.retail.management.entity.auth.Product;
import co.id.niluh.retail.management.entity.auth.TransactionLog;
import co.id.niluh.retail.management.model.TransactionLogModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {
	List<TransactionLog> getAllTransaction();

	List<TransactionLog> getTransactionList(LocalDate startDate, LocalDate endDate, String productId);

	void doTransaction(TransactionLogModel trx, AuditIdentity identity);

}
