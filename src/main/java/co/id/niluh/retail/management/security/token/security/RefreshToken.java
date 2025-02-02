package co.id.niluh.retail.management.security.token.security;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class RefreshToken implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8855379688956329364L;
	private String refreshToken;
	private String authorities;
	private Object info;
}