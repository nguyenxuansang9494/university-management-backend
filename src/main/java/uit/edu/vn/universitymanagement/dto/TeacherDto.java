package uit.edu.vn.universitymanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uit.edu.vn.universitymanagement.model.PersonalInformation;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TeacherDto {
    private Long id;
    private PersonalInformation personalInformation;
    private AccountDto account;
    private MetadataDto metadata;
    private Long facultyId;
}
