package uit.edu.vn.universitymanagement.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import uit.edu.vn.universitymanagement.dto.AccountDto;
import uit.edu.vn.universitymanagement.dto.QueryByIdDto;
import uit.edu.vn.universitymanagement.model.entity.Account;
import uit.edu.vn.universitymanagement.service.AbstractCrudService;

import java.util.List;

@RestController
@RequestMapping("/api/account")
public class SimpleAccountController extends AbstractCrudController<Account, AccountDto> {
    public SimpleAccountController(ModelMapper modelMapper, AbstractCrudService<Account> service) {
        super(modelMapper, service, Account.class, AccountDto.class);
    }

    @Override
    @PutMapping
    public ResponseEntity<AccountDto> create(Authentication authentication, @RequestBody AccountDto reqDto) {
        return super.create(authentication, reqDto);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> read(Authentication authentication, @PathVariable("id") long id) {
        return super.read(authentication, id);
    }

    @Override
    @PatchMapping
    public ResponseEntity<AccountDto> update(Authentication authentication, @RequestBody AccountDto reqDto) {
        return super.update(authentication, reqDto);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(Authentication authentication, @PathVariable("id") long id) {
        return super.delete(authentication, id);
    }

    @Override
    @PutMapping("/batch")
    public ResponseEntity<List<AccountDto>> create(Authentication authentication, List<AccountDto> reqDtos) {
        return super.create(authentication, reqDtos);
    }

    @Override
    @PostMapping("/batch")
    public ResponseEntity<Object> read(Authentication authentication, @RequestBody QueryByIdDto queryByIdDto) {
        return super.read(authentication, queryByIdDto);
    }

    @Override
    @PatchMapping("/batch")
    public ResponseEntity<List<AccountDto>> update(Authentication authentication, @RequestBody List<AccountDto> reqDtos) {
        return super.update(authentication, reqDtos);
    }

    @Override
    @DeleteMapping("/batch")
    public ResponseEntity<Void> delete(Authentication authentication, @RequestBody List<Long> ids) {
        return super.delete(authentication, ids);
    }
}
