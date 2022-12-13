package uit.edu.vn.universitymanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uit.edu.vn.universitymanagement.authorization.Authorizer;
import uit.edu.vn.universitymanagement.exception.CommonRuntimeException;
import uit.edu.vn.universitymanagement.exception.ErrorType;
import uit.edu.vn.universitymanagement.model.entity.CurriculumSubject;
import uit.edu.vn.universitymanagement.model.entity.PrerequisiteSubject;
import uit.edu.vn.universitymanagement.model.entity.Subject;
import uit.edu.vn.universitymanagement.repository.CurriculumSubjectRepository;
import uit.edu.vn.universitymanagement.repository.PrerequisiteSubjectRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CurriculumSubjectLogicService implements Authorizer<CurriculumSubject> {
    private final CurriculumSubjectRepository curriculumSubjectRepository;
    private final PrerequisiteSubjectRepository prerequisiteSubjectRepository;

    public boolean isAddable(CurriculumSubject curriculumSubject, List<PrerequisiteSubject> prerequisiteSubjects) {
        Set<Long> prerequisiteSubjectIds = prerequisiteSubjects
                .stream()
                .map(PrerequisiteSubject::getPrerequisite)
                .map(Subject::getId)
                .collect(Collectors.toSet());
        return curriculumSubjectRepository.countAllByCurriculumIdAndSubjectIdIn(curriculumSubject.getCurriculum().getId(), new ArrayList<>(prerequisiteSubjectIds)) == prerequisiteSubjectIds.size();
    }

    public boolean isAddable(List<CurriculumSubject> addedCurriculumSubjects, List<CurriculumSubject> existedCurSub, Set<PrerequisiteSubject> prerequisiteSubjects) {
        for (CurriculumSubject cur : addedCurriculumSubjects) {
            if (!Objects.equals(cur.getCurriculum().getId(), addedCurriculumSubjects.get(0).getCurriculum().getId()))
                throw new CommonRuntimeException(ErrorType.BAD_REQUEST, "more than one curriculum");
        }
        Set<Long> addedSubjects = addedCurriculumSubjects.stream()
                .map(CurriculumSubject::getSubject)
                .map(Subject::getId)
                .collect(Collectors.toSet());
        Set<Long> prerequisites = prerequisiteSubjects.stream()
                .map(PrerequisiteSubject::getPrerequisite)
                .map(Subject::getId)
                .collect(Collectors.toSet());
        Set<Long> existedInCurSubIds = existedCurSub.stream()
                .map(CurriculumSubject::getSubject)
                .map(Subject::getId)
                .collect(Collectors.toSet());
        return prerequisites.stream()
                .map(e -> addedSubjects.contains(e) || existedInCurSubIds.contains(e))
                .reduce(Boolean::logicalAnd)
                .orElse(false);
    }

    public boolean isDeletable(CurriculumSubject curriculumSubject) {
        Set<Long> dependantIds = prerequisiteSubjectRepository.findAllByPrerequisiteId(curriculumSubject.getSubject().getId())
                .stream()
                .map(PrerequisiteSubject::getSubject)
                .map(Subject::getId)
                .collect(Collectors.toSet());
        return curriculumSubjectRepository.countAllByCurriculumIdAndSubjectIdIn(curriculumSubject.getCurriculum().getId(), new ArrayList<>(dependantIds)) == 0;
    }


    public boolean isDeletable(List<CurriculumSubject> curriculumSubjects) {
        Set<Long> ids = curriculumSubjects.stream()
                .map(CurriculumSubject::getSubject)
                .map(Subject::getId)
                .collect(Collectors.toSet());
        Set<Long> curIds = curriculumSubjects.stream()
                .map(CurriculumSubject::getId)
                .collect(Collectors.toSet());
        Set<Long> dependantIds = prerequisiteSubjectRepository.findAllByPrerequisiteIdIn(new ArrayList<>(ids))
                .stream()
                .map(PrerequisiteSubject::getSubject)
                .map(Subject::getId)
                .collect(Collectors.toSet());
        return curriculumSubjectRepository.countAllByCurriculumIdInAndSubjectIdIn(new ArrayList<>(curIds), new ArrayList<>(dependantIds)) == 0;
    }

    public void updatePrerequisiteSubjects(CurriculumSubject curriculumSubject, List<PrerequisiteSubject> prerequisiteSubjects) {
        Set<Long> prerequisiteIds = prerequisiteSubjects
                .stream()
                .map(PrerequisiteSubject::getPrerequisite)
                .map(Subject::getId)
                .collect(Collectors.toSet());
        List<CurriculumSubject> curriculumSubjects = curriculumSubjectRepository.findAllByCurriculumIdAndSubjectIdIn(curriculumSubject.getCurriculum().getId(), new ArrayList<>(prerequisiteIds));
        curriculumSubjects.forEach(e -> e.setOptional(e.isOptional() && curriculumSubject.isOptional()));
        curriculumSubjectRepository.saveAll(curriculumSubjects);
    }

    public void updatePrerequisiteSubjects(List<CurriculumSubject> curriculumSubjects, List<CurriculumSubject> existedCurSubs, Set<PrerequisiteSubject> prerequisiteSubjectSet) {
        curriculumSubjects.addAll(existedCurSubs);
        curriculumSubjects.sort((e1, e2) -> calculateDepth(e2, prerequisiteSubjectSet) - calculateDepth(e1, prerequisiteSubjectSet));
        for (CurriculumSubject curSub : curriculumSubjects) {
            Set<Long> dependants = prerequisiteSubjectSet.stream()
                    .map(PrerequisiteSubject::getPrerequisite)
                    .map(Subject::getId)
                    .filter(e -> curSub.getSubject().getId().equals(e))
                    .collect(Collectors.toSet());
            curSub.setOptional(curriculumSubjects.stream()
                    .filter(e -> dependants.contains(e.getSubject().getId()))
                    .map(CurriculumSubject::isOptional)
                    .reduce(Boolean::logicalAnd)
                    .orElse(false));
        }
    }

    public int calculateDepth(CurriculumSubject curriculumSubject, Set<PrerequisiteSubject> prerequisiteSubjectSet) {
        Set<Long> prerequisites = prerequisiteSubjectSet.stream()
                .filter(e -> Objects.equals(e.getSubject().getId(), curriculumSubject.getSubject().getId()))
                .map(PrerequisiteSubject::getPrerequisite)
                .map(Subject::getId)
                .collect(Collectors.toSet());
        int depth = 0;
        while (!prerequisites.isEmpty()) {
            final Set<Long> constPrerequisites = prerequisites;
            depth++;
            prerequisites = prerequisiteSubjectSet.stream()
                    .filter(e -> constPrerequisites.contains(e.getSubject().getId()))
                    .map(PrerequisiteSubject::getPrerequisite)
                    .map(Subject::getId)
                    .collect(Collectors.toSet());
        }
        return depth;
    }
}
