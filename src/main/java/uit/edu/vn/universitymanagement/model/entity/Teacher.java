package uit.edu.vn.universitymanagement.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uit.edu.vn.universitymanagement.model.Metadata;
import uit.edu.vn.universitymanagement.model.PersonalInfomation;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teacher_id_seq")
    @SequenceGenerator(name = "teacher_id_seq", allocationSize = 100)
    private Long id;
    @Embedded
    private PersonalInfomation personalInfomation;
    @OneToOne
    private Account account;
    @Embedded
    private Metadata metadata;
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Subject> subjects;
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<SubjectClass> subjectClasses;
    @ManyToOne
    private Faculty faculty;
}
