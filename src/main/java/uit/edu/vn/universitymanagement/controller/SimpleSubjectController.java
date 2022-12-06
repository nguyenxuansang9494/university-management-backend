package uit.edu.vn.universitymanagement.controller;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uit.edu.vn.universitymanagement.dto.SubjectDto;
import uit.edu.vn.universitymanagement.model.entity.Subject;
import uit.edu.vn.universitymanagement.repository.SubjectRepository;
import uit.edu.vn.universitymanagement.service.SimpleSubjectService;

@RestController
@RequestMapping("/api/subject")
public class SimpleSubjectController extends AbstractCrudController<Subject, SubjectDto, SimpleSubjectService, SubjectRepository> {
    public SimpleSubjectController(ModelMapper modelMapper, SimpleSubjectService service) {
        super(modelMapper, service, Subject.class, SubjectDto.class);
    }
}
