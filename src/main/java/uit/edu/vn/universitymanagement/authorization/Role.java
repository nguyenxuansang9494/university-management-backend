package uit.edu.vn.universitymanagement.authorization;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    STUDENT("student"), TEACHER("teacher"), MODERATOR("moderator"), ADMINISTRATOR("administrator");

    private final String name;

    Role(String name) {
        this.name = name;
    }


    @Override
    public String getAuthority() {
        return name;
    }
}
