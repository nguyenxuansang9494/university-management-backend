package uit.edu.vn.universitymanagement.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uit.edu.vn.universitymanagement.dto.FacultyDto;
import uit.edu.vn.universitymanagement.model.entity.Faculty;
import uit.edu.vn.universitymanagement.service.AbstractCrudService;
import uit.edu.vn.universitymanagement.util.ModelMapperWrapper;

@RestController
@RequestMapping("/api/faculty")
public class FacultyController extends AbstractCrudController<Faculty, FacultyDto> {
    public FacultyController(ModelMapperWrapper modelMapperWrapper, AbstractCrudService<Faculty> service) {
        super(modelMapperWrapper, service, Faculty.class, FacultyDto.class);
    }
}
