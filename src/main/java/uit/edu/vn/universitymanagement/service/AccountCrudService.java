package uit.edu.vn.universitymanagement.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import uit.edu.vn.universitymanagement.exception.ResourceNotFoundException;
import uit.edu.vn.universitymanagement.model.Role;
import uit.edu.vn.universitymanagement.model.entity.Account;
import uit.edu.vn.universitymanagement.repository.AccountRepository;
import uit.edu.vn.universitymanagement.util.AuthenticationUtils;
import uit.edu.vn.universitymanagement.util.ManagedModelUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class AccountCrudService extends AbstractCrudService<Account, AccountRepository> {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AccountCrudService(AccountRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        super(repository);
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public boolean authorize(Authentication authentication, ActionType actionType, Account object) {
        Account account = AuthenticationUtils.getAccount(authentication);
        if (account.getAuthorities().stream().map(Role.class::cast).map(Role::ordinal).anyMatch(e -> e >= Role.MODERATOR.ordinal())) {
            return Authorizer.allowAllToReadButOnlyCertainRoleAboveToWrite(authentication, actionType, Role.ADMINISTRATOR);
        }
        return Authorizer.allowAllToReadButOnlyCertainRoleAboveToWrite(authentication, actionType, Role.MODERATOR);
    }

    @Override
    public Account create(Authentication authentication, Account object) {
        object.setPassword(bCryptPasswordEncoder.encode(object.getPassword()));
        return super.create(authentication, object);
    }

    @Override
    public Account update(Authentication authentication, Account object) {
        if (object.getPassword() != null) {
            object.setPassword(bCryptPasswordEncoder.encode(object.getPassword()));
        } else {
            Account updatedObject = repository.findById(object.getId()).orElseThrow(ResourceNotFoundException::new);
            object.setPassword(updatedObject.getPassword());
        }
        return super.update(authentication, object);
    }

    @Override
    public List<Account> create(Authentication authentication, List<Account> objects) {
        objects.forEach(object ->
                object.setPassword(bCryptPasswordEncoder.encode(object.getPassword()))
        );
        return super.create(authentication, objects);
    }

    @Override
    public List<Account> update(Authentication authentication, List<Account> objects) {
        List<Account> accounts = repository.findAllByIdIn(ManagedModelUtils.convertToLongList(objects));
        Map<Account, String> pwMap = new HashMap<>();
        for (Account account : accounts) {
            pwMap.put(account, account.getPassword());
        }
        objects.forEach(object -> {
            if (object.getPassword() != null) {
                object.setPassword(bCryptPasswordEncoder.encode(object.getPassword()));
            } else {
                object.setPassword(pwMap.get(object));
            }
        });
        return super.update(authentication, objects);
    }
}
