package uit.edu.vn.universitymanagement.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import uit.edu.vn.universitymanagement.dto.MajorDto;
import uit.edu.vn.universitymanagement.dto.QueryByIdDto;
import uit.edu.vn.universitymanagement.model.entity.Major;
import uit.edu.vn.universitymanagement.service.AbstractCrudService;

import java.util.List;

@RestController
@RequestMapping("/api/major")
public class SimpleMajorController extends AbstractCrudController<Major, MajorDto> {
    public SimpleMajorController(ModelMapper modelMapper, AbstractCrudService<Major> service) {
        super(modelMapper, service, Major.class, MajorDto.class);
    }

    @Override
    @PutMapping
    public ResponseEntity<MajorDto> create(Authentication authentication, @RequestBody MajorDto reqDto) {
        return super.create(authentication, reqDto);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<MajorDto> read(Authentication authentication, @PathVariable("id") long id) {
        return super.read(authentication, id);
    }

    @Override
    @PatchMapping
    public ResponseEntity<MajorDto> update(Authentication authentication, @RequestBody MajorDto reqDto) {
        return super.update(authentication, reqDto);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(Authentication authentication, @PathVariable("id") long id) {
        return super.delete(authentication, id);
    }

    @Override
    @PutMapping("/batch")
    public ResponseEntity<List<MajorDto>> create(Authentication authentication, @RequestBody List<MajorDto> reqDtos) {
        return super.create(authentication, reqDtos);
    }

    @Override
    @PostMapping("/batch")
    public ResponseEntity<Object> read(Authentication authentication, @RequestBody QueryByIdDto queryByIdDto) {
        return super.read(authentication, queryByIdDto);
    }

    @Override
    @PatchMapping("/batch")
    public ResponseEntity<List<MajorDto>> update(Authentication authentication, @RequestBody List<MajorDto> reqDtos) {
        return super.update(authentication, reqDtos);
    }

    @Override
    @DeleteMapping("/batch")
    public ResponseEntity<Void> delete(Authentication authentication, @RequestBody List<Long> ids) {
        return super.delete(authentication, ids);
    }
}
