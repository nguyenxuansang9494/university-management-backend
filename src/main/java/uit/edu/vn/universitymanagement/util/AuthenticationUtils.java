package uit.edu.vn.universitymanagement.util;

import org.springframework.security.core.Authentication;
import uit.edu.vn.universitymanagement.model.Role;
import uit.edu.vn.universitymanagement.model.entity.Account;
import uit.edu.vn.universitymanagement.service.ActionType;

public final class AuthenticationUtils {
    private AuthenticationUtils(){
        super();
    }

    public static Account getAccount(Authentication authentication) {
        if (authentication.getPrincipal() instanceof Account) {
            return (Account) authentication.getPrincipal();
        }
        throw new IllegalArgumentException("principal is not an account");
    }

    public static boolean allowAllToReadOnlyAboveModeratorToWrite(Authentication authentication, ActionType actionType) {
        Account account = AuthenticationUtils.getAccount(authentication);
        if (actionType.ordinal() > ActionType.READ.ordinal()) {
            return account.getAuthorities().stream().noneMatch(e -> Role.MODERATOR.equals(e) || Role.ADMINISTRATOR.equals(e));
        }
        return false;
    }
}
