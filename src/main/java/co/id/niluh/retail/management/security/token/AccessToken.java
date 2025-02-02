package co.id.niluh.retail.management.security.token;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccessToken implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4527935392873115562L;

	public enum GranType {
		password, refresh_token
	}

	private String username;
	private String password;
	private GranType granType;
	private String refreshToken;

	@Override
	public String toString() {
		return "AccessToken{" +
				"username='" + username + '\'' +
				", granType=" + granType +
				'}';
	}
}
