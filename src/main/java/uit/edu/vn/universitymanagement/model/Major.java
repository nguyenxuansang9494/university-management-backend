package uit.edu.vn.universitymanagement.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.springframework.context.annotation.Lazy;

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
public class Major {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Major_id_seq")
    @SequenceGenerator(name = "Major_id_seq", allocationSize = 100)
    private Long id;
    private String name;
    private Date creationDate;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @Lazy
    private Faculty faculty;
}
