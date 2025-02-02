package co.id.niluh.retail.management.security.token.security.token;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class TokenData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4251941413244591522L;
	private String access_token;
	private String refresh_token;
	private Object userDetails;

	public TokenData(String access_token, String refresh_token, Object userDetails) {
		this.access_token = access_token;
		this.refresh_token = refresh_token;
		this.userDetails = userDetails;
	}
}
