package uit.edu.vn.universitymanagement.util;

import org.springframework.security.core.Authentication;
import uit.edu.vn.universitymanagement.model.entity.Account;

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
}
