package uit.edu.vn.universitymanagement.model;

import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Teacher_id_seq")
    @SequenceGenerator(name = "Teacher_id_seq", allocationSize = 100)
    private Long id;
    @Embedded
    private PersonalInfomation personalInfomation;
    @ManyToMany
    private List<Subject> subjects;
    @ManyToOne
    private Faculty faculty;
}
