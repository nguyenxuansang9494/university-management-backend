package uit.edu.vn.universitymanagement.model.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uit.edu.vn.universitymanagement.model.ManagedModel;
import uit.edu.vn.universitymanagement.model.Metadata;
import uit.edu.vn.universitymanagement.model.PersonalInfomation;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Employee implements ManagedModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admin_id_seq")
    @SequenceGenerator(name = "admin_id_seq", allocationSize = 100)
    private Long id;
    @Embedded
    private PersonalInfomation personalInfomation;
    @OneToOne
    private Account account;
    @Embedded
    private Metadata metadata;
}
