package uit.edu.vn.universitymanagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uit.edu.vn.universitymanagement.dto.PrerequisiteSubjectDto;
import uit.edu.vn.universitymanagement.dto.SubjectDto;
import uit.edu.vn.universitymanagement.model.entity.PrerequisiteSubject;
import uit.edu.vn.universitymanagement.model.entity.Subject;
import uit.edu.vn.universitymanagement.service.AbstractCrudService;
import uit.edu.vn.universitymanagement.service.PrerequisiteSubjectLogicService;
import uit.edu.vn.universitymanagement.util.ModelMapperWrapper;

import java.util.List;

@RestController
@RequestMapping("/api/subject")
public class SubjectController extends AbstractCrudController<Subject, SubjectDto> {
    private final PrerequisiteSubjectLogicService prerequisiteSubjectLogicService;
    public SubjectController(ModelMapperWrapper modelMapperWrapper, AbstractCrudService<Subject> service, PrerequisiteSubjectLogicService prerequisiteSubjectLogicService) {
        super(modelMapperWrapper, service, Subject.class, SubjectDto.class);
        this.prerequisiteSubjectLogicService = prerequisiteSubjectLogicService;
    }

    @GetMapping("/dependency/{id}")
    public ResponseEntity<List<PrerequisiteSubjectDto>> findPrerequisiteBySubjectId(Authentication authentication, @PathVariable("id") Long id) {
        List<PrerequisiteSubject> prerequisiteSubjects = prerequisiteSubjectLogicService.findBySubjectId(authentication, id);
        return ResponseEntity.ok(modelMapperWrapper.mapList(prerequisiteSubjects, PrerequisiteSubjectDto.class));
    }

    @GetMapping("/dependant/{id}")
    public ResponseEntity<List<PrerequisiteSubjectDto>> findDependantByPrerequisiteId(Authentication authentication, @PathVariable("id") Long id) {
        List<PrerequisiteSubject> prerequisiteSubjects = prerequisiteSubjectLogicService.findByPrerequisiteId(authentication, id);
        return ResponseEntity.ok(modelMapperWrapper.mapList(prerequisiteSubjects, PrerequisiteSubjectDto.class));
    }
}
