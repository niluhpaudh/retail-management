package co.id.niluh.retail.management.repository;

import co.id.niluh.retail.management.entity.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUserName(String username);
    Optional<User> findTop1ByUserName(String username);

    Optional<User> findTop1ByUserId(String userId);

}
