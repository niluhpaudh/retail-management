package co.id.niluh.retail.management.entity.pk;

import java.io.Serializable;

public class RolePrivilegePk implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7423147155831555700L;
	private String roleId;
	private String privilegeId;
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getPrivilegeId() {
		return privilegeId;
	}
	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
	}
}
