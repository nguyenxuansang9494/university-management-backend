package uit.edu.vn.universitymanagement.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uit.edu.vn.universitymanagement.exception.ResourceNotFoundException;
import uit.edu.vn.universitymanagement.model.entity.CurriculumSubject;
import uit.edu.vn.universitymanagement.model.entity.Subject;
import uit.edu.vn.universitymanagement.repository.CurriculumSubjectRepository;
import uit.edu.vn.universitymanagement.util.ManagedModelUtils;

import java.util.ArrayList;
import java.util.LinkedList;
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
        List<Long> subjectIds = ManagedModelUtils.convertToLongList(prerequisiteSubjects);
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

    @Transactional
    public void deleteAllDependants(Authentication authentication, CurriculumSubject curriculumSubject) {
        List<Subject> dependants = new ArrayList<>(prerequisiteSubjectService.exploreDependants(curriculumSubject.getSubject().getId()));
        List<Long> ids = ManagedModelUtils.convertToLongList(repository.findAllByCurriculumAndSubjectIn(curriculumSubject.getCurriculum(), dependants));
        super.delete(authentication, ids);
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
        delete(authentication, object);
        return create(authentication, object);
    }

    @Transactional
    public void delete(Authentication authentication, CurriculumSubject curriculumSubject) {
        deleteAllDependants(authentication, curriculumSubject);
        super.delete(authentication, curriculumSubject.getId());
    }

    @Override
    @Transactional
    public void delete(Authentication authentication, Long id) {
        CurriculumSubject curriculumSubject = repository.findById(id).orElseThrow(ResourceNotFoundException::new);
        deleteAllDependants(authentication, curriculumSubject);
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
        deleteObjects(authentication, objects);
        return create(authentication, objects);
    }

    @Transactional
    public void deleteObjects(Authentication authentication, List<CurriculumSubject> curriculumSubjects) {
        for (var e : curriculumSubjects) {
            deleteAllDependants(authentication, e);
        }
        List<Long> ids = ManagedModelUtils.convertToLongList(curriculumSubjects);
        super.delete(authentication, ids);
    }

    @Override
    @Transactional
    public void delete(Authentication authentication, List<Long> ids) {
        List<CurriculumSubject> curriculumSubjects = repository.findAllByIdIn(ids);
        deleteObjects(authentication, curriculumSubjects);
    }

    public void autoDelete(Authentication authentication, CurriculumSubject curriculumSubject) {
        Set<Subject> prerequisites = prerequisiteSubjectService.explorePrerequisite(curriculumSubject.getSubject().getId());
        List<Subject> deletedSubject = new LinkedList<>();
        prerequisites.forEach(e -> {
            Set<Subject> dependants = prerequisiteSubjectService.exploreDependants(e.getId());
            if (dependants.size() == 1 && dependants.contains(e)) {
                deletedSubject.add(e);
            }
        });
        List<CurriculumSubject> deletedCurSub = repository.findAllByCurriculumAndSubjectIn(curriculumSubject.getCurriculum(), deletedSubject);
        List<Long> deletedIds = ManagedModelUtils.convertToLongList(deletedCurSub);
        super.delete(authentication, deletedIds);
    }
}
