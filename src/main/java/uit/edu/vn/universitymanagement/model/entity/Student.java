package uit.edu.vn.universitymanagement.model.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uit.edu.vn.universitymanagement.model.ManagedModel;
import uit.edu.vn.universitymanagement.model.Metadata;
import uit.edu.vn.universitymanagement.model.PersonalInfomation;
import uit.edu.vn.universitymanagement.model.Term;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Student implements ManagedModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_id_seq")
    @SequenceGenerator(name = "student_id_seq", allocationSize = 100)
    private Long id;
    @Embedded
    private PersonalInfomation personalInfomation;
    @OneToOne
    private Account account;
    @Embedded
    private Metadata metadata;
    @Embedded
    private Term firstTerm;
    @ManyToOne
    private Curriculum oldCurriculum;
    @ManyToOne
    private Curriculum curriculum;
    @ElementCollection(targetClass = Term.class)
    private Set<Term> inactiveTerms;
    private boolean isActive;
}
