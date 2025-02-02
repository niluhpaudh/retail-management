package co.id.niluh.retail.management.service.impl;

import co.id.niluh.retail.management.auth.model.AuditIdentity;
import co.id.niluh.retail.management.entity.auth.User;
import co.id.niluh.retail.management.entity.auth.UserRole;
import co.id.niluh.retail.management.enumz.ErrorCodes;
import co.id.niluh.retail.management.enumz.UserStatus;
import co.id.niluh.retail.management.model.UserRegisModel;
import co.id.niluh.retail.management.model.UserRoleModel;
import co.id.niluh.retail.management.repository.UserRepository;
import co.id.niluh.retail.management.repository.UserRoleRepository;
import co.id.niluh.retail.management.service.UserService;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@SuppressWarnings("unchecked")
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserRoleRepository userRoleRepository;

	@Override
	public void logout(String userId) {
		userRepository.findTop1ByUserId(userId).ifPresent(user -> {
			user.setLoginStatus(false);
			userRepository.save(user);
		});
	}

	@Override
	public List<User> getAllUser() {
		return userRepository.findAll();
	}

	@Override
	public void loginFailed(String username) {
		userRepository.findTop1ByUserName(username).ifPresent(x -> {
			int failed = x.getLoginFailed() == null ? 1 : x.getLoginFailed() +1;
			x.setLoginFailed(failed);
			userRepository.save(x);
		});
	}


	@Override
	public void createUser(UserRegisModel create, AuditIdentity identity) {
		if (userRepository.existsByUserName(create.getUserName())){
			throw new RuntimeException("User telah didaftarkan sebelumnya");
		}
		User user = new User();
		user.setUserName(create.getUserName());
		user.setName(create.getName());
		user.setPassword(passwordEncoder.encode(create.getPassword()));
		user.setStatus(UserStatus.NON_ACTIVE);
		user.setLoginStatus(false);
		user.setLoginFailed(0);
		user.setCreatedBy(identity.getUserName());
		user.setCreatedDate(LocalDateTime.now());
		userRepository.save(user);
	}

	@Override
	public void createUserByAdmin(UserRegisModel create, AuditIdentity identity) {
		if (userRepository.existsByUserName(create.getUserName())){
			throw new RuntimeException("User telah didaftarkan sebelumnya");
		}
		User user = new User();
		user.setUserName(create.getUserName());
		user.setName(create.getName());
		user.setPassword(passwordEncoder.encode(create.getPassword()));
		user.setStatus(UserStatus.ACTIVE);
		user.setLoginStatus(false);
		user.setLoginFailed(0);
		user.setCreatedBy(identity.getUserName());
		user.setCreatedDate(LocalDateTime.now());
		userRepository.save(user);
	}

	@Override
	public void updateUser(UserRegisModel update, AuditIdentity identity) {
		User user = userRepository.findTop1ByUserName(update.getUserName())
				.orElseThrow(() -> new RuntimeException(ErrorCodes.USER_NOT_FOUND.getDefaultMessage()));
		user.setName(update.getName());
		user.setLoginFailed(0);
		user.setUpdatedBy(identity.getUserName());
		user.setUpdatedDate(LocalDateTime.now());
		userRepository.save(user);
	}

	@Override
	public void addUserRole(UserRoleModel userRoleModel, AuditIdentity identity) {
		User user = userRepository.findTop1ByUserName(userRoleModel.getUserName())
				.orElseThrow(() -> new RuntimeException(ErrorCodes.USER_NOT_FOUND.getDefaultMessage()));
		UserRole userRole= new UserRole();
		userRole.setRoleId(userRoleModel.getRoleId());
		userRole.setUserId(user.getUserId());
		user.setUpdatedBy(identity.getUserName());
		user.setUpdatedDate(LocalDateTime.now());
		userRepository.save(user);
		userRoleRepository.save(userRole);
	}

	@Override
	public void deleteUser(String userName, AuditIdentity identity) {
		User user = userRepository.findTop1ByUserName(userName).
				orElseThrow(() -> new RuntimeException(ErrorCodes.USER_NOT_FOUND.getDefaultMessage()));
		userRepository.delete(user);
	}

	@Override
	public void activeUser(String userName, AuditIdentity identity) {
		User user = userRepository.findTop1ByUserName(userName)
				.orElseThrow(() -> new RuntimeException(ErrorCodes.USER_NOT_FOUND.getDefaultMessage()));
		user.setStatus(UserStatus.ACTIVE);
		user.setUpdatedBy(identity.getUserName());
		user.setUpdatedDate(LocalDateTime.now());
		userRepository.save(user);
	}
}
