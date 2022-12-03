package uit.edu.vn.universitymanagement.model;

import org.springframework.security.core.GrantedAuthority;

public enum SimpleAuthority implements GrantedAuthority {
    ADMINISTRATOR("ADMINISTRATOR"), STUDENT("STUDENT"), TEACHER("TEACHER");

    private final String authority;
    SimpleAuthority(String authority) {
        this.authority = authority;
    }


    @Override
    public String getAuthority() {
        return authority;
    }
}
