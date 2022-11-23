package uit.edu.vn.universitymanagement.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Faculty_id_seq")
    @SequenceGenerator(name = "Faculty_id_seq", allocationSize = 100)
    private Long id;
    private String name;
    private String creationDate;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Major> majors;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Subject> subjects;
}
