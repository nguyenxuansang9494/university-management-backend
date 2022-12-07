package uit.edu.vn.universitymanagement.service;

import org.springframework.stereotype.Service;
import uit.edu.vn.universitymanagement.model.entity.Subject;
import uit.edu.vn.universitymanagement.repository.SubjectRepository;

@Service
public class SimpleSubjectService extends AbstractCrudService<Subject, SubjectRepository> {
    public SimpleSubjectService(SubjectRepository repository) {
        super(repository);
    }
}
