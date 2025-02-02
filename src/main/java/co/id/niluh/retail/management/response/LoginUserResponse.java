package co.id.niluh.retail.management.response;

import co.id.niluh.retail.management.entity.auth.Role;
import co.id.niluh.retail.management.entity.auth.User;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Data
@ToString
public class LoginUserResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5709325368467046706L;
	private String userId;
    private String username;
    private String name;
    private String email;
    private String phone;
    private String nip;
    private BigDecimal limitTransaction;
    private Boolean needChangePassword;
    private Boolean needChangePin;
    private Boolean firstLogin;
	private Set<Role> roleId;
    private LocalDateTime lastLogin;
    private String roleName;
    private String validation;
    
    public LoginUserResponse(){
    	super();
    }

	public LoginUserResponse(User user) {
		super();
		this.userId = user.getUserId();
		this.username = user.getUserName();
		this.name = user.getName();
		this.roleId = user.getRoles();
		this.roleName = getRoleId() != null ? getRoleId().toString() : "";
	}
}
