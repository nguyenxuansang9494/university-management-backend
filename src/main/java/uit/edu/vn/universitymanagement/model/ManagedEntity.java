package uit.edu.vn.universitymanagement.model;

public interface ManagedEntity {
    long getId();
    Metadata getMetadata();
    void setMetadata(Metadata metadata);
}