package uit.edu.vn.universitymanagement.service;

import org.springframework.stereotype.Service;
import uit.edu.vn.universitymanagement.model.entity.Subject;
import uit.edu.vn.universitymanagement.repository.SubjectRepository;

@Service
public class CrudSubjectService extends AbstractCrudService<Subject> {
    public CrudSubjectService(SubjectRepository repository) {
        super(repository);
    }
}
