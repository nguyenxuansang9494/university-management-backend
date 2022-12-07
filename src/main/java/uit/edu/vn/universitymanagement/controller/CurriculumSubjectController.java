package uit.edu.vn.universitymanagement.controller;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uit.edu.vn.universitymanagement.dto.CurriculumSubjectDto;
import uit.edu.vn.universitymanagement.model.entity.CurriculumSubject;
import uit.edu.vn.universitymanagement.repository.CurriculumSubjectRepository;
import uit.edu.vn.universitymanagement.service.SimpleCurriculumSubjectService;

@RestController
@RequestMapping("/api/curriculum/subject")
public class CurriculumSubjectController extends AbstractCrudController<CurriculumSubject, CurriculumSubjectDto, SimpleCurriculumSubjectService, CurriculumSubjectRepository> {
    public CurriculumSubjectController(ModelMapper modelMapper, SimpleCurriculumSubjectService service) {
        super(modelMapper, service, CurriculumSubject.class, CurriculumSubjectDto.class);
    }
}
