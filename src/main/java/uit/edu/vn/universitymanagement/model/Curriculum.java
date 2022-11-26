package uit.edu.vn.universitymanagement.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@ToString
@Entity
public class Curriculum {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Curriculum_id_seq")
    @SequenceGenerator(name = "Curriculum_id_seq", allocationSize = 100)
    private Long id;
    private String name;
    private Date creationDate;
    @OneToOne(optional = false)
    private Major major;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Subject> subjects;
}
