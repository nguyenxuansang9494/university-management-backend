package uit.edu.vn.universitymanagement.util;

import uit.edu.vn.universitymanagement.model.Person;
import uit.edu.vn.universitymanagement.model.entity.Student;
import uit.edu.vn.universitymanagement.model.entity.Teacher;

public final class PersonalIDGenerator {
    private PersonalIDGenerator() {
        super();
    }

    public static String generate(Person obj) {
        long count = obj.getId();
        if (obj.getClass().equals(Student.class)) {
            return "ST";
        } else if (obj.getClass().equals(Teacher.class)) {
            return String.format("LT%08d", count);
        }
        return String.format("EM%08d", count);
    }
}
