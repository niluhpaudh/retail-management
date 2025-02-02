package co.id.niluh.retail.management.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditingEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8401809780889282343L;
	
	@Column(name="created_by", nullable = false, updatable = false)
	@CreatedBy
	@Length(max = 30)
	private String createdBy;
	
	@Column(name="updated_by")
	@Length(max = 30)
	@LastModifiedBy
	private String updatedBy;
	
	@Column(name="created_date", nullable = false, updatable = false)
	@CreatedDate
	private LocalDateTime createdDate;
	
	@Column(name="updated_date")
	@LastModifiedDate
	private LocalDateTime updatedDate;
}
