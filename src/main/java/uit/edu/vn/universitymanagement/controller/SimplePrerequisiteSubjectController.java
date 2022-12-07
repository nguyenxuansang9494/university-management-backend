package uit.edu.vn.universitymanagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uit.edu.vn.universitymanagement.dto.PrerequisiteSubjectDto;
import uit.edu.vn.universitymanagement.model.entity.PrerequisiteSubject;
import uit.edu.vn.universitymanagement.repository.PrerequisiteSubjectRepository;
import uit.edu.vn.universitymanagement.service.SimplePrerequisiteSubjectService;
import uit.edu.vn.universitymanagement.util.ModelMapperWrapper;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/prerequisite")
public class SimplePrerequisiteSubjectController extends AbstractCrudController<PrerequisiteSubject, PrerequisiteSubjectDto, SimplePrerequisiteSubjectService, PrerequisiteSubjectRepository> {

    public SimplePrerequisiteSubjectController(ModelMapperWrapper modelMapperWrapper, SimplePrerequisiteSubjectService service) {
        super(modelMapperWrapper, service, PrerequisiteSubject.class, PrerequisiteSubjectDto.class);
    }

    @GetMapping
    public ResponseEntity<List<PrerequisiteSubjectDto>> findBySubjectId(Authentication authentication,
                                                                        @RequestParam(name = "subject_id", required = false) Long subjectId,
                                                                        @RequestParam(name = "prerequisite_id", required = false) Long prerequisiteId) {
        List<PrerequisiteSubjectDto> prerequisiteSubjectDtos = Collections.emptyList();
        if (subjectId != null) {
            List<PrerequisiteSubject> prerequisiteSubjects = service.findBySubjectId(authentication, subjectId);
            prerequisiteSubjectDtos = modelMapperWrapper.mapList(prerequisiteSubjects, PrerequisiteSubjectDto.class);
        }
        if (prerequisiteId != null) {
            List<PrerequisiteSubject> prerequisiteSubjects = service.findByPrerequisiteId(authentication, prerequisiteId);
            prerequisiteSubjectDtos = modelMapperWrapper.mapList(prerequisiteSubjects, PrerequisiteSubjectDto.class);
        }
        return ResponseEntity.ok(prerequisiteSubjectDtos);
    }
}