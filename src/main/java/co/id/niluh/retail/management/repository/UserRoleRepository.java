package co.id.niluh.retail.management.repository;

import co.id.niluh.retail.management.entity.auth.User;
import co.id.niluh.retail.management.entity.auth.UserRole;
import co.id.niluh.retail.management.entity.pk.UserRolePk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRolePk> {

}
