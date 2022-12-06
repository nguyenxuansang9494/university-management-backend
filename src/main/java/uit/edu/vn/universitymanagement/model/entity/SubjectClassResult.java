package uit.edu.vn.universitymanagement.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uit.edu.vn.universitymanagement.model.ManagedModel;
import uit.edu.vn.universitymanagement.model.Metadata;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class SubjectClassResult implements ManagedModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subject_class_result_id_seq")
    @SequenceGenerator(name = "subject_class_result_id_seq", allocationSize = 100)
    private long id;
    @Embedded
    private Metadata metadata;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Grade> grades;
    private float finalResult;
    @ManyToOne
    private SubjectClass subjectClass;
    @ManyToOne
    private Student student;
    private boolean isFailed;
    private boolean isDone;
}
