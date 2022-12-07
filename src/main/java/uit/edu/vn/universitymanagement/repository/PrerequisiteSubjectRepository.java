package uit.edu.vn.universitymanagement.repository;

import uit.edu.vn.universitymanagement.model.entity.PrerequisiteSubject;

import java.util.List;

public interface PrerequisiteSubjectRepository extends CommonJpaRepository<PrerequisiteSubject, Long> {
    List<PrerequisiteSubject> findAllBySubjectIdIn(List<Long> ids);
    List<PrerequisiteSubject> findAllBySubjectId(Long id);
    List<PrerequisiteSubject> findAllByPrerequisiteId(Long id);
}
