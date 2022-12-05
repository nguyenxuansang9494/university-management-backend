package uit.edu.vn.universitymanagement.service;

import org.springframework.security.core.Authentication;
import uit.edu.vn.universitymanagement.model.ManagedEntity;

public interface SingleCrudService<T extends ManagedEntity> {
    T create(Authentication authentication, T object);

    T read(Authentication authentication, long id);

    T update(Authentication authentication, T object);

    void delete(Authentication authentication, long id);
}
