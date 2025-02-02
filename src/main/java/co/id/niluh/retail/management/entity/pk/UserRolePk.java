package co.id.niluh.retail.management.entity.pk;

import java.io.Serializable;

public class UserRolePk implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7983280131216134132L;
	private String roleId;
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