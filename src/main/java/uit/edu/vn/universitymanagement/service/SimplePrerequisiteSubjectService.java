package uit.edu.vn.universitymanagement.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import uit.edu.vn.universitymanagement.exception.CyclicDependencyException;
import uit.edu.vn.universitymanagement.exception.PermissionDeniedException;
import uit.edu.vn.universitymanagement.model.entity.PrerequisiteSubject;
import uit.edu.vn.universitymanagement.model.entity.Subject;
import uit.edu.vn.universitymanagement.repository.PrerequisiteSubjectRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SimplePrerequisiteSubjectService extends AbstractCrudService<PrerequisiteSubject, PrerequisiteSubjectRepository> {
    public SimplePrerequisiteSubjectService(PrerequisiteSubjectRepository repository) {
        super(repository);
    }

    @Override
    public PrerequisiteSubject create(Authentication authentication, PrerequisiteSubject object) {
        checkCyclicDependency(object);
        return super.create(authentication, object);
    }

    @Override
    public PrerequisiteSubject update(Authentication authentication, PrerequisiteSubject object) {
        checkCyclicDependency(object);
        return super.update(authentication, object);
    }

    @Override
    public List<PrerequisiteSubject> create(Authentication authentication, List<PrerequisiteSubject> objects) {
        objects.forEach(this::checkCyclicDependency);
        return super.create(authentication, objects);
    }

    @Override
    public List<PrerequisiteSubject> update(Authentication authentication, List<PrerequisiteSubject> objects) {
        objects.forEach(this::checkCyclicDependency);
        return super.update(authentication, objects);
    }

    public List<PrerequisiteSubject> findBySubjectId(Authentication authentication, Long id) {
        if (!authorize(authentication, ActionType.READ, null)) {
            throw new PermissionDeniedException();
        }
        return repository.findAllBySubjectId(id);
    }

    public List<PrerequisiteSubject> findByPrerequisiteId(Authentication authentication, Long id) {
        if (!authorize(authentication, ActionType.READ, null)) {
            throw new PermissionDeniedException();
        }
        return repository.findAllByPrerequisiteId(id);
    }

    public Set<Subject> exploreDependants(Long id) {
        List<PrerequisiteSubject> prerequisiteSubjects = repository.findAllByPrerequisiteId(id);
        return getDependantSubjects(prerequisiteSubjects);
    }

    public Set<Subject> exploreDependants(List<Long> ids) {
        List<PrerequisiteSubject> prerequisiteSubjects = repository.findAllByPrerequisiteIdIn(ids);
        return getDependantSubjects(prerequisiteSubjects);
    }

    private Set<Subject> getDependantSubjects(List<PrerequisiteSubject> prerequisiteSubjects) {
        Set<Subject> exploredSet = new HashSet<>();
        while (!prerequisiteSubjects.isEmpty()) {
            List<Subject> subjects = prerequisiteSubjects.stream()
                    .map(PrerequisiteSubject::getSubject)
                    .collect(Collectors.toList());
            exploredSet.addAll(subjects);
            List<Long> ids = subjects.stream()
                    .map(Subject::getId)
                    .collect(Collectors.toList());
            prerequisiteSubjects = repository.findAllByPrerequisiteIdIn(ids);
        }
        return exploredSet;
    }

    public Set<Subject> explorePrerequisite(Subject subject) {
        return explorePrerequisite(subject.getId());
    }

    public Set<Subject> explorePrerequisite(Long id) {
        List<PrerequisiteSubject> prerequisiteSubjects = repository.findAllBySubjectId(id);
        Set<Subject> exploredSet = new HashSet<>();
        while (!prerequisiteSubjects.isEmpty()) {
            List<Subject> exploredSubjects = prerequisiteSubjects.stream()
                    .map(PrerequisiteSubject::getPrerequisite)
                    .collect(Collectors.toList());
            exploredSet.addAll(exploredSubjects);
            List<Long> prerequisiteIds = exploredSubjects.stream()
                    .map(Subject::getId)
                    .collect(Collectors.toList());
            prerequisiteSubjects = repository.findAllBySubjectIdIn(prerequisiteIds);
        }
        return exploredSet;
    }

    private void checkCyclicDependency(PrerequisiteSubject object) {
        Set<Long> exploredPrerequisiteSubjectIds = explorePrerequisite(object.getPrerequisite()).stream()
                .map(Subject::getId)
                .collect(Collectors.toSet());
        if (exploredPrerequisiteSubjectIds.contains(object.getSubject().getId())) {
            throw new CyclicDependencyException();
        }
    }
}
