package uit.edu.vn.universitymanagement.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import uit.edu.vn.universitymanagement.model.entity.Curriculum;
import uit.edu.vn.universitymanagement.repository.CurriculumRepository;
import uit.edu.vn.universitymanagement.util.AuthenticationUtils;

@Service
public class SimpleCurriculumService extends AbstractCrudService<Curriculum, CurriculumRepository> {

    public SimpleCurriculumService(CurriculumRepository repository) {
        super(repository);
    }

    @Override
    public boolean notAuthorize(Authentication authentication, ActionType actionType, Curriculum object) {
        return AuthenticationUtils.allowAllToReadButOnlyModeratorAboveToWrite(authentication, actionType);
    }
}
