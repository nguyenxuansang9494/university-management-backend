package uit.edu.vn.universitymanagement.service;

import org.springframework.security.core.Authentication;
import uit.edu.vn.universitymanagement.exception.CommonRuntimeException;
import uit.edu.vn.universitymanagement.exception.ErrorType;
import uit.edu.vn.universitymanagement.model.Person;
import uit.edu.vn.universitymanagement.repository.CommonJpaRepository;
import uit.edu.vn.universitymanagement.util.ManagedModelUtils;
import uit.edu.vn.universitymanagement.util.PersonalIDGenerator;

import java.util.List;

public abstract class CrudPersonService<K extends Person> extends AbstractCrudService<K> {
    protected CrudPersonService(CommonJpaRepository<K, Long> repository) {
        super(repository);
    }

    @Override
    public K create(Authentication authentication, K object) {
        K savedObject = super.create(authentication, object);
        savedObject.getPersonalInformation().setPersonalID(PersonalIDGenerator.generate(savedObject));
        return repository.save(savedObject);
    }

    @Override
    public K update(Authentication authentication, K object) {
        K savedObj = repository.findById(object.getId()).orElseThrow(() -> new CommonRuntimeException(ErrorType.NOT_FOUND));
        object.getPersonalInformation().setPersonalID(savedObj.getPersonalInformation().getPersonalID());
        return super.update(authentication, object);
    }

    @Override
    public List<K> create(Authentication authentication, List<K> objects) {
        List<K> savedObject = super.create(authentication, objects);
        savedObject.forEach(e -> e.getPersonalInformation().setPersonalID(PersonalIDGenerator.generate(e)));
        return repository.saveAll(savedObject);
    }

    @Override
    public List<K> update(Authentication authentication, List<K> objects) {
        List<Long> ids = ManagedModelUtils.convertToLongList(objects);
        List<K> savedObjs = repository.findAllByIdIn(ids);
        objects.forEach(e -> e.getPersonalInformation()
                .setPersonalID(savedObjs.stream()
                        .filter(savedE -> savedE.getId().equals(e.getId()))
                        .findFirst()
                        .orElseThrow(() -> new CommonRuntimeException(ErrorType.NOT_FOUND))
                        .getPersonalInformation()
                        .getPersonalID()));
        return super.update(authentication, objects);
    }
}
