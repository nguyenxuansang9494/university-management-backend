package uit.edu.vn.universitymanagement.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uit.edu.vn.universitymanagement.model.entity.CurriculumSubject;
import uit.edu.vn.universitymanagement.model.entity.Subject;
import uit.edu.vn.universitymanagement.repository.CurriculumRepository;
import uit.edu.vn.universitymanagement.repository.CurriculumSubjectRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SimpleCurriculumSubjectService extends AbstractCrudService<CurriculumSubject, CurriculumSubjectRepository> {
    private final SimpleCurriculumService curriculumService;
    private final SimplePrerequisiteSubjectService prerequisiteSubjectService;

    public SimpleCurriculumSubjectService(CurriculumSubjectRepository repository, CurriculumRepository curriculumRepository, SimpleCurriculumService curriculumService, SimplePrerequisiteSubjectService prerequisiteSubjectService) {
        super(repository);
        this.curriculumService = curriculumService;
        this.prerequisiteSubjectService = prerequisiteSubjectService;
    }


    public void savePrerequisiteSubjects(Authentication authentication, CurriculumSubject object) {
        Set<Subject> prerequisiteSubjects = prerequisiteSubjectService.explorePrerequisite(object.getSubject());
        List<CurriculumSubject> curriculumSubjects = prerequisiteSubjects.stream()
                .map(e -> new CurriculumSubject(null, object.getCurriculum(), e, object.isOptional(), null))
                .collect(Collectors.toList());
        super.create(authentication, curriculumSubjects);
    }

    @Transactional
    @Override
    public CurriculumSubject create(Authentication authentication, CurriculumSubject object) {
        return super.create(authentication, object);
    }


    @Override
    public CurriculumSubject update(Authentication authentication, CurriculumSubject object) {
        return super.update(authentication, object);
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
    public List<CurriculumSubject> create(Authentication authentication, List<CurriculumSubject> objects) {
        Set<Subject> subjects = new HashSet<>();
        objects.forEach(object -> {
            subjects.addAll(prerequisiteSubjectService.explorePrerequisite(object.getSubject()));
        });

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


}
