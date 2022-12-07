package uit.edu.vn.universitymanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubjectDto {
    private Long id;
    private String name;
    private String codeName;
    private Integer credit;
    private FacultyDto faculty;
    MetadataDto metadata;
}
