package uit.edu.vn.universitymanagement.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uit.edu.vn.universitymanagement.dto.SubjectDto;
import uit.edu.vn.universitymanagement.model.entity.Subject;
import uit.edu.vn.universitymanagement.repository.SubjectRepository;
import uit.edu.vn.universitymanagement.service.CrudSubjectService;
import uit.edu.vn.universitymanagement.util.ModelMapperWrapper;

@RestController
@RequestMapping("/api/subject")
public class SimpleSubjectController extends AbstractCrudController<Subject, SubjectDto, CrudSubjectService, SubjectRepository> {
    public SimpleSubjectController(ModelMapperWrapper modelMapperWrapper, CrudSubjectService service) {
        super(modelMapperWrapper, service, Subject.class, SubjectDto.class);
    }
}
