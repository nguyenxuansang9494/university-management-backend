package uit.edu.vn.universitymanagement.repository;

import org.springframework.stereotype.Repository;
import uit.edu.vn.universitymanagement.model.entity.Major;

@Repository
public interface MajorRepository extends CommonJpaRepository<Major, Long> {
}
