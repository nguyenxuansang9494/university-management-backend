package uit.edu.vn.universitymanagement.service;

import org.springframework.security.core.Authentication;
import uit.edu.vn.universitymanagement.model.ManagedModel;

public interface SingleCrudService<T extends ManagedModel> {
    T create(Authentication authentication, T object);

    T read(Authentication authentication, Long id);

    T update(Authentication authentication, T object);

    T delete(Authentication authentication, Long id);
}
