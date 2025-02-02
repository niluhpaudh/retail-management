package co.id.niluh.retail.management.security;


import co.id.niluh.retail.management.common.Function;
import co.id.niluh.retail.management.entity.auth.Privilege;
import lombok.Data;
import lombok.ToString;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Data
@ToString
public class SecurityAudit implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6036071191086300167L;
	private String ip;
	private String location;
	private String longitude;
	private String latitude;
	private String agent;
	private String deviceType;

	private List<Privilege> privileges;
	public SecurityAudit(String ip, String location, String longitude, String latitude, List<Privilege> privileges) {
		super();
		this.ip = ip;
		this.location = location;
		this.longitude = longitude;
		this.latitude = latitude;
		this.privileges = privileges;
	}
	public SecurityAudit(String ip, String longitude, String latitude, List<Privilege> privileges) {
		super();
		this.ip = ip;
		this.longitude = longitude;
		this.latitude = latitude;
		this.privileges = privileges;
	}
	public SecurityAudit(HttpServletRequest httpServletRequest) {
		super();
		String longitude = httpServletRequest.getHeader("x-longitude");
		String latitude = httpServletRequest.getHeader("x-latitude");
		String device = httpServletRequest.getHeader("x-device-type");
		this.ip = Function.getIpAddress(httpServletRequest);
		this.longitude = longitude;
		this.latitude = latitude;
		this.deviceType = Optional.ofNullable(device).orElse("web");
	}
	public SecurityAudit(HttpServletRequest httpServletRequest, String deviceType, List<Privilege> privileges) {
		super();
		String longitude = httpServletRequest.getHeader("x-longitude");
		String latitude = httpServletRequest.getHeader("x-latitude");
		this.ip = Function.getIpAddress(httpServletRequest);
		this.longitude = longitude;
		this.latitude = latitude;
		this.deviceType = deviceType;
		this.privileges = privileges;
	}
	public SecurityAudit() {
		super();
		try {
			ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpServletRequest httpServletRequest = attributes.getRequest();
			String longitude = httpServletRequest.getHeader("x-longitude");
			String latitude = httpServletRequest.getHeader("x-latitude");
			this.ip = Function.getIpAddress(httpServletRequest);
			this.longitude = longitude;
			this.latitude = latitude;
		} catch (Exception e) {}
        
	}
	
	
}
