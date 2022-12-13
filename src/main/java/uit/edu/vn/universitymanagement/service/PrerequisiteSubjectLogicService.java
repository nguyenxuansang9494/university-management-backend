package uit.edu.vn.universitymanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import uit.edu.vn.universitymanagement.authorization.ActionType;
import uit.edu.vn.universitymanagement.authorization.Authorizer;
import uit.edu.vn.universitymanagement.exception.CommonRuntimeException;
import uit.edu.vn.universitymanagement.exception.ErrorType;
import uit.edu.vn.universitymanagement.model.entity.PrerequisiteSubject;
import uit.edu.vn.universitymanagement.model.entity.Subject;
import uit.edu.vn.universitymanagement.repository.PrerequisiteSubjectRepository;
import uit.edu.vn.universitymanagement.util.ManagedModelUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PrerequisiteSubjectLogicService implements Authorizer<PrerequisiteSubject> {

    private final PrerequisiteSubjectRepository repository;

    public List<PrerequisiteSubject> findBySubjectId(Authentication authentication, Long id) {
        if (!authorize(authentication, ActionType.READ, null)) {
            throw new CommonRuntimeException(ErrorType.PERMISSION_DENIED);
        }
        return repository.findAllBySubjectId(id);
    }

    public List<PrerequisiteSubject> findByPrerequisiteId(Authentication authentication, Long id) {
        if (!authorize(authentication, ActionType.READ, null)) {
            throw new CommonRuntimeException(ErrorType.PERMISSION_DENIED);
        }
        return repository.findAllByPrerequisiteId(id);
    }

    public Set<Subject> exploreDependants(Long id) {
        List<PrerequisiteSubject> dependants = repository.findAllByPrerequisiteId(id);
        Set<Subject> exploredSet = new HashSet<>();
        while (!dependants.isEmpty()) {
            List<Subject> subjects = dependants.stream()
                    .map(PrerequisiteSubject::getSubject)
                    .collect(Collectors.toList());
            exploredSet.addAll(subjects);
            List<Long> ids = ManagedModelUtils.convertToLongList(subjects);
            dependants = repository.findAllByPrerequisiteIdIn(ids);
        }
        return exploredSet;
    }

    public Set<Subject> explorePrerequisite(Long id) {
        List<PrerequisiteSubject> prerequisite = repository.findAllBySubjectId(id);
        Set<Subject> exploredSet = new HashSet<>();
        while (!prerequisite.isEmpty()) {
            List<Subject> exploredSubjects = prerequisite.stream()
                    .map(PrerequisiteSubject::getPrerequisite)
                    .collect(Collectors.toList());
            exploredSet.addAll(exploredSubjects);
            List<Long> prerequisiteIds = ManagedModelUtils.convertToLongList(exploredSubjects);
            prerequisite = repository.findAllBySubjectIdIn(prerequisiteIds);
        }
        return exploredSet;
    }

    public boolean checkCyclicDependency(PrerequisiteSubject object) {
        Set<Long> exploredPrerequisiteSubjectIds = explorePrerequisite(object.getPrerequisite().getId()).stream()
                .map(Subject::getId)
                .collect(Collectors.toSet());
        return exploredPrerequisiteSubjectIds.contains(object.getSubject().getId());
    }
}
