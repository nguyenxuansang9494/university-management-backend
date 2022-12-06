package uit.edu.vn.universitymanagement.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uit.edu.vn.universitymanagement.model.ManagedEntity;
import uit.edu.vn.universitymanagement.model.Metadata;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Faculty implements ManagedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "faculty_id_seq")
    @SequenceGenerator(name = "faculty_id_seq", allocationSize = 100)
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String codeName;
    @Embedded
    private Metadata metadata;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Major> majors;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Subject> subjects;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Teacher> teachers;
}
