package uit.edu.vn.universitymanagement.controller;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uit.edu.vn.universitymanagement.dto.AccountDto;
import uit.edu.vn.universitymanagement.model.entity.Account;
import uit.edu.vn.universitymanagement.repository.AccountRepository;
import uit.edu.vn.universitymanagement.service.SimpleAccountService;

@RestController
@RequestMapping("/api/account")
public class SimpleAccountController extends AbstractCrudController<Account, AccountDto, SimpleAccountService, AccountRepository> {
    public SimpleAccountController(ModelMapper modelMapper, SimpleAccountService service) {
        super(modelMapper, service, Account.class, AccountDto.class);
    }
}
