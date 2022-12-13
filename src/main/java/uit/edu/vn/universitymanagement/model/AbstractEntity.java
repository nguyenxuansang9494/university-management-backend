package uit.edu.vn.universitymanagement.model;

import java.util.Objects;

public abstract class AbstractEntity implements ManagedModel {
    @Override
    public int hashCode() {
        return this.getId().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !obj.getClass().equals(this.getClass()))
            return false;
        return Objects.equals(((AbstractEntity) obj).getId(), this.getId());
    }
}
