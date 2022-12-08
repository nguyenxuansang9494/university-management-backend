package uit.edu.vn.universitymanagement.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uit.edu.vn.universitymanagement.dto.PrerequisiteSubjectDto;
import uit.edu.vn.universitymanagement.model.entity.PrerequisiteSubject;
import uit.edu.vn.universitymanagement.service.AbstractCrudService;
import uit.edu.vn.universitymanagement.util.ModelMapperWrapper;

@RestController
@RequestMapping("/api/prerequisite")
public class PrerequisiteSubjectController extends AbstractCrudController<PrerequisiteSubject, PrerequisiteSubjectDto> {
    public PrerequisiteSubjectController(ModelMapperWrapper modelMapperWrapper, AbstractCrudService<PrerequisiteSubject> service) {
        super(modelMapperWrapper, service, PrerequisiteSubject.class, PrerequisiteSubjectDto.class);
    }
}