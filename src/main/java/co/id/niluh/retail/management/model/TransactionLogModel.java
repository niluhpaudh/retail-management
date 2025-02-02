package co.id.niluh.retail.management.model;

import co.id.niluh.retail.management.entity.AuditingEntity;
import co.id.niluh.retail.management.enumz.TypeTransaction;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionLogModel{

    private TypeTransaction typeTrx;
    private Integer amount;

    private String productId;

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
}
