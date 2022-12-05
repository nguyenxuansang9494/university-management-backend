package uit.edu.vn.universitymanagement.service;

import org.springframework.data.domain.Page;
import uit.edu.vn.universitymanagement.model.entity.Account;

import java.util.List;

public interface AccountService {
    Account createAccount(Account account);
    Account readAccount(long id);
    Account updateAccount (Account account);
    void deleteAccount(long id);
    List<Account> createAccounts(List<Account> accounts);
    Page<Account> readAllAccount();
    List<Account> updateAccounts(List<Account> accounts);
    void deleteAccounts(long[] ids);
}
