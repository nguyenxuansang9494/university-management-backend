package uit.edu.vn.universitymanagement.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@Getter
@Setter
@Entity
public class Subject {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subject_id_seq")
    @SequenceGenerator(name = "subject_id_seq", allocationSize = 100)
    private Long id;
    private String name;
    private Integer credit;
    @Embedded
    private Metadata metadata;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Faculty faculty;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "mandatorySubjects")
    private Set<Curriculum> mandatoryCurriculums;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "optionalSubjects")
    private Set<Curriculum> optionalCurriculums;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "subjects")
    private Set<Teacher> teachers;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "prerequisiteSubjects")
    private Set<Subject> prerequisiteSubjects;
}
