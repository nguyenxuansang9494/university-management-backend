package uit.edu.vn.universitymanagement.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uit.edu.vn.universitymanagement.model.ManagedModel;
import uit.edu.vn.universitymanagement.model.Metadata;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubjectClassTeacher implements ManagedModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subject_class_teacher_id_seq")
    @SequenceGenerator(name = "subject_teacher_class_id_seq", allocationSize = 100)
    private long id;
    @Embedded
    private Metadata metadata;
    @ManyToOne
    private Teacher teacher;
    @ManyToOne
    private SubjectClass subjectClass;
}