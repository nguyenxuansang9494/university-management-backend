package uit.edu.vn.universitymanagement.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import uit.edu.vn.universitymanagement.dto.QueryByIdDto;
import uit.edu.vn.universitymanagement.dto.RequestDto;
import uit.edu.vn.universitymanagement.dto.ResponseDto;
import uit.edu.vn.universitymanagement.model.ManagedEntity;
import uit.edu.vn.universitymanagement.service.AbstractCrudService;

import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractCrudController<T extends ManagedEntity, U extends RequestDto, V extends ResponseDto> {
    private final ModelMapper modelMapper;
    private final AbstractCrudService<T> service;
    private Class<T> tClass;
    private Class<U> reqDtoClass;
    private Class<V> rspDtoClass;

    public ResponseEntity<V> create(Authentication authentication, U reqDto) {
        T object = modelMapper.map(reqDto, tClass);
        T savedObject = service.create(authentication, object);
        V rspDto = modelMapper.map(savedObject, rspDtoClass);
        return ResponseEntity.ok(rspDto);
    }

    public ResponseEntity<V> read(Authentication authentication, long id) {
        T object = service.read(authentication, id);
        V rspDto = modelMapper.map(object, rspDtoClass);
        return ResponseEntity.ok(rspDto);
    }

    public ResponseEntity<V> update(Authentication authentication, U reqDto) {
        T object = modelMapper.map(reqDto, tClass);
        T updatedObject = service.update(authentication, object);
        V rspDtos = modelMapper.map(updatedObject, rspDtoClass);
        return ResponseEntity.ok(rspDtos);
    }

    public ResponseEntity<Void> delete(Authentication authentication, long id) {
        service.delete(authentication, id);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<List<V>> create(Authentication authentication, List<U> reqDtos) {
        List<T> objects = modelMapper.map(reqDtos, new TypeToken<List<T>>() {
        }.getType());
        List<T> savedObjects = service.create(authentication, objects);
        List<V> rspDtos = modelMapper.map(savedObjects, new TypeToken<V>() {
        }.getType());
        return ResponseEntity.ok(rspDtos);
    }

    public ResponseEntity<Object> read(Authentication authentication, QueryByIdDto queryByIdDto) {
        if (!queryByIdDto.isPaged()) {
            List<T> objects = service.read(authentication, queryByIdDto.getIds());
            List<V> rspDtos = modelMapper.map(objects, new TypeToken<List<V>>() {
            }.getType());
            return ResponseEntity.ok(rspDtos);
        }
        Page<T> objects = service.read(authentication, queryByIdDto.getIds(), queryByIdDto.getPage(), queryByIdDto.getSize());
        Page<V> rspDtos = modelMapper.map(objects, new TypeToken<Page<V>>() {
        }.getType());
        return ResponseEntity.ok(rspDtos);
    }

    public ResponseEntity<List<V>> update(Authentication authentication, List<U> reqDtos) {
        List<T> objects = modelMapper.map(reqDtos, new TypeToken<List<T>>(){}.getType());
        List<T> updatedObjects = service.update(authentication, objects);
        List<V> rspDto = modelMapper.map(updatedObjects, new TypeToken<List<V>>(){}.getType());
        return ResponseEntity.ok(rspDto);
    }

    public ResponseEntity<Void> delete(Authentication authentication, List<Long> ids) {
        service.delete(authentication, ids);
        return ResponseEntity.ok().build();
    }
}
