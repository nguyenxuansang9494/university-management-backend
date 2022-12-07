package uit.edu.vn.universitymanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CurriculumSubjectDto {
    private Long id;
    private Long curriculumId;
    private SubjectDto subject;
    private Boolean isOptional;
    private MetadataDto metadata;
}
