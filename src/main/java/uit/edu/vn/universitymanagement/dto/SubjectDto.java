package uit.edu.vn.universitymanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubjectDto {
    private long id;
    private String name;
    private String codeName;
    private Integer credit;
    private FacultyDto faculty;
    MetadataDto metadata;
    private Set<NonRecursiveSubjectDto> prerequisiteSubjects;
}
