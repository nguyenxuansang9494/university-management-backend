package uit.edu.vn.universitymanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uit.edu.vn.universitymanagement.model.Term;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CurriculumDto {

    private Long id;
    private String name;
    private String codeName;
    private MetadataDto metadata;
    private Term firstAppliedTerm;
    private MajorDto major;
}
