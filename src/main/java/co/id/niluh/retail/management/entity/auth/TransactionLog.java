package co.id.niluh.retail.management.entity.auth;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import co.id.niluh.retail.management.entity.AuditingEntity;
import co.id.niluh.retail.management.enumz.TypeTransaction;
import com.hazelcast.transaction.TransactionOptions;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "transaction_log")
public class TransactionLog extends AuditingEntity {

    private static final long serialVersionUID = -5653310005425830452L;

    @Id
    @Column(name = "tx_id", updatable = false, nullable = false, length = 36)
    private UUID trxId;

    @Column(name = "type_trx", length = 50)
    @Enumerated(EnumType.STRING)
    private TypeTransaction typeTrx;

    @NotNull
    @Column(name = "amount")
    private Integer amount;

    @Column(name = "product_id")
    @Length(max = 50)
    private String productId;

    @Column(name = "transaction_date", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime transactionDate;

    // Getters and Setters
    public UUID getTrxId() {
        return trxId;
    }

    public void setTrxId(UUID trxId) {
        this.trxId = trxId;
    }

    public TypeTransaction getTypeTrx() {
        return typeTrx;
    }

    public void setTypeTrx(TypeTransaction typeTrx) {
        this.typeTrx = typeTrx;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
}
