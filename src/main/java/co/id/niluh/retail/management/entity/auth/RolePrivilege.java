package co.id.niluh.retail.management.entity.auth;


import co.id.niluh.retail.management.entity.pk.RolePrivilegePk;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="mst_role_privilege")
@IdClass(RolePrivilegePk.class)
public class RolePrivilege implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -504393850813223581L;
	@Id
	@Column(name="role_id")
	private String roleId;
	@Id
	@Column(name="privilege_id")
	private String privilegeId;
	
	public RolePrivilege(){}
	
	public RolePrivilege(String roleId, String privilegeId) {
		super();
		this.roleId = roleId;
		this.privilegeId = privilegeId;
	}

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
