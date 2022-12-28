package uit.edu.vn.universitymanagement.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import uit.edu.vn.universitymanagement.authorization.ActionType;
import uit.edu.vn.universitymanagement.authorization.Authorizer;
import uit.edu.vn.universitymanagement.authorization.Role;
import uit.edu.vn.universitymanagement.model.entity.Employee;
import uit.edu.vn.universitymanagement.repository.CommonJpaRepository;

@Service
public class CrudEmployeeService extends CrudPersonService<Employee> {
    public CrudEmployeeService(CommonJpaRepository<Employee, Long> repository) {
        super(repository);
    }
    @Override
    public boolean authorize(Authentication authentication, ActionType actionType, Employee object) {
        boolean isAllowOnlyModToWriteAndAllToRead = Authorizer.allowACertainRoleAboveToReadAndACertainRoleAboveToWrite(authentication, actionType, Role.MODERATOR, Role.STUDENT);
        boolean isAllowOwnerToWrite = Authorizer.allowOwnerToWrite(authentication, actionType, object.getAccount());
        return isAllowOwnerToWrite || isAllowOnlyModToWriteAndAllToRead;
    }
}
