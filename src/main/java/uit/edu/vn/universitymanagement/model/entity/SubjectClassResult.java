package uit.edu.vn.universitymanagement.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uit.edu.vn.universitymanagement.model.AbstractEntity;
import uit.edu.vn.universitymanagement.model.Metadata;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(
        name = "subject_class_result",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"subject_class_id", "student_id"})
        })
public class SubjectClassResult extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subject_class_result_id_seq")
    @SequenceGenerator(name = "subject_class_result_id_seq", allocationSize = 100)
    private Long id;
    @Embedded
    private Metadata metadata;
    private float finalResult;
    @ManyToOne
    private SubjectClass subjectClass;
    @ManyToOne
    private Student student;
    private boolean isFailed;
    private boolean isDone;
}
