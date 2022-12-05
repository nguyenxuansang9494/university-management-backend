package uit.edu.vn.universitymanagement.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
        account = simpleAccountService.create(authentication, account);
        AccountRspDto accountRspDto = modelMapper.map(account, AccountRspDto.class);
        return ResponseEntity.ok(accountRspDto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<AccountRspDto> read(Authentication authentication, @PathVariable("id") long id) {
        Account account = simpleAccountService.read(authentication, id);
        AccountRspDto accountRspDto = modelMapper.map(account, AccountRspDto.class);
        return ResponseEntity.ok(accountRspDto);
    }

}
