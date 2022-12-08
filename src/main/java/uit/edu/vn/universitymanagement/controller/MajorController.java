package uit.edu.vn.universitymanagement.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uit.edu.vn.universitymanagement.dto.MajorDto;
import uit.edu.vn.universitymanagement.model.entity.Major;
import uit.edu.vn.universitymanagement.service.AbstractCrudService;
import uit.edu.vn.universitymanagement.util.ModelMapperWrapper;

@RestController
@RequestMapping("/api/major")
public class MajorController extends AbstractCrudController<Major, MajorDto> {

    public MajorController(ModelMapperWrapper modelMapperWrapper, AbstractCrudService<Major> service) {
        super(modelMapperWrapper, service, Major.class, MajorDto.class);
    }
}
