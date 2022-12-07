package uit.edu.vn.universitymanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface CommonJpaRepository<T, L> extends JpaRepository<T, L> {
    Page<T> findAll(Pageable pageable);

    Page<T> findAllByIdIn(List<L> ids, Pageable pageable);

    List<T> findAllByIdIn(List<L> ids);

    void deleteByIdIn(List<L> ids);
    Integer countByIdIn(List<L> ids);
}
