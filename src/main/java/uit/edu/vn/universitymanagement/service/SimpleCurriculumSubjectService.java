package uit.edu.vn.universitymanagement.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uit.edu.vn.universitymanagement.model.entity.CurriculumSubject;
import uit.edu.vn.universitymanagement.model.entity.Subject;
import uit.edu.vn.universitymanagement.repository.CurriculumSubjectRepository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SimpleCurriculumSubjectService extends AbstractCrudService<CurriculumSubject, CurriculumSubjectRepository> {
    private final SimplePrerequisiteSubjectService prerequisiteSubjectService;

    public SimpleCurriculumSubjectService(CurriculumSubjectRepository repository, SimplePrerequisiteSubjectService prerequisiteSubjectService) {
        super(repository);
        this.prerequisiteSubjectService = prerequisiteSubjectService;
    }


    @Transactional
    public void savePrerequisiteSubjects(Authentication authentication, CurriculumSubject object, Set<Subject> prerequisiteSubjects) {
        List<Long> subjectIds = prerequisiteSubjects.stream().map(Subject::getId).collect(Collectors.toList());
        List<CurriculumSubject> existedCurriculumSubjects = repository.findAllByCurriculumIdAndSubjectIdIn(object.getCurriculum().getId(), subjectIds);
        Map<Subject, CurriculumSubject> existedCurSubMap = existedCurriculumSubjects.stream().collect(Collectors.toMap(CurriculumSubject::getSubject, e -> e));
        List<CurriculumSubject> savedCurSubs = prerequisiteSubjects.stream().map(e -> {
            if (existedCurSubMap.containsKey(e)) {
                CurriculumSubject rs = existedCurSubMap.get(e);
                rs.setOptional(rs.isOptional() && object.isOptional());
                return rs;
            }
            return new CurriculumSubject(null, object.getCurriculum(), e, object.isOptional(), null);
        }).collect(Collectors.toList());
        super.create(authentication, savedCurSubs);
    }

    @Override
    @Transactional
    public CurriculumSubject create(Authentication authentication, CurriculumSubject object) {
        Set<Subject> prerequisiteSubjects = prerequisiteSubjectService.explorePrerequisite(object.getSubject());
        savePrerequisiteSubjects(authentication, object, prerequisiteSubjects);
        return super.create(authentication, object);
    }


    @Override
    @Transactional
    public CurriculumSubject update(Authentication authentication, CurriculumSubject object) {
        delete(authentication, object.getId());
        return create(authentication, object);
    }

    @Override
    public void delete(Authentication authentication, Long id) {
        Set<Subject> prerequisiteSubjects = prerequisiteSubjectService.exploreDependants(id);
        List<Long> ids = prerequisiteSubjects.stream()
                .map(Subject::getId)
                .collect(Collectors.toList());
        super.delete(authentication, ids);
        super.delete(authentication, id);
    }

    @Override
    @Transactional
    public List<CurriculumSubject> create(Authentication authentication, List<CurriculumSubject> objects) {
        for (CurriculumSubject object : objects
        ) {
            Set<Subject> prerequisiteSubjects = prerequisiteSubjectService.explorePrerequisite(object.getSubject());
            savePrerequisiteSubjects(authentication, object, prerequisiteSubjects);
        }
        return super.create(authentication, objects);
    }

    @Override
    @Transactional
    public List<CurriculumSubject> update(Authentication authentication, List<CurriculumSubject> objects) {
        List<Long> ids = objects.stream()
                .map(CurriculumSubject::getId)
                .collect(Collectors.toList());
        delete(authentication, ids);
        return create(authentication, objects);
    }

    @Override
    public void delete(Authentication authentication, List<Long> ids) {
        Set<Subject> prerequisiteSubjects = prerequisiteSubjectService.exploreDependants(ids);
        ids.addAll(prerequisiteSubjects.stream()
                .map(Subject::getId)
                .collect(Collectors.toList()));
        super.delete(authentication, ids);
    }


}
