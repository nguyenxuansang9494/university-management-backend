package uit.edu.vn.universitymanagement.service;

import org.springframework.stereotype.Service;
import uit.edu.vn.universitymanagement.model.entity.Major;
import uit.edu.vn.universitymanagement.repository.MajorRepository;

@Service
public class CrudMajorService extends AbstractCrudService<Major, MajorRepository> {
    public CrudMajorService(MajorRepository repository) {
        super(repository);
    }
}
