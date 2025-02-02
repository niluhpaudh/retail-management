package co.id.niluh.retail.management.auth.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.Validate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@ToString
@EqualsAndHashCode(of = { "userName" })
public class AuditIdentity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5950206654455155086L;

	public static final String UNKNOWN = "SYSTEM";

	private String userId;
	private String userName;
	private String name;

	private List<String> authorities;
	private LocalDateTime timestamp = LocalDateTime.now();

	public AuditIdentity() {}

	public AuditIdentity(String userName) {
		this.userName = userName;
	}
	public AuditIdentity(String userId, String userName) {
		this.userId = userId;
		this.userName = userName;
	}
	public AuditIdentity(String userId, String userName, LocalDateTime timestamp) {
		this.userId = userId;
		this.userName = userName;
		this.timestamp = timestamp;
	}

	public static AuditIdentity get() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			if (authentication.getPrincipal() instanceof UserDetails) {
				UserDetails principal = ((UserDetails) authentication.getPrincipal());
				AuditIdentity auditIdentity = new AuditIdentity();
				auditIdentity.userId = principal.getUserId();
				auditIdentity.userName = principal.getUserName();
				auditIdentity.name = principal.getName();

				auditIdentity.authorities = authentication.getAuthorities().stream().map(a -> a.getAuthority())
						.map(String::new).collect(Collectors.toList());
				return auditIdentity;
			}
		}
		AuditIdentity auditIdentity = new AuditIdentity();
		auditIdentity.userId = UNKNOWN;
		auditIdentity.userName = UNKNOWN;
		return auditIdentity;

	}

	public static AuditIdentity of(String userId, String userName) {
		final AuditIdentity id = new AuditIdentity();
		id.userId = userId;
		id.userName = userName;
		return id;
	}

	public static AuditIdentity of(String userName) {
		final AuditIdentity id = new AuditIdentity();
		id.userName = userName;
		return id;
	}

	public static AuditIdentity of(Principal principal) {
		final AuditIdentity id = new AuditIdentity();
		id.userName = Optional.ofNullable(principal).map(Principal::getName).orElse(UNKNOWN);
		return id;
	}

	public static AuditIdentity of(HttpServletRequest request) {
		Validate.notNull(request);
		final AuditIdentity id = new AuditIdentity();
		id.userName = Optional.ofNullable(request.getUserPrincipal()).map(Principal::getName).orElse(UNKNOWN);
		return id;
	}

	public static String getRemoteHost(HttpServletRequest request) {
		final String remoteHost = request.getRemoteHost();
		final String remoteHeader = request.getHeader("X-Forwarded-Host");
		if (remoteHeader != null) {
			return remoteHeader;
		} else {
			return remoteHost;
		}
	}

	public static AuditIdentity of(Authentication auth) {
		Validate.notNull(auth);
		return of(auth.getName());
	}
}
