package uit.edu.vn.universitymanagement.model;

import java.util.Objects;

public abstract class AbstractEntity implements ManagedModel {
    @Override
    public int hashCode() {
        return Objects.hashCode(this.getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !obj.getClass().equals(this.getClass()))
            return false;
        return Objects.equals(this.getClass().cast(obj).getId(), this.getId());
    }

    public boolean isObjectEquals(Object obj) {
        return super.equals(obj);
    }
}
