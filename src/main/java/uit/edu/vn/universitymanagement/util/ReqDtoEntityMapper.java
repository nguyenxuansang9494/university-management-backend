package uit.edu.vn.universitymanagement.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import uit.edu.vn.universitymanagement.dto.AccountReqDto;
import uit.edu.vn.universitymanagement.model.entity.Account;

public final class ReqDtoEntityMapper {
    private ReqDtoEntityMapper() {
        super();
    }

    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public static Account map(AccountReqDto accountReqDto) {
        Account account = new Account();
        account.setUsername(accountReqDto.getUsername());
        account.setPassword(bCryptPasswordEncoder.encode(accountReqDto.getPassword()));
        account.setRoles(accountReqDto.getRoles());
        account.setEnable(accountReqDto.isEnable());
        return account;
    }
}
