package uit.edu.vn.universitymanagement.model;

import java.util.Date;

import javax.persistence.Column;
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
public class PersonalInformation {
    @Column(unique = true, nullable = false)
    private String personalID;
    private String firstName;
    private String lastName;
    private Date registerDate = new Date();
    private Date dob;
    private String address;
    private String birthPlace;
    private String email;
    private String phoneNumber;
}
