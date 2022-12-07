package uit.edu.vn.universitymanagement.service;

import org.springframework.stereotype.Service;
import uit.edu.vn.universitymanagement.model.entity.Faculty;
import uit.edu.vn.universitymanagement.repository.FacultyRepository;

@Service
public class SimpleFacultyService extends AbstractCrudService<Faculty, FacultyRepository> {
    public SimpleFacultyService(FacultyRepository repository) {
        super(repository);
    }
}
