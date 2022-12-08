package uit.edu.vn.universitymanagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import uit.edu.vn.universitymanagement.dto.QueryByIdDto;
import uit.edu.vn.universitymanagement.model.ManagedModel;
import uit.edu.vn.universitymanagement.service.AbstractCrudService;
import uit.edu.vn.universitymanagement.util.ModelMapperWrapper;

import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractCrudController<T extends ManagedModel, U> {
    final ModelMapperWrapper modelMapperWrapper;
    final AbstractCrudService<T> service;
    private final Class<T> tClass;
    private final Class<U> dtoClass;

    @PutMapping
    public ResponseEntity<U> create(Authentication authentication, @RequestBody U reqDto) {
        T object = modelMapperWrapper.getModelMapper().map(reqDto, tClass);
        T savedObject = service.create(authentication, object);
        U rspDto = modelMapperWrapper.getModelMapper().map(savedObject, dtoClass);
        return ResponseEntity.ok(rspDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<U> read(Authentication authentication, @PathVariable("id") Long id) {
        T object = service.read(authentication, id);
        U rspDto = modelMapperWrapper.getModelMapper().map(object, dtoClass);
        return ResponseEntity.ok(rspDto);
    }

    @PatchMapping
    public ResponseEntity<U> update(Authentication authentication, @RequestBody U reqDto) {
        T object = modelMapperWrapper.getModelMapper().map(reqDto, tClass);
        T updatedObject = service.update(authentication, object);
        U rspDtos = modelMapperWrapper.getModelMapper().map(updatedObject, dtoClass);
        return ResponseEntity.ok(rspDtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(Authentication authentication, @PathVariable("id") Long id) {
        service.delete(authentication, id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/batch")
    public ResponseEntity<List<U>> create(Authentication authentication, @RequestBody List<U> reqDtos) {
        List<T> objects = modelMapperWrapper.mapList(reqDtos, tClass);
        List<T> savedObjects = service.create(authentication, objects);
        List<U> rspDtos = modelMapperWrapper.mapList(savedObjects, dtoClass);
        return ResponseEntity.ok(rspDtos);
    }

    @PostMapping("/batch")
    public ResponseEntity<Object> read(Authentication authentication, @RequestBody QueryByIdDto queryByIdDto) {
        if (!queryByIdDto.isPaged()) {
            List<T> objects = service.read(authentication, queryByIdDto.getIds());
            List<U> rspDtos = modelMapperWrapper.mapList(objects, dtoClass);
            return ResponseEntity.ok(rspDtos);
        }
        Page<T> objects = service.read(authentication, queryByIdDto.getIds(), queryByIdDto.getPage(), queryByIdDto.getSize());
        Page<U> rspDtos = modelMapperWrapper.mapPage(objects, dtoClass);
        return ResponseEntity.ok(rspDtos);
    }

    @PatchMapping("/batch")
    public ResponseEntity<List<U>> update(Authentication authentication, @RequestBody List<U> reqDtos) {
        List<T> objects = modelMapperWrapper.mapList(reqDtos, tClass);
        List<T> updatedObjects = service.update(authentication, objects);
        List<U> rspDto = modelMapperWrapper.mapList(updatedObjects, dtoClass);
        return ResponseEntity.ok(rspDto);
    }

    @DeleteMapping("/batch")
    public ResponseEntity<Void> delete(Authentication authentication, @RequestBody List<Long> ids) {
        service.delete(authentication, ids);
        return ResponseEntity.ok().build();
    }

}
