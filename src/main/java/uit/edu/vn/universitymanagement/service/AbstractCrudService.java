package uit.edu.vn.universitymanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import uit.edu.vn.universitymanagement.exception.PermissionDeniedException;
import uit.edu.vn.universitymanagement.exception.ResourceNotFoundException;
import uit.edu.vn.universitymanagement.model.ManagedEntity;
import uit.edu.vn.universitymanagement.model.Metadata;
import uit.edu.vn.universitymanagement.repository.CommonJpaRepository;
import uit.edu.vn.universitymanagement.util.AuthenticationUtils;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public abstract class AbstractCrudService<T extends ManagedEntity> implements SingleCrudService<T>, MultipleCrudService<T>, Authorizer<T> {
    final CommonJpaRepository<T, Long> repository;

    @Override
    public boolean notAuthorize(Authentication authentication, ActionType actionType, List<T> objects) {
        return objects.stream().map(obj -> notAuthorize(authentication, actionType, obj)).reduce((Boolean::logicalAnd)).orElse(false);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public T create(Authentication authentication, T object) {
        if (notAuthorize(authentication, ActionType.WRITE, object)) {
            throw new PermissionDeniedException();
        }
        object.setMetadata(new Metadata());
        object.getMetadata().setCreator(AuthenticationUtils.getAccount(authentication));
        object.getMetadata().setCreatedAt(new Date());
        object.getMetadata().setLastModifier(null);
        object.getMetadata().setModifiedAt(null);
        return repository.save(object);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public T read(Authentication authentication, long id) {
        Optional<T> optionalT = repository.findById(id);
        T object = optionalT.orElseThrow(ResourceNotFoundException::new);
        if (notAuthorize(authentication, ActionType.READ, object)) {
            throw new PermissionDeniedException();
        }
        return object;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public T update(Authentication authentication, T object) {
        if (notAuthorize(authentication, ActionType.WRITE, object)) {
            throw new PermissionDeniedException();
        }
        Optional<T> optionalT = repository.findById(object.getId());
        T savedObject = optionalT.orElseThrow(ResourceNotFoundException::new);
        object.setMetadata(new Metadata());
        object.getMetadata().setCreator(savedObject.getMetadata().getCreator());
        object.getMetadata().setCreatedAt(savedObject.getMetadata().getCreatedAt());
        object.getMetadata().setLastModifier(AuthenticationUtils.getAccount(authentication));
        object.getMetadata().setModifiedAt(new Date());
        return repository.save(object);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void delete(Authentication authentication, long id) {
        T object = repository.findById(id).orElseThrow(ResourceNotFoundException::new);
        repository.deleteById(id);
        if (notAuthorize(authentication, ActionType.WRITE, object)) {
            throw new PermissionDeniedException();
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public List<T> create(Authentication authentication, List<T> objects) {
        objects.forEach(obj -> {
            obj.setMetadata(new Metadata());
            obj.getMetadata().setCreator(AuthenticationUtils.getAccount(authentication));
            obj.getMetadata().setCreatedAt(new Date());
            obj.getMetadata().setLastModifier(null);
            obj.getMetadata().setModifiedAt(null);
        });
        if (notAuthorize(authentication, ActionType.WRITE, objects)) {
            throw new PermissionDeniedException();
        }
        return repository.saveAll(objects);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Page<T> read(Authentication authentication, List<Long> ids, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<T> objects = repository.findAllByIdIn(ids, pageable);
        if (ids.isEmpty()) {
            objects = repository.findAll(pageable);
        }
        if (notAuthorize(authentication, ActionType.READ, objects.getContent())) {
            throw new PermissionDeniedException();
        }
        return objects;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public List<T> read(Authentication authentication, List<Long> ids) {
        List<T> objects = repository.findAllByIdIn(ids);
        if (notAuthorize(authentication, ActionType.READ, objects)) {
            throw new PermissionDeniedException();
        }
        return objects;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public List<T> update(Authentication authentication, List<T> objects) {
        if (notAuthorize(authentication, ActionType.WRITE, objects)) {
            throw new PermissionDeniedException();
        }
        List<Long> ids = objects.stream().map(ManagedEntity::getId).collect(Collectors.toList());
        List<T> savedObjects = repository.findAllByIdIn(ids);
        if (savedObjects.size() != objects.size()) {
            throw new ResourceNotFoundException();
        }
        savedObjects.sort(Comparator.comparingLong(ManagedEntity::getId));
        objects.sort(Comparator.comparingLong(ManagedEntity::getId));
        for (int i = 0; i < savedObjects.size(); i++) {
            objects.get(i).setMetadata(new Metadata());
            objects.get(i).getMetadata().setCreator(savedObjects.get(i).getMetadata().getCreator());
            objects.get(i).getMetadata().setCreatedAt(savedObjects.get(i).getMetadata().getCreatedAt());
            objects.get(i).getMetadata().setLastModifier(AuthenticationUtils.getAccount(authentication));
            objects.get(i).getMetadata().setModifiedAt(new Date());
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
        if (notAuthorize(authentication, ActionType.WRITE, objects)) {
            throw new PermissionDeniedException();
        }
    }
}
