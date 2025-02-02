package co.id.niluh.retail.management.auth.model;

import co.id.niluh.retail.management.entity.auth.User;
import co.id.niluh.retail.management.security.UserModel;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetails extends UserModel implements org.springframework.security.core.userdetails.UserDetails{

    	/**
		 * 
		 */
		private static final long serialVersionUID = 6233722334883782945L;
		
		private List<GrantedAuthority> authorities;
		
        public UserDetails(){}
        public UserDetails(User user, List<GrantedAuthority> authorities, Long idleTimeout, Boolean allowedApprove, String headBranch){
        	super(user, idleTimeout, allowedApprove, headBranch);
        	this.authorities = authorities;
        }

        public UserDetails(UserModel model, List<GrantedAuthority> authorities){
        	super(model);
        	this.authorities = authorities;
        }
        
        public UserDetails(String user, List<GrantedAuthority> authorities){
        	super(user);
        	this.authorities = authorities;
        }
        
		public void setAuthorities(List<GrantedAuthority> authorities) {
			this.authorities = authorities;
		}
		
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return new ArrayList<>(authorities);
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
		@Override
		public String getUsername() {
			return this.getUserName();
		}
    }