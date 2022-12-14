package uit.edu.vn.universitymanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import uit.edu.vn.universitymanagement.authorization.ActionType;
import uit.edu.vn.universitymanagement.authorization.Authorizer;
import uit.edu.vn.universitymanagement.exception.CommonRuntimeException;
import uit.edu.vn.universitymanagement.exception.ErrorType;
import uit.edu.vn.universitymanagement.model.ManagedModel;
import uit.edu.vn.universitymanagement.model.Metadata;
import uit.edu.vn.universitymanagement.repository.CommonJpaRepository;
import uit.edu.vn.universitymanagement.util.AuthenticationUtils;
import uit.edu.vn.universitymanagement.util.ManagedModelUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class AbstractCrudService<T extends ManagedModel> implements SingleCrudService<T>, MultipleCrudService<T>, Authorizer<T> {
    final CommonJpaRepository<T, Long> repository;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public T create(Authentication authentication, T object) {
        if (!authorize(authentication, ActionType.WRITE, object)) {
            throw new CommonRuntimeException(ErrorType.PERMISSION_DENIED);
        }
        object.setId(null);
        object.setMetadata(new Metadata());
        object.getMetadata().setLastModifier(AuthenticationUtils.getAccount(authentication));
        object.getMetadata().setModifiedAt(new Date());
        return repository.save(object);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public T read(Authentication authentication, Long id) {
        Optional<T> optionalT = repository.findById(id);
        T object = optionalT.orElseThrow(() -> {
            throw new CommonRuntimeException(ErrorType.NOT_FOUND);
        });
        if (!authorize(authentication, ActionType.READ, object)) {
            throw new CommonRuntimeException(ErrorType.PERMISSION_DENIED);
        }
        return object;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public T update(Authentication authentication, T object) {
        if (!authorize(authentication, ActionType.WRITE, object)) {
            throw new CommonRuntimeException(ErrorType.PERMISSION_DENIED);
        }
        if (!repository.existsById(object.getId())) {
            throw new CommonRuntimeException(ErrorType.NOT_FOUND);
        }
        object.setMetadata(new Metadata());
        object.getMetadata().setLastModifier(AuthenticationUtils.getAccount(authentication));
        object.getMetadata().setModifiedAt(new Date());
        return repository.save(object);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public T delete(Authentication authentication, Long id) {
        T object = repository.findById(id).orElseThrow(() -> {
            throw new CommonRuntimeException(ErrorType.NOT_FOUND);
        });
        repository.deleteById(id);
        if (!authorize(authentication, ActionType.WRITE, object)) {
            throw new CommonRuntimeException(ErrorType.PERMISSION_DENIED);
        }
        return object;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public List<T> create(Authentication authentication, List<T> objects) {
        objects.forEach(obj -> {
            obj.setId(null);
            obj.setMetadata(new Metadata());
            obj.getMetadata().setLastModifier(AuthenticationUtils.getAccount(authentication));
            obj.getMetadata().setModifiedAt(new Date());
        });
        if (!batchAuthorize(authentication, ActionType.WRITE, objects)) {
            throw new CommonRuntimeException(ErrorType.PERMISSION_DENIED);
        }
        return repository.saveAll(objects);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Page<T> read(Authentication authentication, List<Long> ids, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<T> objects = repository.findAllByIdIn(ids, pageable);
        if (ids.isEmpty()) {
            objects = repository.findAll(pageable);
        }
        if (!batchAuthorize(authentication, ActionType.READ, objects.getContent())) {
            throw new CommonRuntimeException(ErrorType.PERMISSION_DENIED);
        }
        return objects;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public List<T> read(Authentication authentication, List<Long> ids) {
        List<T> objects = repository.findAllByIdIn(ids);
        if (!batchAuthorize(authentication, ActionType.READ, objects)) {
            throw new CommonRuntimeException(ErrorType.PERMISSION_DENIED);
        }
        return objects;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public List<T> update(Authentication authentication, List<T> objects) {
        if (!batchAuthorize(authentication, ActionType.WRITE, objects)) {
            throw new CommonRuntimeException(ErrorType.PERMISSION_DENIED);
        }
        List<Long> ids = ManagedModelUtils.convertToLongList(objects);

        if (repository.countByIdIn(ids) != objects.size()) {
            throw new CommonRuntimeException(ErrorType.NOT_FOUND);
        }
        for (T object : objects) {
            object.setMetadata(new Metadata());
            object.getMetadata().setLastModifier(AuthenticationUtils.getAccount(authentication));
            object.getMetadata().setModifiedAt(new Date());
        }
        return repository.saveAll(objects);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public List<T> delete(Authentication authentication, List<Long> ids) {
        List<T> objects = repository.findAllByIdIn(ids);
        if (objects.size() < ids.size()) {
            throw new CommonRuntimeException(ErrorType.NOT_FOUND);
        }
        repository.deleteByIdIn(ids);
        if (!batchAuthorize(authentication, ActionType.WRITE, objects)) {
            throw new CommonRuntimeException(ErrorType.PERMISSION_DENIED);
        }
        return objects;
    }
}
