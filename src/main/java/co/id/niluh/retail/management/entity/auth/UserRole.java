package co.id.niluh.retail.management.entity.auth;


import co.id.niluh.retail.management.entity.pk.UserRolePk;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="mst_user_role")
@IdClass(UserRolePk.class)
public class UserRole implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -285855639207725507L;
	@Id
	@Column(name="role_id")
	private String roleId;
	@Id
	@Column(name="user_id")
	private String userId;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
}
