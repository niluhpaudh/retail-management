package co.id.niluh.retail.management.security;

import co.id.niluh.retail.management.config.prop.ConfigProperties;
import co.id.niluh.retail.management.entity.auth.Privilege;
import co.id.niluh.retail.management.entity.auth.User;
import co.id.niluh.retail.management.enumz.ErrorCodes;
import co.id.niluh.retail.management.enumz.UserStatus;
import co.id.niluh.retail.management.exception.AuthenticationException;
import co.id.niluh.retail.management.repository.UserRepository;
import co.id.niluh.retail.management.security.token.security.LoginData;
import co.id.niluh.retail.management.security.token.security.LoginDetails;
import co.id.niluh.retail.management.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthProvider implements AuthenticationProvider {
	private final Logger log = LoggerFactory.getLogger(AuthProvider.class);

	@Autowired
	private ConfigProperties configProperties;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
    private UserRepository userRepository;
	@Autowired
    private UserService userService;
    
	@Override
	public Authentication authenticate(Authentication authentication) {
		String userName = authentication.getName();
        String password = (String) authentication.getCredentials();
        log.info("Authenticating {}", userName);
        
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest httpRequest = attributes.getRequest();

		User user = userRepository.findTop1ByUserName(userName).orElseThrow(() -> new AuthenticationException(ErrorCodes.USER_NOT_FOUND.getMessageCode()));
		if (!user.getStatus().equals(UserStatus.ACTIVE)) {
			throw new AuthenticationException("User Tidak Aktif");
		}

		if (!passwordEncoder.matches(password, user.getPassword())) {
			userService.loginFailed(userName);
			throw new AuthenticationException(ErrorCodes.BAD_CREDENTIAL.getDefaultMessage());
		}

//        user.setLastLogin(LocalDateTime.now());
        user.setLoginFailed(0);
        userRepository.save(user);
		List<Privilege> privileges = new ArrayList<>();
        SecurityAudit audit = new SecurityAudit(httpRequest);
        LoginData loginData = new LoginData(user, userName,privileges );
		LoginDetails details = new LoginDetails(loginData, Arrays.asList(new String[]{"USER"}).stream()
				.map(SimpleGrantedAuthority::new).collect(Collectors.toList()), audit);
		return new UsernamePasswordAuthenticationToken(details, password, details.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

}
