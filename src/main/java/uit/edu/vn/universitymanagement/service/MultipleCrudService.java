package uit.edu.vn.universitymanagement.service;

import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import uit.edu.vn.universitymanagement.model.ManagedEntity;

import java.util.List;

public interface MultipleCrudService<T extends ManagedEntity> {
    List<T> create(Authentication authentication, List<T> objects);

    Page<T> read(Authentication authentication, List<Long> ids, int page, int size);

    List<T> read(Authentication authentication, List<Long> ids);

    List<T> update(Authentication authentication, List<T> objects);

    void deleteByIdIn(Authentication authentication, List<Long> ids);
}
