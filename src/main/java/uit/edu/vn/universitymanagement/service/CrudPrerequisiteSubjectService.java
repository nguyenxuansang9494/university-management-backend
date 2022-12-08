package uit.edu.vn.universitymanagement.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import uit.edu.vn.universitymanagement.exception.CyclicDependencyException;
import uit.edu.vn.universitymanagement.model.entity.PrerequisiteSubject;
import uit.edu.vn.universitymanagement.repository.PrerequisiteSubjectRepository;

import java.util.List;

@Service
public class CrudPrerequisiteSubjectService extends AbstractCrudService<PrerequisiteSubject, PrerequisiteSubjectRepository> {
    public final PrerequisiteSubjectLogicService prerequisiteSubjectLogicService;
    public CrudPrerequisiteSubjectService(PrerequisiteSubjectRepository repository, PrerequisiteSubjectLogicService prerequisiteSubjectLogicService) {
        super(repository);
        this.prerequisiteSubjectLogicService = prerequisiteSubjectLogicService;
    }

    @Override
    public PrerequisiteSubject create(Authentication authentication, PrerequisiteSubject object) {
        if (prerequisiteSubjectLogicService.checkCyclicDependency(object)) {
            throw new CyclicDependencyException();
        }
        return super.create(authentication, object);
    }

    @Override
    public PrerequisiteSubject update(Authentication authentication, PrerequisiteSubject object) {
        if (prerequisiteSubjectLogicService.checkCyclicDependency(object)) {
            throw new CyclicDependencyException();
        }
        return super.update(authentication, object);
    }

    @Override
    public List<PrerequisiteSubject> create(Authentication authentication, List<PrerequisiteSubject> objects) {
        objects.forEach(object -> {
            if (prerequisiteSubjectLogicService.checkCyclicDependency(object)) {
                throw new CyclicDependencyException();
            }
        });
        return super.create(authentication, objects);
    }

    @Override
    public List<PrerequisiteSubject> update(Authentication authentication, List<PrerequisiteSubject> objects) {
        objects.forEach(object -> {
            if (prerequisiteSubjectLogicService.checkCyclicDependency(object)) {
                throw new CyclicDependencyException();
            }
        });
        return super.update(authentication, objects);
    }
}
