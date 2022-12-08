package uit.edu.vn.universitymanagement.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uit.edu.vn.universitymanagement.dto.CurriculumDto;
import uit.edu.vn.universitymanagement.model.entity.Curriculum;
import uit.edu.vn.universitymanagement.service.AbstractCrudService;
import uit.edu.vn.universitymanagement.util.ModelMapperWrapper;

@RestController
@RequestMapping("/api/curriculum")
public class CurriculumController extends AbstractCrudController<Curriculum, CurriculumDto> {
    public CurriculumController(ModelMapperWrapper modelMapperWrapper, AbstractCrudService<Curriculum> service) {
        super(modelMapperWrapper, service, Curriculum.class, CurriculumDto.class);
    }
}
