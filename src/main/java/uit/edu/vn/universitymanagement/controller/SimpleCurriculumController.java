package uit.edu.vn.universitymanagement.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uit.edu.vn.universitymanagement.dto.CurriculumDto;
import uit.edu.vn.universitymanagement.model.entity.Curriculum;
import uit.edu.vn.universitymanagement.repository.CurriculumRepository;
import uit.edu.vn.universitymanagement.service.CurriculumCrudService;
import uit.edu.vn.universitymanagement.util.ModelMapperWrapper;

@RestController
@RequestMapping("/api/curriculum")
public class SimpleCurriculumController extends AbstractCrudController<Curriculum, CurriculumDto, CurriculumCrudService, CurriculumRepository> {
    public SimpleCurriculumController(ModelMapperWrapper modelMapperWrapper, CurriculumCrudService service) {
        super(modelMapperWrapper, service, Curriculum.class, CurriculumDto.class);
    }
}
