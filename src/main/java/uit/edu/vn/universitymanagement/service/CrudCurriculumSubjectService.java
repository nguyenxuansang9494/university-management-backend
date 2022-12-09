package uit.edu.vn.universitymanagement.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uit.edu.vn.universitymanagement.exception.FailedToDeleteRequiredSubjectException;
import uit.edu.vn.universitymanagement.exception.MissingPrerequisiteSubjectException;
import uit.edu.vn.universitymanagement.exception.ResourceNotFoundException;
import uit.edu.vn.universitymanagement.model.entity.CurriculumSubject;
import uit.edu.vn.universitymanagement.repository.CommonJpaRepository;

import java.util.List;

@Service
public class CrudCurriculumSubjectService extends AbstractCrudService<CurriculumSubject> {
    private final CurriculumSubjectLogicService curriculumSubjectLogicService;

    public CrudCurriculumSubjectService(CommonJpaRepository<CurriculumSubject, Long> repository, CurriculumSubjectLogicService curriculumSubjectLogicService) {
        super(repository);
        this.curriculumSubjectLogicService = curriculumSubjectLogicService;
    }

    @Override
    @Transactional
    public CurriculumSubject create(Authentication authentication, CurriculumSubject object) {
        if (curriculumSubjectLogicService.isAddable(object)) {
            curriculumSubjectLogicService.updatePrerequisiteSubjects(object);
            return super.create(authentication, object);
        }
        throw new MissingPrerequisiteSubjectException();
    }

    @Override
    @Transactional
    public CurriculumSubject update(Authentication authentication, CurriculumSubject object) {
        delete(authentication, object);
        return create(authentication, object);
    }

    @Override
    public void delete(Authentication authentication, Long id) {
        final CurriculumSubject deletedObject = repository.findById(id).orElseThrow(ResourceNotFoundException::new);
        delete(authentication, deletedObject);
    }

    @Override
    public List<CurriculumSubject> create(Authentication authentication, List<CurriculumSubject> objects) {
        return super.create(authentication, objects);
    }

    @Override
    public List<CurriculumSubject> update(Authentication authentication, List<CurriculumSubject> objects) {
        return super.update(authentication, objects);
    }

    @Override
    public void delete(Authentication authentication, List<Long> ids) {
        super.delete(authentication, ids);
    }


    public void delete(Authentication authentication, CurriculumSubject deletedObject) {
        if (curriculumSubjectLogicService.isDeletable(deletedObject))
            super.delete(authentication, deletedObject.getId());
        else
            throw new FailedToDeleteRequiredSubjectException();
    }
}
