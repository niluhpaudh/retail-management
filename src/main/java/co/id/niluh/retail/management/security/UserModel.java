package co.id.niluh.retail.management.security;

import co.id.niluh.retail.management.entity.auth.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;

@Data
@ToString
public class UserModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6233722334883782945L;

	private String userId;
	private String userName;
	private String name;
	@JsonIgnore
	private String password;
	private Boolean firstLogin;
	private String branchId;
	private Long idleTimeout;
	private Boolean allowedApprove;
	private Boolean isHeadBranch;
	
	public UserModel() {}
	
	public UserModel(User user, Long idleTimeout, Boolean allowedApprove, String headBranch) {
		super();
		this.userId = user.getUserId();
		this.userName = user.getUserName();
		this.password = user.getPassword();
		this.name = user.getName();
		this.idleTimeout = idleTimeout;
		this.allowedApprove = allowedApprove;
	}

	public UserModel(Map<String, ?> userDetails) {
		this.userId = (String) userDetails.get("userId");
		this.userName = (String) userDetails.get("userName");
		this.password = (String) userDetails.get("password");
		this.name = (String) userDetails.get("name");
		this.firstLogin =(Boolean) userDetails.get("firstLogin");
		this.branchId = (String) userDetails.get("branchId");
		this.idleTimeout = (Long) userDetails.get("idleTimeout");
		this.allowedApprove = (Boolean) userDetails.get("allowedApprove");
		this.isHeadBranch = (Boolean) userDetails.get("isHeadBranch");
	}

	public UserModel(UserModel model) {
		super();
		this.userId = model.getUserId();
		this.userName = model.getUserName();
		this.password = model.getPassword();
		this.name = model.getName();
		this.firstLogin = model.getFirstLogin();
		this.branchId = model.getBranchId();
		this.idleTimeout = model.getIdleTimeout();
		this.allowedApprove = model.getAllowedApprove();
		this.isHeadBranch = model.getIsHeadBranch();
	}
	
	public UserModel(String user) {
		super();
		this.userId = user;
		this.userName = user;
		this.name = user;
		this.firstLogin = false;
	}
	
	public void clearPassword() {
		this.password = null;
	}
}