package uit.edu.vn.universitymanagement.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uit.edu.vn.universitymanagement.dto.FacultyDto;
import uit.edu.vn.universitymanagement.model.entity.Faculty;
import uit.edu.vn.universitymanagement.repository.FacultyRepository;
import uit.edu.vn.universitymanagement.service.CrudFacultyService;
import uit.edu.vn.universitymanagement.util.ModelMapperWrapper;

@RestController
@RequestMapping("/api/faculty")
public class SimpleFacultyController extends AbstractCrudController<Faculty, FacultyDto, CrudFacultyService, FacultyRepository> {
    public SimpleFacultyController(ModelMapperWrapper modelMapperWrapper, CrudFacultyService service) {
        super(modelMapperWrapper, service, Faculty.class, FacultyDto.class);
    }
}
