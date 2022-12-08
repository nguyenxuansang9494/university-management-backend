package uit.edu.vn.universitymanagement.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uit.edu.vn.universitymanagement.dto.AccountDto;
import uit.edu.vn.universitymanagement.model.entity.Account;
import uit.edu.vn.universitymanagement.service.AbstractCrudService;
import uit.edu.vn.universitymanagement.util.ModelMapperWrapper;

@RestController
@RequestMapping("/api/account")
public class AccountController extends AbstractCrudController<Account, AccountDto> {
    public AccountController(ModelMapperWrapper modelMapperWrapper, AbstractCrudService<Account> service) {
        super(modelMapperWrapper, service, Account.class, AccountDto.class);
    }
}
