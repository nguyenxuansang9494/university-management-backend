package uit.edu.vn.universitymanagement.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uit.edu.vn.universitymanagement.exception.FailedToDeleteRequiredSubjectException;
import uit.edu.vn.universitymanagement.exception.MissingPrerequisiteSubjectException;
import uit.edu.vn.universitymanagement.model.entity.CurriculumSubject;
import uit.edu.vn.universitymanagement.model.entity.PrerequisiteSubject;
import uit.edu.vn.universitymanagement.repository.CommonJpaRepository;
import uit.edu.vn.universitymanagement.repository.PrerequisiteSubjectRepository;

import java.util.List;

@Service
public class CrudCurriculumSubjectService extends AbstractCrudService<CurriculumSubject> {
    private final CurriculumSubjectLogicService curriculumSubjectLogicService;
    private final PrerequisiteSubjectRepository prerequisiteSubjectRepository;

    public CrudCurriculumSubjectService(CommonJpaRepository<CurriculumSubject, Long> repository, CurriculumSubjectLogicService curriculumSubjectLogicService, PrerequisiteSubjectRepository prerequisiteSubjectRepository) {
        super(repository);
        this.curriculumSubjectLogicService = curriculumSubjectLogicService;
        this.prerequisiteSubjectRepository = prerequisiteSubjectRepository;
    }

    @Override
    @Transactional
    public CurriculumSubject create(Authentication authentication, CurriculumSubject object) {
        List<PrerequisiteSubject> prerequisiteSubjects = prerequisiteSubjectRepository.findAllBySubjectId(object.getSubject().getId());
        if (curriculumSubjectLogicService.isAddable(object, prerequisiteSubjects)) {
            curriculumSubjectLogicService.updatePrerequisiteSubjects(object, prerequisiteSubjects);
            return super.create(authentication, object);
        }
        throw new MissingPrerequisiteSubjectException();
    }

    @Override
    @Transactional
    public CurriculumSubject update(Authentication authentication, CurriculumSubject object) {
        delete(authentication, object.getId());
        return create(authentication, object);
    }

    @Override
    @Transactional
    public CurriculumSubject delete(Authentication authentication, Long id) {
        CurriculumSubject curriculumSubject = super.delete(authentication, id);
        if (!curriculumSubjectLogicService.isDeletable(curriculumSubject))
            throw new FailedToDeleteRequiredSubjectException();
        return curriculumSubject;
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
    @Transactional
    public List<CurriculumSubject> delete(Authentication authentication, List<Long> ids) {
        List<CurriculumSubject> curriculumSubjects = super.delete(authentication, ids);
        if (!curriculumSubjectLogicService.isDeletable(curriculumSubjects))
            throw new FailedToDeleteRequiredSubjectException();
        return curriculumSubjects;
    }
}
