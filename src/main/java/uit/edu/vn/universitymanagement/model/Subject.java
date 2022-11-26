package uit.edu.vn.universitymanagement.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@ToString
@Entity
public class Subject {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Subject_id_seq")
    @SequenceGenerator(name = "Subject_id_seq", allocationSize = 100)
    private Long id;
    private String name;
    private Integer credit;
    @Embedded
    private Metadata metadata;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Faculty faculty;
}
