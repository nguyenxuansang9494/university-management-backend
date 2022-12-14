package uit.edu.vn.universitymanagement.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import uit.edu.vn.universitymanagement.exception.CommonRuntimeException;
import uit.edu.vn.universitymanagement.exception.ErrorType;
import uit.edu.vn.universitymanagement.model.entity.PrerequisiteSubject;
import uit.edu.vn.universitymanagement.repository.PrerequisiteSubjectRepository;

import java.util.List;

@Service
public class CrudPrerequisiteSubjectService extends AbstractCrudService<PrerequisiteSubject> {
    public final PrerequisiteSubjectLogicService prerequisiteSubjectLogicService;
    private static final String CYCLIC_DEPENDENCY_MESSAGE = "cyclic dependency";
    public CrudPrerequisiteSubjectService(PrerequisiteSubjectRepository repository, PrerequisiteSubjectLogicService prerequisiteSubjectLogicService) {
        super(repository);
        this.prerequisiteSubjectLogicService = prerequisiteSubjectLogicService;
    }

    @Override
    public PrerequisiteSubject create(Authentication authentication, PrerequisiteSubject object) {
        if (prerequisiteSubjectLogicService.checkCyclicDependency(object)) {
            throw new CommonRuntimeException(ErrorType.BAD_REQUEST, CYCLIC_DEPENDENCY_MESSAGE);
        }
        return super.create(authentication, object);
    }

    @Override
    public PrerequisiteSubject update(Authentication authentication, PrerequisiteSubject object) {
        if (prerequisiteSubjectLogicService.checkCyclicDependency(object)) {
            throw new CommonRuntimeException(ErrorType.BAD_REQUEST, CYCLIC_DEPENDENCY_MESSAGE);
        }
        return super.update(authentication, object);
    }

    @Override
    public List<PrerequisiteSubject> create(Authentication authentication, List<PrerequisiteSubject> objects) {
        objects.forEach(object -> {
            if (prerequisiteSubjectLogicService.checkCyclicDependency(object)) {
                throw new CommonRuntimeException(ErrorType.BAD_REQUEST, CYCLIC_DEPENDENCY_MESSAGE);
            }
        });
        return super.create(authentication, objects);
    }

    @Override
    public List<PrerequisiteSubject> update(Authentication authentication, List<PrerequisiteSubject> objects) {
        objects.forEach(object -> {
            if (prerequisiteSubjectLogicService.checkCyclicDependency(object)) {
                throw new CommonRuntimeException(ErrorType.BAD_REQUEST, CYCLIC_DEPENDENCY_MESSAGE);
            }
        });
        return super.update(authentication, objects);
    }
}
