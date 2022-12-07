package uit.edu.vn.universitymanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uit.edu.vn.universitymanagement.model.Metadata;
import uit.edu.vn.universitymanagement.model.Term;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CurriculumDto {

    private long id;
    private String name;
    private String codeName;
    private Metadata metadata;
    private Term firstAppliedTerm;
    private MajorDto major;
    private Set<SubjectDto> mandatorySubjects;
    private Set<SubjectDto> optionalSubjects;
}
