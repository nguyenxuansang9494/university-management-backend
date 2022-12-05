package uit.edu.vn.universitymanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface CommonJpaRepository<T, Long> extends JpaRepository<T, Long> {
    Page<T> findAllByIdIn(List<Long> ids, Pageable pageable);
    List<T> findAllByIdIn(List<Long> ids);
    List<T> deleteByIdIn(List<Long> ids);
    T deleteById(long id);
}
