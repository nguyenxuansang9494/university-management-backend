package uit.edu.vn.universitymanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import uit.edu.vn.universitymanagement.exception.PermissionDeniedException;
import uit.edu.vn.universitymanagement.exception.ResourceNotFoundException;
import uit.edu.vn.universitymanagement.model.ManagedModel;
import uit.edu.vn.universitymanagement.model.Metadata;
import uit.edu.vn.universitymanagement.model.Role;
import uit.edu.vn.universitymanagement.repository.CommonJpaRepository;
import uit.edu.vn.universitymanagement.util.AuthenticationUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public abstract class AbstractCrudService<T extends ManagedModel, U extends CommonJpaRepository<T, Long>> implements SingleCrudService<T>, MultipleCrudService<T>, Authorizer<T> {
    final U repository;

    @Override
    public boolean authorize(Authentication authentication, ActionType actionType, T object) {
        return Authorizer.allowAllToReadButOnlyCertainRoleAboveToWrite(authentication, actionType, Role.MODERATOR);
    }

    @Override
    public boolean batchAuthorize(Authentication authentication, ActionType actionType, List<T> objects) {
        return objects.stream().map(obj -> authorize(authentication, actionType, obj)).reduce((Boolean::logicalAnd)).orElse(false);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public T create(Authentication authentication, T object) {
        if (!authorize(authentication, ActionType.WRITE, object)) {
            throw new PermissionDeniedException();
        }
        object.setMetadata(new Metadata());
        object.getMetadata().setLastModifier(AuthenticationUtils.getAccount(authentication));
        object.getMetadata().setModifiedAt(new Date());
        return repository.save(object);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public T read(Authentication authentication, Long id) {
        Optional<T> optionalT = repository.findById(id);
        T object = optionalT.orElseThrow(ResourceNotFoundException::new);
        if (!authorize(authentication, ActionType.READ, object)) {
            throw new PermissionDeniedException();
        }
        return object;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public T update(Authentication authentication, T object) {
        if (!authorize(authentication, ActionType.WRITE, object)) {
            throw new PermissionDeniedException();
        }
        if (!repository.existsById(object.getId())) {
            throw new ResourceNotFoundException();
        }
        object.setMetadata(new Metadata());
        object.getMetadata().setLastModifier(AuthenticationUtils.getAccount(authentication));
        object.getMetadata().setModifiedAt(new Date());
        return repository.save(object);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void delete(Authentication authentication, Long id) {
        T object = repository.findById(id).orElseThrow(ResourceNotFoundException::new);
        repository.deleteById(id);
        if (!authorize(authentication, ActionType.WRITE, object)) {
            throw new PermissionDeniedException();
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public List<T> create(Authentication authentication, List<T> objects) {
        objects.forEach(obj -> {
            obj.setMetadata(new Metadata());
            obj.getMetadata().setLastModifier(AuthenticationUtils.getAccount(authentication));
            obj.getMetadata().setModifiedAt(new Date());
        });
        if (!batchAuthorize(authentication, ActionType.WRITE, objects)) {
            throw new PermissionDeniedException();
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
            throw new PermissionDeniedException();
        }
        return objects;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public List<T> read(Authentication authentication, List<Long> ids) {
        List<T> objects = repository.findAllByIdIn(ids);
        if (!batchAuthorize(authentication, ActionType.READ, objects)) {
            throw new PermissionDeniedException();
        }
        return objects;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public List<T> update(Authentication authentication, List<T> objects) {
        if (!batchAuthorize(authentication, ActionType.WRITE, objects)) {
            throw new PermissionDeniedException();
        }
        List<Long> ids = objects.stream().map(ManagedModel::getId).collect(Collectors.toList());

        if (repository.countByIdIn(ids) != objects.size()) {
            throw new ResourceNotFoundException();
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
    public void delete(Authentication authentication, List<Long> ids) {
        List<T> objects = repository.findAllByIdIn(ids);
        if (objects.size() < ids.size()) {
            throw new ResourceNotFoundException();
        }
        repository.deleteByIdIn(ids);
        if (!batchAuthorize(authentication, ActionType.WRITE, objects)) {
            throw new PermissionDeniedException();
        }
    }
}
