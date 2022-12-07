package uit.edu.vn.universitymanagement.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uit.edu.vn.universitymanagement.dto.CurriculumSubjectDto;
import uit.edu.vn.universitymanagement.model.entity.CurriculumSubject;
import uit.edu.vn.universitymanagement.repository.CurriculumSubjectRepository;
import uit.edu.vn.universitymanagement.service.SimpleCurriculumSubjectService;
import uit.edu.vn.universitymanagement.util.ModelMapperWrapper;

@RestController
@RequestMapping("/api/curriculum/subject")
public class CurriculumSubjectController extends AbstractCrudController<CurriculumSubject, CurriculumSubjectDto, SimpleCurriculumSubjectService, CurriculumSubjectRepository> {

    public CurriculumSubjectController(ModelMapperWrapper modelMapperWrapper, SimpleCurriculumSubjectService service) {
        super(modelMapperWrapper, service, CurriculumSubject.class, CurriculumSubjectDto.class);
    }
}
