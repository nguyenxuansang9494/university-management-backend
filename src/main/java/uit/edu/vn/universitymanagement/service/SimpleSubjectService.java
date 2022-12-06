package uit.edu.vn.universitymanagement.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import uit.edu.vn.universitymanagement.model.entity.Subject;
import uit.edu.vn.universitymanagement.repository.SubjectRepository;
import uit.edu.vn.universitymanagement.util.AuthenticationUtils;

@Service
public class SimpleSubjectService extends AbstractCrudService<Subject, SubjectRepository> {
    public SimpleSubjectService(SubjectRepository repository) {
        super(repository);
    }

    @Override
    public boolean notAuthorize(Authentication authentication, ActionType actionType, Subject object) {
        return AuthenticationUtils.allowAllToReadButOnlyModeratorAboveToWrite(authentication, actionType);
    }
}
