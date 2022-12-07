package uit.edu.vn.universitymanagement.model.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uit.edu.vn.universitymanagement.model.ManagedModel;
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
        name = "curriculum_subject",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"subject_id", "curriculum_id"})
        })
public class CurriculumSubject implements ManagedModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "curriculum_subject_id_seq")
    @SequenceGenerator(name = "curriculum_subject_id_seq", allocationSize = 100)
    private Long id;
    @ManyToOne
    private Curriculum curriculum;
    @ManyToOne
    private Subject subject;
    private boolean isOptional;
    @Embedded
    private Metadata metadata;
}
