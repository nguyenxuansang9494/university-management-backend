package uit.edu.vn.universitymanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uit.edu.vn.universitymanagement.model.Role;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private long id;
    private String username;
    private String password;
    private MetadataDto metadata;
    private Set<Role> roles;
    private boolean isAccountExpired;
    private boolean isAccountLock;
    private boolean isCredentialExpired;
    private boolean isEnable;

}
