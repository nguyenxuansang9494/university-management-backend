package uit.edu.vn.universitymanagement.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uit.edu.vn.universitymanagement.dto.EmployeeDto;
import uit.edu.vn.universitymanagement.model.entity.Employee;
import uit.edu.vn.universitymanagement.service.AbstractCrudService;
import uit.edu.vn.universitymanagement.util.ModelMapperWrapper;

@RestController
@RequestMapping("api/employee")
public class EmployeeController extends AbstractCrudController<Employee, EmployeeDto> {
    public EmployeeController(ModelMapperWrapper modelMapperWrapper, AbstractCrudService<Employee> service) {
        super(modelMapperWrapper, service, Employee.class, EmployeeDto.class);
    }
}
