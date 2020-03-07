package co.com.publishapp.repository;

import co.com.publishapp.domain.Autor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Autor entity.
 */
@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    @Query(value = "select distinct autor from Autor autor left join fetch autor.universidads",
        countQuery = "select count(distinct autor) from Autor autor")
    Page<Autor> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct autor from Autor autor left join fetch autor.universidads")
    List<Autor> findAllWithEagerRelationships();

    @Query("select autor from Autor autor left join fetch autor.universidads where autor.id =:id")
    Optional<Autor> findOneWithEagerRelationships(@Param("id") Long id);

}
