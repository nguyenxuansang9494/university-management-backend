package uit.edu.vn.universitymanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uit.edu.vn.universitymanagement.authorization.Authorizer;
import uit.edu.vn.universitymanagement.model.entity.Curriculum;
import uit.edu.vn.universitymanagement.model.entity.CurriculumSubject;
import uit.edu.vn.universitymanagement.model.entity.PrerequisiteSubject;
import uit.edu.vn.universitymanagement.model.entity.Subject;
import uit.edu.vn.universitymanagement.repository.CurriculumSubjectRepository;
import uit.edu.vn.universitymanagement.repository.PrerequisiteSubjectRepository;

import java.util.ArrayList;
import java.util.List;
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

    public boolean isAddable(List<CurriculumSubject> curriculumSubjects) {
        Set<Long> addedSubjects = curriculumSubjects.stream()
                .map(CurriculumSubject::getSubject)
                .map(Subject::getId)
                .collect(Collectors.toSet());
        Set<Long> prerequisites = prerequisiteSubjectRepository.findAllBySubjectIdIn(new ArrayList<>(addedSubjects))
                .stream()
                .map(PrerequisiteSubject::getPrerequisite)
                .map(Subject::getId)
                .collect(Collectors.toSet());
        Set<Long> curriculumIds = curriculumSubjects.stream()
                .map(CurriculumSubject::getCurriculum)
                .map(Curriculum::getId)
                .collect(Collectors.toSet());
        Set<Long> existedInCurSubject = curriculumSubjectRepository.findAllByCurriculumIdInAndSubjectIdIn(new ArrayList<>(curriculumIds), new ArrayList<>(prerequisites))
                .stream()
                .map(CurriculumSubject::getSubject)
                .map(Subject::getId)
                .collect(Collectors.toSet());
        return prerequisites.stream()
                .map(e -> addedSubjects.contains(e) || existedInCurSubject.contains(e))
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
}
