package co.id.niluh.retail.management.security.token.security.token;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class Token implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5986920809262386272L;
	private String token;
	private String refreshToken;
	private String authorities;
	private Object info;
}
