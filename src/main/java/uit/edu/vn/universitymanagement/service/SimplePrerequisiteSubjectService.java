package uit.edu.vn.universitymanagement.service;

import org.springframework.stereotype.Service;
import uit.edu.vn.universitymanagement.model.entity.PrerequisiteSubject;
import uit.edu.vn.universitymanagement.repository.PrerequisiteSubjectRepository;

@Service
public class SimplePrerequisiteSubjectService extends AbstractCrudService<PrerequisiteSubject, PrerequisiteSubjectRepository> {
    public SimplePrerequisiteSubjectService(PrerequisiteSubjectRepository repository) {
        super(repository);
    }
}
