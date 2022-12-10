package uit.edu.vn.universitymanagement.authorization;

import org.springframework.security.core.Authentication;
import uit.edu.vn.universitymanagement.model.entity.Account;
import uit.edu.vn.universitymanagement.util.AuthenticationUtils;

import java.util.List;

public interface Authorizer<T> {
    default boolean authorize(Authentication authentication, ActionType actionType, T object) {
        return Authorizer.allowAllToReadButOnlyCertainRoleAboveToWrite(authentication, actionType, Role.MODERATOR);
    }
    default boolean batchAuthorize(Authentication authentication, ActionType actionType, List<T> objects) {
        return objects.stream().map(obj -> authorize(authentication, actionType, obj)).reduce((Boolean::logicalAnd)).orElse(false);
    }

    static boolean allowAllToReadButOnlyCertainRoleAboveToWrite(Authentication authentication, ActionType actionType, Role role) {
        Account account = AuthenticationUtils.getAccount(authentication);
        if (actionType.ordinal() > ActionType.READ.ordinal()) {
            return account.getAuthorities().stream().map(Role.class::cast).map(Role::ordinal).anyMatch(e -> e >= role.ordinal());
        }
        return true;
    }
}
