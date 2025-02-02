package co.id.niluh.retail.management.repository;

import co.id.niluh.retail.management.entity.auth.Role;
import co.id.niluh.retail.management.entity.auth.UserRole;
import co.id.niluh.retail.management.entity.pk.UserRolePk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
}
