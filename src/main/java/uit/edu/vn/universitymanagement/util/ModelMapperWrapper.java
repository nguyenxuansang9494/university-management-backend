package uit.edu.vn.universitymanagement.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Getter
public class ModelMapperWrapper {
    private final ModelMapper modelMapper;

    public <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }

    public <S, T> Page<T> mapPage(Page<S> source, Class<T> targetClass) {

        return new PageImpl<>(source.get()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList()), source.getPageable(), source.getTotalElements());
    }
}
