package uit.edu.vn.universitymanagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uit.edu.vn.universitymanagement.model.entity.Account;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class Metadata implements Serializable {
    private Date createdAt;
    private Date modifiedAt;
    @OneToOne(fetch = FetchType.LAZY)
    private Account creator;
    @OneToOne(fetch = FetchType.LAZY)
    private Account lastModifier;

}
