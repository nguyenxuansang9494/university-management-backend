package uit.edu.vn.universitymanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uit.edu.vn.universitymanagement.authorization.Role;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private Long id;
    private String username;
    private String password;
    private MetadataDto metadata;
    private Set<Role> roles;
    private Boolean isAccountExpired;
    private Boolean isAccountLock;
    private Boolean isCredentialExpired;
    private Boolean isEnable;

}
