package uit.edu.vn.universitymanagement.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uit.edu.vn.universitymanagement.exception.CommonRuntimeException;
import uit.edu.vn.universitymanagement.exception.ErrorType;
import uit.edu.vn.universitymanagement.model.entity.CurriculumSubject;
import uit.edu.vn.universitymanagement.model.entity.PrerequisiteSubject;
import uit.edu.vn.universitymanagement.model.entity.Subject;
import uit.edu.vn.universitymanagement.repository.CommonJpaRepository;
import uit.edu.vn.universitymanagement.repository.CurriculumSubjectRepository;
import uit.edu.vn.universitymanagement.repository.PrerequisiteSubjectRepository;
import uit.edu.vn.universitymanagement.util.ManagedModelUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CrudCurriculumSubjectService extends AbstractCrudService<CurriculumSubject> {
    private final LogicCurriculumSubjectService logicCurriculumSubjectService;
    private final PrerequisiteSubjectRepository prerequisiteSubjectRepository;

    public CrudCurriculumSubjectService(CommonJpaRepository<CurriculumSubject, Long> repository, LogicCurriculumSubjectService logicCurriculumSubjectService, PrerequisiteSubjectRepository prerequisiteSubjectRepository) {
        super(repository);
        this.logicCurriculumSubjectService = logicCurriculumSubjectService;
        this.prerequisiteSubjectRepository = prerequisiteSubjectRepository;
    }

    @Override
    @Transactional
    public CurriculumSubject create(Authentication authentication, CurriculumSubject object) {
        List<PrerequisiteSubject> prerequisiteSubjects = prerequisiteSubjectRepository.findAllBySubjectId(object.getSubject().getId());
        if (logicCurriculumSubjectService.isAddable(object, prerequisiteSubjects)) {
            logicCurriculumSubjectService.updatePrerequisiteSubjects(object, prerequisiteSubjects);
            return super.create(authentication, object);
        }
        throw new CommonRuntimeException(ErrorType.BAD_REQUEST, "prerequisite subjects are missed");
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
        if (!logicCurriculumSubjectService.isDeletable(curriculumSubject))
            throw new CommonRuntimeException(ErrorType.BAD_REQUEST, "subject is required");
        return curriculumSubject;
    }

    @Override
    @Transactional
    public List<CurriculumSubject> create(Authentication authentication, List<CurriculumSubject> objects) {
        if (objects.isEmpty())
            return objects;
        Long curriculumId = objects.get(0).getCurriculum().getId();
        List<Long> subjectIds = objects.stream()
                .map(CurriculumSubject::getSubject)
                .map(Subject::getId)
                .collect(Collectors.toList());
        Set<PrerequisiteSubject> prerequisiteSubjects = new HashSet<>(prerequisiteSubjectRepository.findAllBySubjectIdIn(subjectIds));
        List<Long> prerequisiteIds = prerequisiteSubjects.stream()
                .map(PrerequisiteSubject::getPrerequisite)
                .map(Subject::getId)
                .collect(Collectors.toList());
        List<CurriculumSubject> existedCurSub = ((CurriculumSubjectRepository) repository).findAllByCurriculumIdAndSubjectIdIn(curriculumId, prerequisiteIds);
        if (!logicCurriculumSubjectService.isAddable(objects, existedCurSub, prerequisiteSubjects)) {
            throw new CommonRuntimeException(ErrorType.BAD_REQUEST, "prerequisite subjects are missed");
        }
        logicCurriculumSubjectService.updatePrerequisiteSubjects(objects, existedCurSub, prerequisiteSubjects);
        return super.create(authentication, objects);
    }

    @Override
    @Transactional
    public List<CurriculumSubject> update(Authentication authentication, List<CurriculumSubject> objects) {
        List<Long> objectIds = ManagedModelUtils.convertToLongList(objects);
        delete(authentication, objectIds);
        return create(authentication, objects);
    }

    @Override
    @Transactional
    public List<CurriculumSubject> delete(Authentication authentication, List<Long> ids) {
        List<CurriculumSubject> curriculumSubjects = super.delete(authentication, ids);
        if (!logicCurriculumSubjectService.isDeletable(curriculumSubjects))
            throw new CommonRuntimeException(ErrorType.BAD_REQUEST, "subjects are required");
        return curriculumSubjects;
    }
}
