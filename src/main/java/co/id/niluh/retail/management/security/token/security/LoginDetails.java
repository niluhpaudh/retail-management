package co.id.niluh.retail.management.security.token.security;

import co.id.niluh.retail.management.entity.auth.Privilege;
import co.id.niluh.retail.management.entity.auth.User;
import co.id.niluh.retail.management.security.SecurityAudit;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ToString
public class LoginDetails extends LoginData implements org.springframework.security.core.userdetails.UserDetails{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4215076879888145276L;
	
	private List<GrantedAuthority> authorities;
    private SecurityAudit audit;
    
	public LoginDetails(LoginData loginData, List<GrantedAuthority> authorities, SecurityAudit audit) {
        super(loginData);
        this.setAuthorities(authorities);
        this.setAudit(audit);
    }
	
	public LoginDetails(User userAccess, String user, List<Privilege> privileges, List<GrantedAuthority> authorities, SecurityAudit audit) {
        super(userAccess, user, privileges);
        this.setAuthorities(authorities);
        this.setAudit(audit);
    }
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList<>(authorities);
	}

	@Override
	public String getUsername() {
		return getUserName();
	}

	@Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public SecurityAudit getAudit() {
		return audit;
	}

	public void setAudit(SecurityAudit audit) {
		this.audit = audit;
	}

}
