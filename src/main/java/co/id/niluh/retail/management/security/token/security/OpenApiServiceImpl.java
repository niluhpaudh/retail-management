package co.id.niluh.retail.management.security.token.security;

import co.id.niluh.retail.management.entity.auth.Privilege;
import co.id.niluh.retail.management.entity.auth.Role;
import co.id.niluh.retail.management.entity.auth.User;
import co.id.niluh.retail.management.enumz.ErrorCodes;
import co.id.niluh.retail.management.repository.RolePrivilegeRepository;
import co.id.niluh.retail.management.repository.UserRepository;
import co.id.niluh.retail.management.repository.UserRoleRepository;
import co.id.niluh.retail.management.security.SecurityAudit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OpenApiServiceImpl implements OpenApiService {
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	UserRepository userRepository;
	@Autowired
	RolePrivilegeRepository rolePrivilegeRepository;

	@Override
	public Authentication doAuthorization(String token, String ip, String user) {
		if (token == null || token.isEmpty()) {
			throw new InternalAuthenticationServiceException(ErrorCodes.FORBIDDEN_ACCESS.getDefaultMessage());
		}

		User access = userRepository.findTop1ByUserName(user).orElseThrow(
				() -> new InternalAuthenticationServiceException(ErrorCodes.FORBIDDEN_ACCESS.getDefaultMessage()));
		if (access != null) {
			if(!passwordEncoder.matches(token, access.getPassword())) {
				throw new InternalAuthenticationServiceException(ErrorCodes.FORBIDDEN_ACCESS.getDefaultMessage());
			}
		}else{
				throw new InternalAuthenticationServiceException(ErrorCodes.FORBIDDEN_ACCESS.getDefaultMessage());

		}
		Iterator<Role> iterator = access.getRoles().iterator();
		List<Privilege> privileges = new ArrayList<>();
		if (iterator.hasNext()) {
			Role firstRole = iterator.next();
			privileges = new ArrayList<>(firstRole.getPrivileges());
		}



		LoginDetails details = new LoginDetails(access, user, privileges,Arrays.asList("USER").stream()
				.map(SimpleGrantedAuthority::new).collect(Collectors.toList()), new SecurityAudit());
		return new UsernamePasswordAuthenticationToken(details, "", details.getAuthorities());
	}

	@Override
	public List<Privilege> privileges(String user) {
		User access = userRepository.findTop1ByUserName(user).orElseThrow(
				() -> new InternalAuthenticationServiceException(ErrorCodes.FORBIDDEN_ACCESS.getDefaultMessage()));
		List<Privilege> privileges = new ArrayList<>();
		if (access != null) {
			Iterator<Role> iterator = access.getRoles().iterator();

			if (iterator.hasNext()) {
				Role firstRole = iterator.next();
				privileges = new ArrayList<>(firstRole.getPrivileges());
			}
		}
		return privileges;
	}
}
