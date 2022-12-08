package uit.edu.vn.universitymanagement.config;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;
import uit.edu.vn.universitymanagement.dto.AccountDto;
import uit.edu.vn.universitymanagement.model.entity.Account;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Configuration
public class ModelMapperConfig {
    private final ModelMapper modelMapper;

    @PostConstruct
    public void accountToAccountDto() {
        modelMapper.createTypeMap(Account.class, AccountDto.class)
                .addMappings(mapping -> mapping.skip(AccountDto::setPassword));
    }
}
