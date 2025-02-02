package co.id.niluh.retail.management.security.token.security;

import co.id.niluh.retail.management.entity.auth.Privilege;
import co.id.niluh.retail.management.entity.auth.User;
import co.id.niluh.retail.management.response.LoginUserResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
public class LoginData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1874347071220312456L;
	
	private String userId;
	private String userName;
	private String name;
	@JsonIgnore
	private String password;
	private LoginUserResponse user;
	
	private Long idleTimeout;
    private Long maxSecondsReRequestOtp;
    private Long maxMinutesOtpValid;
    private String channelIdentity;
    private String deviceType;

	List<Privilege> privileges;
    
    public LoginData(User userAccess, String user, List<Privilege> privileges){
    	super();
    }
    
    public LoginData(Long idleTimeout, Long maxSecondsReRequestOtp, Long maxMinutesOtpValid){
    	super();
    	this.idleTimeout = idleTimeout;
    	this.maxSecondsReRequestOtp = maxSecondsReRequestOtp;
    	this.maxMinutesOtpValid = maxMinutesOtpValid;
    }
    
    public LoginData( User user,
                     Long idleTimeout, Long maxSecondsReRequestOtp, Long maxMinutesOtpValid, String deviceType,List<Privilege> privileges){
    	super();
    	this.userId = user.getUserId();
    	this.userName = user.getUserName();
    	this.password = user.getPassword();
    	this.name = user.getName();
    	this.user = new LoginUserResponse(user);
    	
    	this.idleTimeout = idleTimeout;
    	this.maxSecondsReRequestOtp = maxSecondsReRequestOtp;
    	this.maxMinutesOtpValid = maxMinutesOtpValid;
    	this.channelIdentity = "SYSTEM";
    	this.deviceType = deviceType;
		this.privileges = privileges;
    }
    
    public LoginData(LoginData loginData){
    	super();
    	this.userId = loginData.getUserId();
    	this.userName = loginData.getUserName();
    	this.password = loginData.getPassword();
    	this.name = loginData.getName();
    	this.user = loginData.getUser();
    	
    	this.idleTimeout = loginData.getIdleTimeout();
    	this.maxMinutesOtpValid = loginData.getMaxMinutesOtpValid();
    	this.maxSecondsReRequestOtp = loginData.getMaxSecondsReRequestOtp();
    	this.channelIdentity = loginData.getChannelIdentity();
    	this.deviceType = loginData.getDeviceType();
		this.privileges = loginData.getPrivileges();
    }

    
    public void clearPassword(){
        this.password = null;
    }
}
