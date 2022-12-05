package uit.edu.vn.universitymanagement.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class SearchParam {
    private final String key;
    private final String value;

}
