package co.id.niluh.retail.management.repository;

import co.id.niluh.retail.management.entity.auth.Privilege;
import co.id.niluh.retail.management.entity.auth.Role;
import co.id.niluh.retail.management.entity.auth.RolePrivilege;
import co.id.niluh.retail.management.entity.pk.RolePrivilegePk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RolePrivilegeRepository extends JpaRepository<RolePrivilege, RolePrivilegePk> {
    List<Privilege> findAllByRoleId(String roleId);
}
