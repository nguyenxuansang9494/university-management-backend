package uit.edu.vn.universitymanagement.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uit.edu.vn.universitymanagement.model.ManagedEntity;
import uit.edu.vn.universitymanagement.model.Metadata;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Subject implements ManagedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subject_id_seq")
    @SequenceGenerator(name = "subject_id_seq", allocationSize = 100)
    private long id;
    private String name;
    private String codeName;
    private Integer credit;
    @Embedded
    private Metadata metadata;
    @ManyToOne(optional = false)
    private Faculty faculty;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "mandatorySubjects")
    private Set<Curriculum> mandatoryCurriculums;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "optionalSubjects")
    private Set<Curriculum> optionalCurriculums;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "subjects")
    private Set<Teacher> teachers;
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Subject> prerequisiteSubjects;
}
