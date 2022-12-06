package uit.edu.vn.universitymanagement.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import uit.edu.vn.universitymanagement.dto.AccountReqDto;
import uit.edu.vn.universitymanagement.dto.AccountRspDto;
import uit.edu.vn.universitymanagement.model.entity.Account;
import uit.edu.vn.universitymanagement.service.SimpleAccountService;
import uit.edu.vn.universitymanagement.util.ReqDtoEntityMapper;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/account")
public class AccountController {
    private final ModelMapper modelMapper;
    private final SimpleAccountService simpleAccountService;
    @PutMapping
    public ResponseEntity<AccountRspDto> create(Authentication authentication, @RequestBody  AccountReqDto accountReqDto) {
        Account account = ReqDtoEntityMapper.map(accountReqDto);
        Account savedAccount = simpleAccountService.create(authentication, account);
        AccountRspDto accountRspDto = modelMapper.map(savedAccount, AccountRspDto.class);
        return ResponseEntity.ok(accountRspDto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<AccountRspDto> read(Authentication authentication, @PathVariable("id") long id) {
        Account account = simpleAccountService.read(authentication, id);
        AccountRspDto accountRspDto = modelMapper.map(account, AccountRspDto.class);
        return ResponseEntity.ok(accountRspDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(Authentication authentication, @PathVariable("id") long id) {
        simpleAccountService.delete(authentication, id);
        return ResponseEntity.ok().build();
    }
}
