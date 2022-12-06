package uit.edu.vn.universitymanagement.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import uit.edu.vn.universitymanagement.model.entity.Faculty;
import uit.edu.vn.universitymanagement.repository.CommonJpaRepository;
import uit.edu.vn.universitymanagement.util.AuthenticationUtils;

@Service
public class SimpleFacultyService extends AbstractCrudService<Faculty> {
    public SimpleFacultyService(CommonJpaRepository<Faculty, Long> repository) {
        super(repository);
    }

    @Override
    public boolean notAuthorize(Authentication authentication, ActionType actionType, Faculty object) {
        return AuthenticationUtils.allowAllToReadOnlyAboveModeratorToWrite(authentication, actionType);
    }
}
