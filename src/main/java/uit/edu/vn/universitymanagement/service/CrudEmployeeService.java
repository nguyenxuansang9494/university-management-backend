package uit.edu.vn.universitymanagement.service;

import org.springframework.stereotype.Service;
import uit.edu.vn.universitymanagement.model.entity.Employee;
import uit.edu.vn.universitymanagement.repository.EmployeeRepository;

@Service
public class CrudEmployeeService extends AbstractCrudService<Employee> {
    public CrudEmployeeService(EmployeeRepository repository) {
        super(repository);
    }
}
