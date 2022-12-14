package uit.edu.vn.universitymanagement.service;

import org.springframework.stereotype.Service;
import uit.edu.vn.universitymanagement.model.entity.Curriculum;
import uit.edu.vn.universitymanagement.repository.CurriculumRepository;

@Service
public class CrudCurriculumService extends AbstractCrudService<Curriculum> {
    public CrudCurriculumService(CurriculumRepository repository) {
        super(repository);
    }
}
