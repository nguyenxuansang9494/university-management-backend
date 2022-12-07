package uit.edu.vn.universitymanagement.service;

import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import uit.edu.vn.universitymanagement.model.entity.CurriculumSubject;
import uit.edu.vn.universitymanagement.repository.CurriculumRepository;
import uit.edu.vn.universitymanagement.repository.CurriculumSubjectRepository;
import uit.edu.vn.universitymanagement.repository.SubjectRepository;

import java.util.List;

@Service
public class SimpleCurriculumSubjectService extends AbstractCrudService<CurriculumSubject, CurriculumSubjectRepository> {
    private final CurriculumRepository curriculumRepository;
    private final SubjectRepository subjectRepository;
    public SimpleCurriculumSubjectService(CurriculumSubjectRepository repository, CurriculumRepository curriculumRepository, SubjectRepository subjectRepository) {
        super(repository);
        this.curriculumRepository = curriculumRepository;
        this.subjectRepository = subjectRepository;
    }

    @Override
    public CurriculumSubject create(Authentication authentication, CurriculumSubject object) {
        return super.create(authentication, object);
    }

    @Override
    public CurriculumSubject read(Authentication authentication, long id) {
        return super.read(authentication, id);
    }

    @Override
    public CurriculumSubject update(Authentication authentication, CurriculumSubject object) {
        return super.update(authentication, object);
    }

    @Override
    public void delete(Authentication authentication, long id) {
        super.delete(authentication, id);
    }

    @Override
    public List<CurriculumSubject> create(Authentication authentication, List<CurriculumSubject> objects) {
        return super.create(authentication, objects);
    }

    @Override
    public Page<CurriculumSubject> read(Authentication authentication, List<Long> ids, int page, int size) {
        return super.read(authentication, ids, page, size);
    }

    @Override
    public List<CurriculumSubject> read(Authentication authentication, List<Long> ids) {
        return super.read(authentication, ids);
    }

    @Override
    public List<CurriculumSubject> update(Authentication authentication, List<CurriculumSubject> objects) {
        return super.update(authentication, objects);
    }

    @Override
    public void delete(Authentication authentication, List<Long> ids) {
        super.delete(authentication, ids);
    }


}
