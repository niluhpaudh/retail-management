package co.id.niluh.retail.management.security.token.security;

import co.id.niluh.retail.management.entity.auth.Privilege;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface OpenApiService {
	Authentication doAuthorization(String token, String ip, String user);
	List<Privilege> privileges(String user);
}
