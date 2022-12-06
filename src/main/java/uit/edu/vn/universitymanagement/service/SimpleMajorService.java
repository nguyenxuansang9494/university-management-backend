package uit.edu.vn.universitymanagement.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import uit.edu.vn.universitymanagement.model.entity.Major;
import uit.edu.vn.universitymanagement.repository.CommonJpaRepository;
import uit.edu.vn.universitymanagement.util.AuthenticationUtils;

@Service
public class SimpleMajorService extends AbstractCrudService<Major> {
    public SimpleMajorService(CommonJpaRepository<Major, Long> repository) {
        super(repository);
    }

    @Override
    public boolean notAuthorize(Authentication authentication, ActionType actionType, Major object) {
        return AuthenticationUtils.allowAllToReadOnlyAboveModeratorToWrite(authentication, actionType);
    }
}
