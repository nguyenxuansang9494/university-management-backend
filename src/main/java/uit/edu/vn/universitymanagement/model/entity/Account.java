package uit.edu.vn.universitymanagement.model.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uit.edu.vn.universitymanagement.model.ManagedModel;
import uit.edu.vn.universitymanagement.model.Metadata;
import uit.edu.vn.universitymanagement.model.Role;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Account implements UserDetails, ManagedModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_id_seq")
    @SequenceGenerator(name = "account_id_seq", allocationSize = 100)
    private Long id;
    @Embedded
    private Metadata metadata;
    @Column(unique = true, nullable = false, length = 20)
    private String username;
    @Column(nullable = false)
    private String password;
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Set<Role> roles;
    @Column(nullable = false)
    private boolean isAccountExpired;
    @Column(nullable = false)
    private boolean isAccountLock;
    @Column(nullable = false)
    private boolean isCredentialExpired;
    @Column(nullable = false)
    private boolean isEnable;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !isAccountLock;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isAccountLock;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !isCredentialExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnable;
    }
}
