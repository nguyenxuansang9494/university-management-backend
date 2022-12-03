package uit.edu.vn.universitymanagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uit.edu.vn.universitymanagement.model.entity.Account;

import javax.persistence.Embeddable;
import javax.persistence.OneToOne;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class Metadata {
    private Date creationDate;
    private Date lastModificationDate;
    @OneToOne
    private Account creator;
    @OneToOne
    private Account lastModifier;
}
