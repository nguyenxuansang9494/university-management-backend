package uit.edu.vn.universitymanagement.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import uit.edu.vn.universitymanagement.model.Role;
import uit.edu.vn.universitymanagement.model.entity.Account;
import uit.edu.vn.universitymanagement.repository.AccountRepository;
import uit.edu.vn.universitymanagement.util.AuthenticationUtils;


@Service
public class SimpleAccountService extends AbstractCrudService<Account, AccountRepository> {
    public SimpleAccountService(AccountRepository repository) {
        super(repository);
    }

    @Override
    public boolean notAuthorize(Authentication authentication, ActionType actionType, Account object) {
        Account account = AuthenticationUtils.getAccount(authentication);
        if (actionType == ActionType.WRITE && object.getAuthorities().stream().anyMatch(e -> ((Role) e).ordinal() >= Role.MODERATOR.ordinal())) {
            return !account.getAuthorities().contains(Role.ADMINISTRATOR);
        }
        return account.getAuthorities().stream().noneMatch(e -> ((Role) e).ordinal() >= Role.MODERATOR.ordinal());
    }
}
