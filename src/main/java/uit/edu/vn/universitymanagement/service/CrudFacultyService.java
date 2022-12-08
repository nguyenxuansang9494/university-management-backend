package uit.edu.vn.universitymanagement.service;

import org.springframework.stereotype.Service;
import uit.edu.vn.universitymanagement.model.entity.Faculty;
import uit.edu.vn.universitymanagement.repository.FacultyRepository;

@Service
public class CrudFacultyService extends AbstractCrudService<Faculty, FacultyRepository> {
    public CrudFacultyService(FacultyRepository repository) {
        super(repository);
    }
}
