package uit.edu.vn.universitymanagement.model;

import java.util.Date;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Embeddable
public class PersonalInfomation {
    private String firstName;
    private String fullName;
    private Date dob;
    private String address;
    private String birthPlace;
    private String email;
    private String phoneNumber;
}
