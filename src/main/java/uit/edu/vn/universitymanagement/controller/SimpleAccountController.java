package uit.edu.vn.universitymanagement.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uit.edu.vn.universitymanagement.dto.AccountDto;
import uit.edu.vn.universitymanagement.model.entity.Account;
import uit.edu.vn.universitymanagement.repository.AccountRepository;
import uit.edu.vn.universitymanagement.service.AccountCrudService;
import uit.edu.vn.universitymanagement.util.ModelMapperWrapper;

@RestController
@RequestMapping("/api/account")
public class SimpleAccountController extends AbstractCrudController<Account, AccountDto, AccountCrudService, AccountRepository> {
    public SimpleAccountController(ModelMapperWrapper modelMapperWrapper, AccountCrudService service) {
        super(modelMapperWrapper, service, Account.class, AccountDto.class);
    }
}
