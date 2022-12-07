package uit.edu.vn.universitymanagement.service;

import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import uit.edu.vn.universitymanagement.model.ManagedModel;

import java.util.List;

public interface MultipleCrudService<T extends ManagedModel> {
    List<T> create(Authentication authentication, List<T> objects);

    Page<T> read(Authentication authentication, List<Long> ids, Integer page, Integer size);

    List<T> read(Authentication authentication, List<Long> ids);

    List<T> update(Authentication authentication, List<T> objects);

    void delete(Authentication authentication, List<Long> ids);
}
