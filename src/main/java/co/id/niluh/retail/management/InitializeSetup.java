package co.id.niluh.retail.management;



import co.id.niluh.retail.management.entity.auth.Role;
import co.id.niluh.retail.management.entity.auth.User;
import co.id.niluh.retail.management.enumz.UserStatus;
import co.id.niluh.retail.management.repository.PrivilegeRepository;
import co.id.niluh.retail.management.repository.RolePrivilegeRepository;
import co.id.niluh.retail.management.repository.RoleRepository;
import co.id.niluh.retail.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InitializeSetup implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PrivilegeRepository privilegeRepository;
    @Autowired
    private RolePrivilegeRepository rolePrivilegeRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
    	Role role = createRoleAdmin();
        Role cashier = createRoleCashier();
		createUserAdmin(role);
    }

	private Role createRoleAdmin() {
		Role role = roleRepository.findById("ADMIN")
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setRoleId("ADMIN");
                    newRole.setRoleName("System Administrator");
                    newRole.setCreatedBy("admin");
                    newRole.setCreatedDate(LocalDateTime.now());
                    newRole.setUpdatedBy("admin");
                    newRole.setUpdatedDate(LocalDateTime.now());
                    roleRepository.save(newRole);
                    return newRole;
                });
		return role;
	}

    private Role createRoleCashier() {
        Role role = roleRepository.findById("CASHIER")
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setRoleId("CASHIER");
                    newRole.setRoleName("Cashier");
                    newRole.setCreatedBy("admin");
                    newRole.setCreatedDate(LocalDateTime.now());
                    newRole.setUpdatedBy("admin");
                    newRole.setUpdatedDate(LocalDateTime.now());
                    roleRepository.save(newRole);
                    return newRole;
                });
        return role;
    }

	@Transactional
    void createUserAdmin(Role role) {
		if (!userRepository.existsByUserName("admin")) {
			User user = new User();
			user.setUserName("admin");
			user.updatePassword(null, "password");
			user.setLoginFailed(0);
			user.setStatus(UserStatus.ACTIVE);
			user.getRoles().add(role);
			user.setName("Admin User, de-active me!");
			user.setCreatedBy("admin");
			user.setCreatedDate(LocalDateTime.now());
			user.setUpdatedBy("admin");
			user.setUpdatedDate(LocalDateTime.now());
			userRepository.save(user);
		}
	}

}
