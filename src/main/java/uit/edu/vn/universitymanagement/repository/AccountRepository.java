package uit.edu.vn.universitymanagement.repository;

import uit.edu.vn.universitymanagement.model.entity.Account;

import java.util.Optional;

public interface AccountRepository extends CommonJpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);
}
