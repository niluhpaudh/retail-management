package co.id.niluh.retail.management.repository;

import co.id.niluh.retail.management.entity.auth.Privilege;
import co.id.niluh.retail.management.entity.auth.RolePrivilege;
import co.id.niluh.retail.management.entity.pk.RolePrivilegePk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, String> {

}
