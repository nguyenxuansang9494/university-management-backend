package uit.edu.vn.universitymanagement.service;

import org.springframework.security.core.Authentication;

import java.util.List;

public interface Authorizer<T> {
    boolean notAuthorize(Authentication authentication, ActionType actionType, T object);
    boolean notAuthorize(Authentication authentication, ActionType actionType, List<T> objects);
}
