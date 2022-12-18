package uit.edu.vn.universitymanagement.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uit.edu.vn.universitymanagement.model.Person;
import uit.edu.vn.universitymanagement.model.entity.Student;
import uit.edu.vn.universitymanagement.model.entity.Teacher;
import uit.edu.vn.universitymanagement.repository.EmployeeRepository;
import uit.edu.vn.universitymanagement.repository.TeacherRepository;

@Component
@RequiredArgsConstructor
public final class PersonalIDGenerator {
    private final TeacherRepository teacherRepository;
    private final EmployeeRepository employeeRepository;

    public String generate(Person obj) {
        if (obj.getClass().equals(Student.class)) {
            return "ST";
        } else if (obj.getClass().equals(Teacher.class)) {
            long count = teacherRepository.findTopByOrderByIdDesc().orElse(0L) + 1;
            return String.format("LT%08d", count);
        }
        long count = employeeRepository.findTopByOrderByIdDesc().orElse(0L) + 1;
        return String.format("EM%08d", count);
    }
}
