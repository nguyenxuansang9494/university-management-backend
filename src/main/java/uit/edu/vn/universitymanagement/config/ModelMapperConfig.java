package uit.edu.vn.universitymanagement.config;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Configuration;
import uit.edu.vn.universitymanagement.dto.MetadataDto;
import uit.edu.vn.universitymanagement.model.Metadata;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Configuration
public class ModelMapperConfig {
    private final ModelMapper modelMapper;
    @PostConstruct
    void setupModelMapperForMetadata() {
        TypeMap<Metadata, MetadataDto> typeMap = modelMapper.createTypeMap(Metadata.class, MetadataDto.class);
        typeMap.addMappings(mapper -> mapper.map(src -> src.getCreator().getUsername(), MetadataDto::setCreatorUsername));
        typeMap.addMappings(mapper -> mapper.map(src -> src.getCreator().getId(), MetadataDto::setCreatorId));
        typeMap.addMappings(mapper -> mapper.map(src -> src.getLastModifier().getUsername(), MetadataDto::setLastModifierUsername));
        typeMap.addMappings(mapper -> mapper.map(src -> src.getLastModifier().getId(), MetadataDto::setLastModifierId));
    }
}
