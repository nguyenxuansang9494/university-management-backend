package uit.edu.vn.universitymanagement.repository;

import uit.edu.vn.universitymanagement.model.entity.Curriculum;
import uit.edu.vn.universitymanagement.model.entity.CurriculumSubject;
import uit.edu.vn.universitymanagement.model.entity.Subject;

import java.util.List;

public interface CurriculumSubjectRepository extends CommonJpaRepository<CurriculumSubject, Long> {
    List<CurriculumSubject> findAllByCurriculumIdAndSubjectIdIn(Long curriculumId, List<Long> subjectId);
    List<CurriculumSubject> findAllByCurriculumIdInAndSubjectIdIn(List<Long> curIds, List<Long> subIds);
    List<CurriculumSubject> findAllByCurriculumAndSubjectIn(Curriculum curriculum, List<Subject> subjects);
    boolean existsAllByCurriculumIdAndSubjectIdIn(Long curriculumId, List<Long> subjectIds);
}
