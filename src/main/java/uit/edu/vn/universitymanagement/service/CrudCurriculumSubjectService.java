package uit.edu.vn.universitymanagement.service;

import org.springframework.stereotype.Service;
import uit.edu.vn.universitymanagement.model.entity.CurriculumSubject;
import uit.edu.vn.universitymanagement.repository.CommonJpaRepository;

@Service
public class CrudCurriculumSubjectService extends AbstractCrudService<CurriculumSubject> {
    public CrudCurriculumSubjectService(CommonJpaRepository<CurriculumSubject, Long> repository) {
        super(repository);
    }
}
