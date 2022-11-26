package uit.edu.vn.universitymanagement.model;

import java.util.Date;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class Metadata {
    private Date creationDate;
    private Date lastModificationDate;
}
