package uit.edu.vn.universitymanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uit.edu.vn.universitymanagement.authorization.Authorizer;
import uit.edu.vn.universitymanagement.model.entity.CurriculumSubject;
import uit.edu.vn.universitymanagement.model.entity.PrerequisiteSubject;
import uit.edu.vn.universitymanagement.model.entity.Subject;
import uit.edu.vn.universitymanagement.repository.CurriculumSubjectRepository;
import uit.edu.vn.universitymanagement.repository.PrerequisiteSubjectRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CurriculumSubjectLogicService implements Authorizer<CurriculumSubject> {
    private final CurriculumSubjectRepository curriculumSubjectRepository;
    private final PrerequisiteSubjectRepository prerequisiteSubjectRepository;
    public boolean isAddable(CurriculumSubject curriculumSubject) {
        List<Long> prerequisiteSubjects = prerequisiteSubjectRepository.findAllBySubjectId(curriculumSubject.getSubject().getId())
                .stream()
                .map(PrerequisiteSubject::getPrerequisite)
                .map(Subject::getId)
                .collect(Collectors.toList());
        return curriculumSubjectRepository.existsAllByCurriculumIdAndSubjectIdIn(curriculumSubject.getCurriculum().getId(), prerequisiteSubjects);
    }

//    public boolean isAddable(List<CurriculumSubject> curriculumSubjects) {
//
//    }

    public boolean isDeletable(CurriculumSubject curriculumSubject) {
        List<Long> dependantIds = prerequisiteSubjectRepository.findAllByPrerequisiteId(curriculumSubject.getSubject().getId())
                .stream()
                .map(PrerequisiteSubject::getSubject)
                .map(Subject::getId)
                .collect(Collectors.toList());
        return !curriculumSubjectRepository.existsAllByCurriculumIdAndSubjectIdIn(curriculumSubject.getCurriculum().getId(), dependantIds);
    }

    public void updatePrerequisiteSubjects(CurriculumSubject curriculumSubject) {
        List<Long> prerequisiteIds = prerequisiteSubjectRepository.findAllBySubjectId(curriculumSubject.getSubject().getId())
                .stream()
                .map(PrerequisiteSubject::getPrerequisite)
                .map(Subject::getId)
                .collect(Collectors.toList());
        List<CurriculumSubject> curriculumSubjects = curriculumSubjectRepository.findAllByCurriculumIdAndSubjectIdIn(curriculumSubject.getCurriculum().getId(), prerequisiteIds);
        curriculumSubjects.forEach(e -> e.setOptional(e.isOptional() && curriculumSubject.isOptional()));
        curriculumSubjectRepository.saveAll(curriculumSubjects);
    }
}
