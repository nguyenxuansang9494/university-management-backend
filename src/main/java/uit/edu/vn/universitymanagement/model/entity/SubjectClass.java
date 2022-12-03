package uit.edu.vn.universitymanagement.model.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uit.edu.vn.universitymanagement.model.Metadata;
import uit.edu.vn.universitymanagement.model.Term;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class SubjectClass {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subject_class_id_seq")
    @SequenceGenerator(name = "subject_class_id_seq", allocationSize = 100)
    private long id;
    @Embedded
    private Metadata metadata;
    @OneToOne
    private Subject subject;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "subjectClasses")
    private Set<Teacher> teachers;
    @OneToMany
    private Set<SubjectClassResult> subjectClassResults;
    @Embedded
    private Term term;
}
