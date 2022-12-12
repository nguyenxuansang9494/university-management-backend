package uit.edu.vn.universitymanagement.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uit.edu.vn.universitymanagement.exception.FailedToDeleteRequiredSubjectException;
import uit.edu.vn.universitymanagement.exception.MissingPrerequisiteSubjectException;
import uit.edu.vn.universitymanagement.model.entity.CurriculumSubject;
import uit.edu.vn.universitymanagement.model.entity.PrerequisiteSubject;
import uit.edu.vn.universitymanagement.model.entity.Subject;
import uit.edu.vn.universitymanagement.repository.CommonJpaRepository;
import uit.edu.vn.universitymanagement.repository.CurriculumSubjectRepository;
import uit.edu.vn.universitymanagement.repository.PrerequisiteSubjectRepository;
import uit.edu.vn.universitymanagement.util.ManagedModelUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
    @Transactional
    public List<CurriculumSubject> create(Authentication authentication, List<CurriculumSubject> objects) {
        Map<Long, List<CurriculumSubject>> curMap = new HashMap<>();
        List<CurriculumSubject> result = new LinkedList<>();
        for (CurriculumSubject object : objects) {
            if (!curMap.containsKey(object.getCurriculum().getId())) {
                curMap.put(object.getCurriculum().getId(), new LinkedList<>());
            }
            curMap.get(object.getCurriculum().getId()).add(object);
        }
        for (Map.Entry<Long, List<CurriculumSubject>> entry : curMap.entrySet()) {
            List<CurriculumSubject> curSubs = entry.getValue();
            List<Long> subjectIds = curSubs.stream()
                    .map(CurriculumSubject::getSubject)
                    .map(Subject::getId)
                    .collect(Collectors.toList());
            Set<PrerequisiteSubject> prerequisiteSubjects = new HashSet<>(prerequisiteSubjectRepository.findAllBySubjectIdIn(subjectIds));
            List<Long> prerequisiteIds = prerequisiteSubjects.stream()
                    .map(PrerequisiteSubject::getPrerequisite)
                    .map(Subject::getId)
                    .collect(Collectors.toList());
            List<CurriculumSubject> existedCurSubs = ((CurriculumSubjectRepository) repository).findAllByCurriculumIdAndSubjectIdIn(entry.getKey(), prerequisiteIds);
            if (!curriculumSubjectLogicService.isAddable(curSubs, existedCurSubs, prerequisiteSubjects))
                continue;
            curriculumSubjectLogicService.updatePrerequisiteSubjects(curSubs, existedCurSubs, prerequisiteSubjects);
            result.addAll(super.create(authentication, curSubs));
        }
        return result;
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
        if (!curriculumSubjectLogicService.isDeletable(curriculumSubjects))
            throw new FailedToDeleteRequiredSubjectException();
        return curriculumSubjects;
    }
}
