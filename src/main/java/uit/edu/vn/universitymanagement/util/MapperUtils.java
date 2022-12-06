package uit.edu.vn.universitymanagement.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

public final class MapperUtils {
    private static final ModelMapper modelMapper;

    static {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.STANDARD);
    }

    private MapperUtils() {
        super();
    }

    public static <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }

    public static <S, T> Page<T> mapPage(Page<S> source, Class<T> targetClass) {

        return new PageImpl<>(source.get()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList()), source.getPageable(), source.getTotalElements());
    }
}
