package uit.edu.vn.universitymanagement.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uit.edu.vn.universitymanagement.model.ManagedModel;
import uit.edu.vn.universitymanagement.model.Metadata;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Faculty implements ManagedModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "faculty_id_seq")
    @SequenceGenerator(name = "faculty_id_seq", allocationSize = 100)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String codeName;
    @Embedded
    private Metadata metadata;
}
