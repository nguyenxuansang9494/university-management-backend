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
        name = "prerequisite_subject",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"subject_id", "prerequisite_id"})
        })
public class PrerequisiteSubject extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prerequisite_subject_gen")
    @SequenceGenerator(name = "prerequisite_subject_gen", allocationSize = 100)
    private Long id;
    @ManyToOne
    private Subject subject;
    @ManyToOne
    private Subject prerequisite;
    @Embedded
    private Metadata metadata;
}
