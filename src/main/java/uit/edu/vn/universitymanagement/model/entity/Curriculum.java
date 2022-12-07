package uit.edu.vn.universitymanagement.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uit.edu.vn.universitymanagement.model.ManagedModel;
import uit.edu.vn.universitymanagement.model.Metadata;
import uit.edu.vn.universitymanagement.model.Term;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Curriculum implements ManagedModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "curriculum_id_seq")
    @SequenceGenerator(name = "curriculum_id_seq", allocationSize = 100)
    private Long id;
    private String name;
    private String codeName;
    @Embedded
    private Metadata metadata;
    @Embedded
    private Term firstAppliedTerm;
    @ManyToOne(optional = false)
    private Major major;
}
