package uit.edu.vn.universitymanagement.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import uit.edu.vn.universitymanagement.dto.QueryByIdDto;
import uit.edu.vn.universitymanagement.model.ManagedModel;
import uit.edu.vn.universitymanagement.service.AbstractCrudService;
import uit.edu.vn.universitymanagement.util.MapperUtils;

import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractCrudController<T extends ManagedModel, U> {
    private final ModelMapper modelMapper;
    private final AbstractCrudService<T> service;
    private final Class<T> tClass;
    private final Class<U> dtoClass;

    public ResponseEntity<U> create(Authentication authentication, U reqDto) {
        T object = modelMapper.map(reqDto, tClass);
        T savedObject = service.create(authentication, object);
        U rspDto = modelMapper.map(savedObject, dtoClass);
        return ResponseEntity.ok(rspDto);
    }

    public ResponseEntity<U> read(Authentication authentication, long id) {
        T object = service.read(authentication, id);
        U rspDto = modelMapper.map(object, dtoClass);
        return ResponseEntity.ok(rspDto);
    }

    public ResponseEntity<U> update(Authentication authentication, U reqDto) {
        T object = modelMapper.map(reqDto, tClass);
        T updatedObject = service.update(authentication, object);
        U rspDtos = modelMapper.map(updatedObject, dtoClass);
        return ResponseEntity.ok(rspDtos);
    }

    public ResponseEntity<Void> delete(Authentication authentication, long id) {
        service.delete(authentication, id);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<List<U>> create(Authentication authentication, List<U> reqDtos) {
        List<T> objects = MapperUtils.mapList(reqDtos, tClass);
        List<T> savedObjects = service.create(authentication, objects);
        List<U> rspDtos = MapperUtils.mapList(savedObjects, dtoClass);
        return ResponseEntity.ok(rspDtos);
    }

    public ResponseEntity<Object> read(Authentication authentication, QueryByIdDto queryByIdDto) {
        if (!queryByIdDto.isPaged()) {
            List<T> objects = service.read(authentication, queryByIdDto.getIds());
            List<U> rspDtos = MapperUtils.mapList(objects, dtoClass);
            return ResponseEntity.ok(rspDtos);
        }
        Page<T> objects = service.read(authentication, queryByIdDto.getIds(), queryByIdDto.getPage(), queryByIdDto.getSize());
        Page<U> rspDtos = MapperUtils.mapPage(objects, dtoClass);
        return ResponseEntity.ok(rspDtos);
    }

    public ResponseEntity<List<U>> update(Authentication authentication, List<U> reqDtos) {
        List<T> objects = MapperUtils.mapList(reqDtos, tClass);
        List<T> updatedObjects = service.update(authentication, objects);
        List<U> rspDto = MapperUtils.mapList(updatedObjects, dtoClass);
        return ResponseEntity.ok(rspDto);
    }

    public ResponseEntity<Void> delete(Authentication authentication, List<Long> ids) {
        service.delete(authentication, ids);
        return ResponseEntity.ok().build();
    }
}
