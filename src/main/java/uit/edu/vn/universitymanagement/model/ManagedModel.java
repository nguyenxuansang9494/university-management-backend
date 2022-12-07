package uit.edu.vn.universitymanagement.model;

public interface ManagedModel {
    Long getId();
    Metadata getMetadata();
    void setMetadata(Metadata metadata);
}
