package uit.edu.vn.universitymanagement.service;

import org.springframework.stereotype.Service;
import uit.edu.vn.universitymanagement.model.entity.CurriculumSubject;
import uit.edu.vn.universitymanagement.repository.CurriculumSubjectRepository;

@Service
public class SimpleCurriculumSubjectService extends AbstractCrudService<CurriculumSubject, CurriculumSubjectRepository> {
    public SimpleCurriculumSubjectService(CurriculumSubjectRepository repository) {
        super(repository);
    }
}
