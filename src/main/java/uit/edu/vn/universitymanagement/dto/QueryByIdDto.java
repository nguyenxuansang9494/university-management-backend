package uit.edu.vn.universitymanagement.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class QueryByIdDto {
    private final List<Long> ids;
    private final boolean isPaged;
    private final Integer page;
    private final Integer size;
}
