package uit.edu.vn.universitymanagement.controller;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uit.edu.vn.universitymanagement.dto.CurriculumDto;
import uit.edu.vn.universitymanagement.model.entity.Curriculum;
import uit.edu.vn.universitymanagement.repository.CurriculumRepository;
import uit.edu.vn.universitymanagement.service.SimpleCurriculumService;

@RestController
@RequestMapping("/api/curriculum")
public class SimpleCurriculumController extends AbstractCrudController<Curriculum, CurriculumDto, SimpleCurriculumService, CurriculumRepository> {
    public SimpleCurriculumController(ModelMapper modelMapper, SimpleCurriculumService service) {
        super(modelMapper, service, Curriculum.class, CurriculumDto.class);
    }
}
