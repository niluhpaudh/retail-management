package co.id.niluh.retail.management.exception;

import lombok.ToString;

@ToString
public class AuthenticationException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3675369137995737858L;

    public AuthenticationException(String msgError) {
        super("Error");

    }

}
