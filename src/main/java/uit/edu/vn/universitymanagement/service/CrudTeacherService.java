package uit.edu.vn.universitymanagement.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import uit.edu.vn.universitymanagement.authorization.ActionType;
import uit.edu.vn.universitymanagement.authorization.Authorizer;
import uit.edu.vn.universitymanagement.authorization.Role;
import uit.edu.vn.universitymanagement.model.entity.Teacher;
import uit.edu.vn.universitymanagement.repository.CommonJpaRepository;
import uit.edu.vn.universitymanagement.util.PersonalIDGenerator;

@Service
public class CrudTeacherService extends CrudPersonService<Teacher> {
    public CrudTeacherService(CommonJpaRepository<Teacher, Long> repository, PersonalIDGenerator generator) {
        super(repository, generator);
    }

    @Override
    public boolean authorize(Authentication authentication, ActionType actionType, Teacher object) {
        boolean isOwner = Authorizer.allowOnlyOwnerToInteractWith(authentication, object.getAccount());
        boolean allowModToWriteAndAllToRead = Authorizer.allowACertainRoleAboveToReadAndACertainRoleAboveToWrite(authentication, actionType, Role.MODERATOR, Role.STUDENT);
        return isOwner || allowModToWriteAndAllToRead;
    }
}
