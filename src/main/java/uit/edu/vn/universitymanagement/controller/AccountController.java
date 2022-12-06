package uit.edu.vn.universitymanagement.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import uit.edu.vn.universitymanagement.dto.AccountReqDto;
import uit.edu.vn.universitymanagement.dto.AccountRspDto;
import uit.edu.vn.universitymanagement.dto.QueryByIdDto;
import uit.edu.vn.universitymanagement.model.entity.Account;
import uit.edu.vn.universitymanagement.service.AbstractCrudService;

import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountController extends AbstractCrudController<Account, AccountReqDto, AccountRspDto> {
    public AccountController(ModelMapper modelMapper, AbstractCrudService<Account> service) {
        super(modelMapper, service, Account.class, AccountRspDto.class);
    }

    @Override
    @PutMapping
    public ResponseEntity<AccountRspDto> create(Authentication authentication, @RequestBody AccountReqDto reqDto) {
        return super.create(authentication, reqDto);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<AccountRspDto> read(Authentication authentication, @PathVariable("id") long id) {
        return super.read(authentication, id);
    }

    @Override
    @PatchMapping
    public ResponseEntity<AccountRspDto> update(Authentication authentication, @RequestBody AccountReqDto reqDto) {
        return super.update(authentication, reqDto);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(Authentication authentication, @PathVariable("id") long id) {
        return super.delete(authentication, id);
    }

    @Override
    @PutMapping("/batch")
    public ResponseEntity<List<AccountRspDto>> create(Authentication authentication, List<AccountReqDto> reqDtos) {
        return super.create(authentication, reqDtos);
    }

    @Override
    @PostMapping("/batch")
    public ResponseEntity<Object> read(Authentication authentication, @RequestBody QueryByIdDto queryByIdDto) {
        return super.read(authentication, queryByIdDto);
    }

    @Override
    @PatchMapping("/batch")
    public ResponseEntity<List<AccountRspDto>> update(Authentication authentication, @RequestBody List<AccountReqDto> reqDtos) {
        return super.update(authentication, reqDtos);
    }

    @Override
    @DeleteMapping("/batch")
    public ResponseEntity<Void> delete(Authentication authentication, @RequestBody List<Long> ids) {
        return super.delete(authentication, ids);
    }
}
