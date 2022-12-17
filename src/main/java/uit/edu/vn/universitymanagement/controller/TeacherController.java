package uit.edu.vn.universitymanagement.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uit.edu.vn.universitymanagement.dto.TeacherDto;
import uit.edu.vn.universitymanagement.model.entity.Teacher;
import uit.edu.vn.universitymanagement.service.AbstractCrudService;
import uit.edu.vn.universitymanagement.util.ModelMapperWrapper;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController extends AbstractCrudController<Teacher, TeacherDto> {
    public TeacherController(ModelMapperWrapper modelMapperWrapper, AbstractCrudService<Teacher> service, Class<Teacher> teacherClass, Class<TeacherDto> dtoClass) {
        super(modelMapperWrapper, service, teacherClass, dtoClass);
    }
}
