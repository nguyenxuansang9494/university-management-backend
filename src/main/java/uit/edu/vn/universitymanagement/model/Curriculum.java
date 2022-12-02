package uit.edu.vn.universitymanagement.model;

import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
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
@Entity
public class Curriculum {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Curriculum_id_seq")
    @SequenceGenerator(name = "Curriculum_id_seq", allocationSize = 100)
    private Long id;
    private String name;
    @Embedded
    private Metadata metadata;
    @Embedded
    private Term firstAppliedTerm;
    @ManyToOne(optional = false)
    private Major major;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "mandatory_subjects")
    @ToString.Exclude
    private List<Subject> mandatorySubjects;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "optional_subjects")
    @ToString.Exclude
    private List<Subject> optionalSubjects;
}
