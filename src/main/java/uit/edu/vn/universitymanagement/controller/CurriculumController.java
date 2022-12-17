package uit.edu.vn.universitymanagement.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uit.edu.vn.universitymanagement.dto.CurriculumDto;
import uit.edu.vn.universitymanagement.dto.CurriculumSubjectDto;
import uit.edu.vn.universitymanagement.dto.QueryByIdDto;
import uit.edu.vn.universitymanagement.exception.CommonRuntimeException;
import uit.edu.vn.universitymanagement.exception.ErrorType;
import uit.edu.vn.universitymanagement.model.entity.Curriculum;
import uit.edu.vn.universitymanagement.service.AbstractCrudService;
import uit.edu.vn.universitymanagement.service.LogicCurriculumSubjectService;
import uit.edu.vn.universitymanagement.util.ModelMapperWrapper;

import java.util.List;

@RestController
@RequestMapping("/api/curriculum")
public class CurriculumController extends AbstractCrudController<Curriculum, CurriculumDto> {
    private final LogicCurriculumSubjectService logicCurriculumSubjectService;
    public CurriculumController(ModelMapperWrapper modelMapperWrapper, AbstractCrudService<Curriculum> service, LogicCurriculumSubjectService logicCurriculumSubjectService) {
        super(modelMapperWrapper, service, Curriculum.class, CurriculumDto.class);
        this.logicCurriculumSubjectService = logicCurriculumSubjectService;
    }

    @PostMapping("/subjects")
    public ResponseEntity<Object> findSubjectByCurriculum(Authentication authentication, @RequestBody QueryByIdDto queryByIdDto) {
        if (queryByIdDto.getIds().size() != 1)
            throw new CommonRuntimeException(ErrorType.BAD_REQUEST, "ids is not 1");
        if (queryByIdDto.isPaged()) {
            Page<CurriculumSubjectDto> rs = modelMapperWrapper.mapPage(logicCurriculumSubjectService.getByCurriculumId(authentication, queryByIdDto.getIds().get(0), queryByIdDto.getPage(), queryByIdDto.getSize()), CurriculumSubjectDto.class);
            return ResponseEntity.ok(rs);
        }
        List<CurriculumSubjectDto> rs = modelMapperWrapper.mapList(logicCurriculumSubjectService.getByCurriculumId(authentication, queryByIdDto.getIds().get(0)), CurriculumSubjectDto.class);
        return ResponseEntity.ok(rs);
    }
}
