package uit.edu.vn.universitymanagement.repository;

import org.springframework.stereotype.Repository;
import uit.edu.vn.universitymanagement.model.entity.Account;

import java.util.Optional;

@Repository
public interface AccountRepository extends CommonJpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);
}
