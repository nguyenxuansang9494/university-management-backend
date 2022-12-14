package uit.edu.vn.universitymanagement.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import uit.edu.vn.universitymanagement.authorization.ActionType;
import uit.edu.vn.universitymanagement.authorization.Authorizer;
import uit.edu.vn.universitymanagement.authorization.Role;
import uit.edu.vn.universitymanagement.exception.CommonRuntimeException;
import uit.edu.vn.universitymanagement.exception.ErrorType;
import uit.edu.vn.universitymanagement.model.entity.Account;
import uit.edu.vn.universitymanagement.repository.AccountRepository;
import uit.edu.vn.universitymanagement.util.AuthenticationUtils;
import uit.edu.vn.universitymanagement.util.ManagedModelUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class CrudAccountService extends AbstractCrudService<Account> {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public CrudAccountService(AccountRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        super(repository);
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public boolean authorize(Authentication authentication, ActionType actionType, Account object) {
        Account account = AuthenticationUtils.getAccount(authentication);
        boolean isOwner = Authorizer.allowOwnerToWrite(authentication, actionType, account);
        if (account.getAuthorities().stream().map(Role.class::cast).map(Role::ordinal).anyMatch(e -> e >= Role.MODERATOR.ordinal())) {
            return Authorizer.allowACertainRoleAboveToReadAndACertainRoleAboveToWrite(authentication, actionType, Role.ADMINISTRATOR, Role.MODERATOR) || isOwner;
        }
        return Authorizer.allowACertainRoleAboveToReadAndACertainRoleAboveToWrite(authentication, actionType, Role.MODERATOR, Role.MODERATOR) || isOwner;
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
            Account updatedObject = repository.findById(object.getId()).orElseThrow(() -> {
                throw new CommonRuntimeException(ErrorType.NOT_FOUND);
            });
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
