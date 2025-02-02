package co.id.niluh.retail.management.entity.auth;

import co.id.niluh.retail.management.entity.AuditingEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "mst_product")
public class Product extends AuditingEntity {

    /**
     *
     */
    private static final long serialVersionUID = -5653310005425830452L;

    @Id
    @Column(name = "product_id")
    @Length(max = 50)
    private String productId;

    @NotNull
    @Length(max = 50)
    @Column(name = "product_name")
    private String productName;

    @Column(name = "stock")
    private Integer stock;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
