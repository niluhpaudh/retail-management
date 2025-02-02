package co.id.niluh.retail.management.repository;

import co.id.niluh.retail.management.entity.auth.Product;
import co.id.niluh.retail.management.entity.auth.TransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionLogRepository extends JpaRepository<TransactionLog, String> {
    @Query(value = "SELECT e.* FROM transaction_log e WHERE DATE(e.transaction_date) between :from and :to", nativeQuery = true)
    List<TransactionLog> findAllByTransactionDateBetween(LocalDate from, LocalDate to);

    @Query(value = "SELECT e.* FROM transaction_log e WHERE e.product_Id = :productId and DATE(e.transaction_date) between :from and :to", nativeQuery = true)
    List<TransactionLog> findAllByProductIdAndTransactionDateBetween(String productId, LocalDate from, LocalDate to);
}
