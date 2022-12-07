package uit.edu.vn.universitymanagement.service;

import org.springframework.stereotype.Service;
import uit.edu.vn.universitymanagement.model.entity.Curriculum;
import uit.edu.vn.universitymanagement.repository.CurriculumRepository;

@Service
public class SimpleCurriculumService extends AbstractCrudService<Curriculum, CurriculumRepository> {
    public SimpleCurriculumService(CurriculumRepository repository) {
        super(repository);
    }
}
