package uit.edu.vn.universitymanagement.controller;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uit.edu.vn.universitymanagement.dto.PrerequisiteSubjectDto;
import uit.edu.vn.universitymanagement.model.entity.PrerequisiteSubject;
import uit.edu.vn.universitymanagement.repository.PrerequisiteSubjectRepository;
import uit.edu.vn.universitymanagement.service.SimplePrerequisiteSubjectService;

@RestController
@RequestMapping("/api/prerequisite")
public class SimplePrerequisiteSubjectController extends AbstractCrudController<PrerequisiteSubject, PrerequisiteSubjectDto, SimplePrerequisiteSubjectService, PrerequisiteSubjectRepository> {
    public SimplePrerequisiteSubjectController(ModelMapper modelMapper, SimplePrerequisiteSubjectService service) {
        super(modelMapper, service, PrerequisiteSubject.class, PrerequisiteSubjectDto.class);
    }
}
