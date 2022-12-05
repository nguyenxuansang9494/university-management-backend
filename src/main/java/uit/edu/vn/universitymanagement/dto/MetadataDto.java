package uit.edu.vn.universitymanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MetadataDto {
    private Date createdAt;
    private long creatorId;
    private String creatorUsername;
    private Date modifiedAt;
    private long lastModifierId;
    private String lastModifierUsername;
}
