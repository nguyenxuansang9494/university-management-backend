package uit.edu.vn.universitymanagement.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uit.edu.vn.universitymanagement.model.ManagedModel;
import uit.edu.vn.universitymanagement.model.Metadata;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public class Major implements ManagedModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "major_id_seq")
    @SequenceGenerator(name = "major_id_seq", allocationSize = 100)
    private long id;
    private String name;
    private String codeName;
    @Embedded
    private Metadata metadata;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Faculty faculty;
}
