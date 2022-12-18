package uit.edu.vn.universitymanagement.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uit.edu.vn.universitymanagement.exception.CommonRuntimeException;
import uit.edu.vn.universitymanagement.exception.ErrorType;
import uit.edu.vn.universitymanagement.model.ManagedModel;
import uit.edu.vn.universitymanagement.model.entity.Employee;
import uit.edu.vn.universitymanagement.model.entity.Student;
import uit.edu.vn.universitymanagement.model.entity.Teacher;
import uit.edu.vn.universitymanagement.repository.EmployeeRepository;
import uit.edu.vn.universitymanagement.repository.TeacherRepository;

@Component
@RequiredArgsConstructor
public final class PersonalIDGenerator {
    private final TeacherRepository teacherRepository;
    private final EmployeeRepository employeeRepository;

    public String generate(ManagedModel obj) {
        if (obj.getClass().equals(Student.class)) {
            return "ST";
        } else if (obj.getClass().equals(Teacher.class)) {
            return "LT";
        } else if (obj.getClass().equals(Employee.class)) {
            return "EM";
        }
        throw new CommonRuntimeException(ErrorType.ILLEGAL_ARGUMENT);

    }
}
