package uit.edu.vn.universitymanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PrerequisiteSubjectDto {
    private Long id;
    private Long subjectId;
    private SubjectDto prerequisite;
    private MetadataDto metadata;
}
