package uit.edu.vn.universitymanagement.model;

public interface ManagedModel {
    long getId();
    Metadata getMetadata();
    void setMetadata(Metadata metadata);
}
