package uit.edu.vn.universitymanagement.controller;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uit.edu.vn.universitymanagement.dto.FacultyDto;
import uit.edu.vn.universitymanagement.model.entity.Faculty;
import uit.edu.vn.universitymanagement.service.AbstractCrudService;

@RestController
@RequestMapping("/api/faculty")
public class SimpleFacultyController extends AbstractCrudController<Faculty, FacultyDto> {
    public SimpleFacultyController(ModelMapper modelMapper, AbstractCrudService<Faculty> service) {
        super(modelMapper, service, Faculty.class, FacultyDto.class);
    }
}
