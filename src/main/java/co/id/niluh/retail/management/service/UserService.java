package co.id.niluh.retail.management.service;

import co.id.niluh.retail.management.auth.model.AuditIdentity;
import co.id.niluh.retail.management.entity.auth.User;
import co.id.niluh.retail.management.model.UserRegisModel;
import co.id.niluh.retail.management.model.UserRoleModel;

import java.util.List;

public interface UserService {

	void logout(String userId);
	List<User> getAllUser();
	void loginFailed(String username);
	void createUser(UserRegisModel create, AuditIdentity identity);
	void createUserByAdmin(UserRegisModel create, AuditIdentity identity);
	void updateUser(UserRegisModel update, AuditIdentity identity);
	void deleteUser(String userName, AuditIdentity identity);
	void activeUser(String userName, AuditIdentity identity);
	void addUserRole(UserRoleModel userRoleModel, AuditIdentity identity);
}
