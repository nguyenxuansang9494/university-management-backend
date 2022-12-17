package uit.edu.vn.universitymanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uit.edu.vn.universitymanagement.model.entity.CurriculumSubject;

import java.util.List;

public interface CurriculumSubjectRepository extends CommonJpaRepository<CurriculumSubject, Long> {
    List<CurriculumSubject> findAllByCurriculumId(Long curriculumId);
    Page<CurriculumSubject> findAllByCurriculumId(Long curriculumId, Pageable pageable);
    List<CurriculumSubject> findAllByCurriculumIdAndSubjectIdIn(Long curriculumId, List<Long> subjectId);
    int countAllByCurriculumIdAndSubjectIdIn(Long curriculumId, List<Long> subjectIds);
    int countAllByCurriculumIdInAndSubjectIdIn(List<Long> curriculumIds, List<Long> subjectIds);
}
