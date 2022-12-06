package uit.edu.vn.universitymanagement.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import uit.edu.vn.universitymanagement.dto.FacultyDto;
import uit.edu.vn.universitymanagement.dto.QueryByIdDto;
import uit.edu.vn.universitymanagement.model.entity.Faculty;
import uit.edu.vn.universitymanagement.service.AbstractCrudService;

import java.util.List;

@RestController
@RequestMapping("/api/faculty")
public class SimpleFacultyController extends AbstractCrudController<Faculty, FacultyDto> {
    public SimpleFacultyController(ModelMapper modelMapper, AbstractCrudService<Faculty> service) {
        super(modelMapper, service, Faculty.class, FacultyDto.class);
    }

    @Override
    @PutMapping
    public ResponseEntity<FacultyDto> create(Authentication authentication, @RequestBody FacultyDto reqDto) {
        return super.create(authentication, reqDto);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<FacultyDto> read(Authentication authentication, @PathVariable("id") long id) {
        return super.read(authentication, id);
    }

    @Override
    @PatchMapping
    public ResponseEntity<FacultyDto> update(Authentication authentication, @RequestBody FacultyDto reqDto) {
        return super.update(authentication, reqDto);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(Authentication authentication, @PathVariable("id") long id) {
        return super.delete(authentication, id);
    }

    @Override
    @PutMapping("/batch")
    public ResponseEntity<List<FacultyDto>> create(Authentication authentication, @RequestBody List<FacultyDto> reqDtos) {
        return super.create(authentication, reqDtos);
    }

    @Override
    @PostMapping("/batch")
    public ResponseEntity<Object> read(Authentication authentication, @RequestBody QueryByIdDto queryByIdDto) {
        return super.read(authentication, queryByIdDto);
    }

    @Override
    @PatchMapping("/batch")
    public ResponseEntity<List<FacultyDto>> update(Authentication authentication, @RequestBody List<FacultyDto> reqDtos) {
        return super.update(authentication, reqDtos);
    }

    @Override
    @DeleteMapping("/batch")
    public ResponseEntity<Void> delete(Authentication authentication, @RequestBody List<Long> ids) {
        return super.delete(authentication, ids);
    }
}
