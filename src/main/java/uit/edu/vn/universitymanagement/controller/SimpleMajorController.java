package uit.edu.vn.universitymanagement.controller;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uit.edu.vn.universitymanagement.dto.MajorDto;
import uit.edu.vn.universitymanagement.model.entity.Major;
import uit.edu.vn.universitymanagement.repository.MajorRepository;
import uit.edu.vn.universitymanagement.service.SimpleMajorService;

@RestController
@RequestMapping("/api/major")
public class SimpleMajorController extends AbstractCrudController<Major, MajorDto, SimpleMajorService, MajorRepository> {
    public SimpleMajorController(ModelMapper modelMapper, SimpleMajorService service) {
        super(modelMapper, service, Major.class, MajorDto.class);
    }

}
