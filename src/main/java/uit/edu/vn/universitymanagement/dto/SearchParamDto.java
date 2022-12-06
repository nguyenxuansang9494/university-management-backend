package uit.edu.vn.universitymanagement.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class SearchParamDto {
    private final String key;
    private final String value;

}
